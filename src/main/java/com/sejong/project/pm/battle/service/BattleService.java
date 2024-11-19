package com.sejong.project.pm.battle.service;

import com.sejong.project.pm.battle.Battle;
import com.sejong.project.pm.battle.BattlePhrase;
import com.sejong.project.pm.battle.dto.BattleRequest;
import com.sejong.project.pm.battle.dto.BattleResponse;
import com.sejong.project.pm.battle.repository.BattlePhraseRepository;
import com.sejong.project.pm.battle.repository.BattleRepository;
import com.sejong.project.pm.member.Member;
import com.sejong.project.pm.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class BattleService {
    private static final long INVITE_CODE_TTL = 10;  // 10 minutes TTL

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BattleRepository battleRepository;
    @Autowired
    private BattlePhraseRepository battlePhraseRepository;


    // 초대 룸 생성
    public Long createroom(BattleRequest.createBattleRequestDto createBattleRequestDto) {
        System.out.println(createBattleRequestDto);
        Optional<Member> optionalmember = memberRepository.findById(createBattleRequestDto.member1Id());
        Member member1 = optionalmember.get();
        System.out.println(member1);
        Battle newbattle = new Battle(member1, member1.getMemberWeight(), createBattleRequestDto.member1TargetWeight(), createBattleRequestDto.targetDay());
        System.out.println(member1);
        battleRepository.save(newbattle);

        return newbattle.getBattleId();
    }

    // 초대 코드 생성
    public String createInviteCode(Long battleId) {

        Optional<Battle> optionalBattle = battleRepository.findById(battleId);

        String inviteCode = UUID.randomUUID().toString();

        Battle battle = optionalBattle.get();
        battle.setInviteCode(inviteCode);
        battleRepository.save(battle);
        System.out.println(inviteCode);
        return inviteCode;
    }

    // 초대 코드 조회
    public void acceptbattle(BattleRequest.acceptBattleRequestDto acceptBattleRequestDto) {
        Optional<Battle> optionalBattle = battleRepository.findByInviteCode(acceptBattleRequestDto.inviteCode());
        Battle battle = optionalBattle.get();

        Optional<Member> optionalMember = memberRepository.findById(acceptBattleRequestDto.member2Id());
        battle.setMember2Id(optionalMember.get());
        battle.setMember2TargetWeight(acceptBattleRequestDto.member2TargetWeight());
        battle.setMember2StartWeight(optionalMember.get().getMemberWeight());
        battleRepository.save(battle);
    }

    public BattleResponse.battlestatusDto battlestatus(Long battleId) {
        Optional<Battle> optionalBattle = battleRepository.findById(battleId);
        Battle battle = optionalBattle.get();

        Optional<Member> optionalMember1 = memberRepository.findById(battle.getMember1Id().getId());
        Optional<Member> optionalMember2 = memberRepository.findById(battle.getMember2Id().getId());

        if (optionalMember1.isEmpty() || optionalMember2.isEmpty()) {
            return null;
        }

        Member member1 = optionalMember1.get();
        Member member2 = optionalMember2.get();

        int member1AttainmentRate = (int) Math.round(
                ((battle.getMember1StartWeight() - battle.getMember1Id().getMemberWeight()) /
                        (battle.getMember1StartWeight() - battle.getMember1TargetWeight())) * 100
        );
        int member2AttainmentRate = (int) Math.round(
                ((battle.getMember2StartWeight() - battle.getMember2Id().getMemberWeight()) /
                        (battle.getMember2StartWeight() - battle.getMember2TargetWeight())) * 100
        );
        BattleResponse.battlestatusDto battlestatusDto = new BattleResponse.battlestatusDto(
                member1.getMemberName(),
                member2.getMemberName(),
                member1.getMemberImage(),
                member2.getMemberImage(),
                battle.getMember1StartWeight(),
                battle.getMember2StartWeight(),
                battle.getMember1TargetWeight(),
                battle.getMember2TargetWeight(),
                member1AttainmentRate,
                member2AttainmentRate,
                battle.getTargetDay()
        );
        return battlestatusDto;
    }


    public List<BattleResponse.battleListDto> battlelist(Long memberId) {

        List<Battle> allBattles = battleRepository.findAll();
        LocalDate today = LocalDate.now();

        List<BattleResponse.battleListDto> filteredBattles = allBattles.stream()
                .filter(battle ->
                        (battle.getMember1Id().getId().equals(memberId) || battle.getMember2Id().getId().equals(memberId)) &&
                                !battle.getTargetDay().isBefore(today) && battle.getMember2Id() != null
                )
                .map(battle -> {
                    // 상대방 정보 추출
                    String opponentName;
                    String opponentImage;

                    if (battle.getMember1Id().getId().equals(memberId)) {
                        opponentName = battle.getMember2Id().getMemberName();
                        opponentImage = battle.getMember2Id().getMemberImage();
                    } else {
                        opponentName = battle.getMember1Id().getMemberName();
                        opponentImage = battle.getMember1Id().getMemberImage();
                    }

                    return new BattleResponse.battleListDto(
                            battle.getBattleId(),
                            opponentName,
                            opponentImage,
                            battle.getCreatedAt().toLocalDate(),
                            battle.getTargetDay()
                    );
                })
                .collect(Collectors.toList());

        System.out.println(filteredBattles);
        return filteredBattles;
    }


    public BattleResponse.battleResultDto battleResultDto(BattleRequest.resultBattleRequestDto resultBattleRequestDto) {
        Battle battle = battleRepository.findById(resultBattleRequestDto.battleId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid battle ID"));
        Member member = memberRepository.findById(resultBattleRequestDto.memberId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        double member1RemainWeight = battle.getMember1Id().getMemberWeight() - battle.getMember1TargetWeight();
        double member2RemainWeight = battle.getMember2Id().getMemberWeight() - battle.getMember2TargetWeight();

        boolean isMember1 = member.getId().equals(battle.getMember1Id().getId());
        boolean isWinner = (isMember1 && member1RemainWeight <= member2RemainWeight)
                || (!isMember1 && member1RemainWeight >= member2RemainWeight);

        BattlePhrase.State resultState = isWinner ? BattlePhrase.State.WINNER : BattlePhrase.State.LOSER;
        String phrase = getRandomPhrase(resultState);

        return new BattleResponse.battleResultDto(member.getMemberName(), resultState, phrase);
    }

    private String getRandomPhrase(BattlePhrase.State state) {
        List<BattlePhrase> phrases = battlePhraseRepository.findByState(state);
        if (phrases.isEmpty()) {
            throw new IllegalStateException("No phrases available for state: " + state);
        }
        return phrases.get(new Random().nextInt(phrases.size())).getPhrase();
    }
}