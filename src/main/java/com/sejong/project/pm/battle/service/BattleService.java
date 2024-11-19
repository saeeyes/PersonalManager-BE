package com.sejong.project.pm.battle.service;

import com.sejong.project.pm.battle.Battle;
import com.sejong.project.pm.battle.dto.BattleRequest;
import com.sejong.project.pm.battle.dto.BattleResponse;
import com.sejong.project.pm.battle.repository.BattleRepository;
import com.sejong.project.pm.member.model.Member;
import com.sejong.project.pm.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BattleService {
    private static final long INVITE_CODE_TTL = 10;  // 10 minutes TTL

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BattleRepository battleRepository;

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

    public BattleResponse.battlestatusDto battlestatus(Long battleId){
        Optional<Battle> optionalBattle = battleRepository.findById(battleId);
        Battle battle = optionalBattle.get();

        Optional<Member> optionalMember1 = memberRepository.findById(battle.getMember1Id().getId());
        Optional<Member> optionalMember2 = memberRepository.findById(battle.getMember2Id().getId());
        Member member1 = optionalMember1.get();
        Member member2 = optionalMember2.get();

        BattleResponse.battlestatusDto battlestatusDto = new BattleResponse.battlestatusDto(
                member1.getMemberName(),
                member2.getMemberName(),
                member1.getMemberImage(),
                member2.getMemberImage(),
                battle.getMember1StartWeight(),
                battle.getMember2StartWeight(),
                battle.getMember1TargetWeight(),
                battle.getMember2TargetWeight(),
                battle.getTargetDay()
        );
        return battlestatusDto;
    }


    public List<BattleResponse.battleListDto> battlelist(Long memberId) {
        System.out.println(memberId);
        List<Battle> allBattles = battleRepository.findAll();
        System.out.println(allBattles);
        // 현재 날짜 가져오기
        LocalDate today = LocalDate.now();
        System.out.println(today);
        // 필터링 로직: java.util.Date -> LocalDate 변환 후 비교
        List<BattleResponse.battleListDto> filteredBattles = allBattles.stream()
                .filter(battle ->
                        (battle.getMember1Id().getId().equals(memberId) || battle.getMember2Id().getId().equals(memberId)) &&
                                !convertToLocalDateViaInstant(battle.getTargetDay()).isBefore(today)
                )
                .map(battle -> new BattleResponse.battleListDto(battle.getMember1Id().getMemberName(),
                        battle.getMember2Id().getMemberName(), battle.getMember1Id().getMemberImage(),
                        battle.getMember2Id().getMemberImage(), battle.getCreatedAt(), battle.getTargetDay()))
                .collect(Collectors.toList());

        System.out.println(filteredBattles);
        return filteredBattles;
    }

    private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
    }