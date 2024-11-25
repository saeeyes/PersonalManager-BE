package com.sejong.project.pm.battle.controller;

import com.sejong.project.pm.battle.Battle;
import com.sejong.project.pm.battle.dto.BattleResponse;
import com.sejong.project.pm.battle.service.BattleService;
import com.sejong.project.pm.battle.dto.BattleRequest;
import com.sejong.project.pm.global.exception.BaseResponse;
import com.sejong.project.pm.member.dto.MemberDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BattelController {

    @Autowired
    private BattleService battleService;

    @PostMapping("/createbattle")
    public BaseResponse<?> createbattle(@AuthenticationPrincipal MemberDetails member, @RequestBody BattleRequest.createBattleRequestDto createBattleRequestDto) {
        Long battleId = battleService.createroom(member, createBattleRequestDto);
        String inviteCode = battleService.createInviteCode(battleId);
        return BaseResponse.onSuccess(inviteCode);
    }


    @PostMapping("/acceptbattle")
    public  BaseResponse<?> acceptbattle(@AuthenticationPrincipal MemberDetails member, @RequestBody BattleRequest.acceptBattleRequestDto acceptBattleRequestDto) {
        battleService.acceptbattle(member, acceptBattleRequestDto);
        return BaseResponse.onSuccess("success");
    }

    @GetMapping("/battlestatus")
    public  BaseResponse<?> battlestatus(@RequestParam(name = "battleId")Long battleId){
        BattleResponse.battlestatusDto battleResponse = battleService.battlestatus(battleId);
        return BaseResponse.onSuccess(battleResponse);
    }

    @GetMapping("/battlelist")
    public  BaseResponse<?> battlelist(@AuthenticationPrincipal MemberDetails member){
        List<BattleResponse.battleListDto> battles = battleService.battlelist(member);
        return BaseResponse.onSuccess(battles);
    }

    @GetMapping("/battleresult")
    public BaseResponse<?> battleresult(@RequestParam(name = "battleId")Long battleId, @AuthenticationPrincipal MemberDetails member){
        return BaseResponse.onSuccess(battleService.battleResultDto(battleId, member));
    }

}
