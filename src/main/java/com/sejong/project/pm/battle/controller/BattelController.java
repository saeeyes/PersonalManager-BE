package com.sejong.project.pm.battle.controller;

import com.sejong.project.pm.battle.Battle;
import com.sejong.project.pm.battle.dto.BattleResponse;
import com.sejong.project.pm.battle.service.BattleService;
import com.sejong.project.pm.battle.dto.BattleRequest;
import com.sejong.project.pm.global.exception.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BattelController {

    @Autowired
    private BattleService battleService;

    @PostMapping("/createbattle")
    public BaseResponse<?> createbattle(@RequestBody BattleRequest.createBattleRequestDto createBattleRequestDto) {
        Long battleId = battleService.createroom(createBattleRequestDto);
        String inviteCode = battleService.createInviteCode(battleId);
        return BaseResponse.onSuccess(inviteCode);
    }


    @PostMapping("/acceptbattle")
    public  BaseResponse<?> acceptbattle(@RequestBody BattleRequest.acceptBattleRequestDto acceptBattleRequestDto) {
        battleService.acceptbattle(acceptBattleRequestDto);
        return BaseResponse.onSuccess("success");
    }

    @PostMapping("/battlestatus")
    public  BaseResponse<?> battlestatus(@RequestBody Map<String, Long> data){
        Long battleId = data.get("battleId");
        BattleResponse.battlestatusDto battleResponse = battleService.battlestatus(battleId);
        return BaseResponse.onSuccess(battleResponse);
    }

    @PostMapping("/battlelist")
    public  BaseResponse<?> battlelist(@RequestBody Map<String, Long> data){
        Long memeberId =data.get("memberId");
        List<BattleResponse.battleListDto> battles = battleService.battlelist(memeberId);
        return BaseResponse.onSuccess(battles);
    }

    @PostMapping("/battleresult")
    public BaseResponse<?> battleresult(@RequestBody BattleRequest.resultBattleRequestDto resultBattleRequestDto){
        return BaseResponse.onSuccess(battleService.battleResultDto(resultBattleRequestDto));
    }

}
