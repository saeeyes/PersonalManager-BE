package com.sejong.project.pm.battle.service;

import com.sejong.project.pm.battle.Battle;
import com.sejong.project.pm.battle.BattlePhrase;
import com.sejong.project.pm.battle.dto.BattleRequest;
import com.sejong.project.pm.battle.dto.BattleResponse;
import com.sejong.project.pm.battle.repository.BattlePhraseRepository;
import com.sejong.project.pm.battle.repository.BattleRepository;
import com.sejong.project.pm.global.exception.BaseException;
import com.sejong.project.pm.global.exception.codes.ErrorCode;
import com.sejong.project.pm.member.dto.MemberDetails;
import com.sejong.project.pm.member.model.Member;
import com.sejong.project.pm.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import static com.sejong.project.pm.global.exception.codes.ErrorCode.MEMBER_NOT_FOUND;


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
    public Long createroom(MemberDetails memberDetails, BattleRequest.createBattleRequestDto createBattleRequestDto) {
        Member member = memberRepository
                .findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(ErrorCode.MEMBER_NOT_FOUND));

        Battle newbattle = new Battle(member, member.getMemberWeight(), createBattleRequestDto.member1TargetWeight(), createBattleRequestDto.targetDay());
        battleRepository.save(newbattle);

        return newbattle.getBattleId();
    }

    // 초대 코드 생성
    public String createInviteCode(Long battleId) {
        Optional<Battle> optionalBattle = battleRepository.findById(battleId);

        if (optionalBattle.isEmpty()) {
            throw new IllegalArgumentException("Battle not found with ID: " + battleId);
        }

        Battle battle = optionalBattle.get();

        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();
        String inviteCode;

        do {
            inviteCode = random.ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
        } while (!isValidInviteCode(inviteCode));

        battle.setInviteCode(inviteCode);
        battleRepository.save(battle);
        System.out.println(inviteCode);
        return inviteCode;
    }

    // 초대 코드 유효성 검사
    private boolean isValidInviteCode(String inviteCode) {
        List<Battle> allBattles = battleRepository.findAll(); // 모든 Battle 가져오기
        return allBattles.stream()
                .noneMatch(battle -> inviteCode.equals(battle.getInviteCode()));
    }


    // 초대 코드 조회
    public void acceptbattle(MemberDetails memberDetails, BattleRequest.acceptBattleRequestDto acceptBattleRequestDto) {
        Member member = memberRepository
                .findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        Optional<Battle> optionalBattle = battleRepository.findByInviteCode(acceptBattleRequestDto.inviteCode());
        Battle battle = optionalBattle.get();

        battle.setMember2Id(member);
        battle.setMember2TargetWeight(acceptBattleRequestDto.member2TargetWeight());
        battle.setMember2StartWeight(member.getMemberWeight());
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

    public List<BattleResponse.battleListDto> battlelist(MemberDetails memberDetails) {
        Member member = memberRepository
                .findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        List<Battle> allBattles = battleRepository.findAll();
        LocalDate today = LocalDate.now();

        List<BattleResponse.battleListDto> filteredBattles = allBattles.stream()
                .filter(battle -> {
                    // Member2Id가 null인지 확인
                    if (battle.getMember2Id() == null) {
                        return false;
                    }
                    return (battle.getMember1Id().getId().equals(member.getId())
                            || battle.getMember2Id().getId().equals(member.getId()));
                            //&& !battle.getTargetDay().isBefore(today);
                })
                .map(battle -> {
                    // 상대방 정보 추출
                    String opponentName;
                    String opponentImage;

                    if (battle.getMember1Id().getId().equals(member.getId())) {
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

        return filteredBattles;
    }


    public BattleResponse.battleResultDto battleResultDto(Long battleId, MemberDetails memberDetails) {
        Member member = memberRepository
                .findMemberByMemberEmail(memberDetails.getUsername())
                .orElseThrow(() -> new BaseException(MEMBER_NOT_FOUND));

        Battle battle = battleRepository.findById(battleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid battle ID"));

        double member1RemainWeight = battle.getMember1Id().getMemberWeight() / battle.getMember1TargetWeight() * 100;
        double member2RemainWeight = battle.getMember2Id().getMemberWeight() / battle.getMember2TargetWeight() * 100;

        boolean isMember1 = member.getId().equals(battle.getMember1Id().getId());
        boolean isWinner = (isMember1 && member1RemainWeight <= member2RemainWeight)
                || (!isMember1 && member1RemainWeight >= member2RemainWeight);

        BattlePhrase.State resultState = isWinner ? BattlePhrase.State.WINNER : BattlePhrase.State.LOSER;

        String opponentName = isMember1 ? battle.getMember2Id().getMemberName() : battle.getMember1Id().getMemberName();
        BattlePhrase.State opponentState = isWinner ? BattlePhrase.State.LOSER : BattlePhrase.State.WINNER;

        String phrase = getRandomPhrase(resultState);

        return new BattleResponse.battleResultDto(member.getMemberName(), resultState, opponentName, opponentState, phrase);
    }

    private String getRandomPhrase(BattlePhrase.State state) {
        List<BattlePhrase> phrases = battlePhraseRepository.findByState(state);
        if (phrases.isEmpty()) {
            throw new IllegalStateException("No phrases available for state: " + state);
        }
        return phrases.get(new Random().nextInt(phrases.size())).getPhrase();
    }
}