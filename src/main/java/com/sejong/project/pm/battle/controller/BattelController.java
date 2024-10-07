package com.sejong.project.pm.battle.controller;

import com.sejong.project.pm.battle.Battle;
import com.sejong.project.pm.battle.dto.BattleResponse;
import com.sejong.project.pm.battle.service.BattleService;
import com.sejong.project.pm.battle.dto.BattleRequest;
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
    public ResponseEntity<String> createbattle(@RequestBody BattleRequest.createBattleRequestDto createBattleRequestDto) {
        Long battleId = battleService.createroom(createBattleRequestDto);
        String inviteCode = battleService.createInviteCode(battleId);
        return ResponseEntity.ok(inviteCode);
    }
        // 초대 코드로 초대 받기

    @GetMapping("/acceptbattle")
    public ResponseEntity<String> acceptbattle(@RequestBody BattleRequest.acceptBattleRequestDto acceptBattleRequestDto) {
         battleService.acceptbattle(acceptBattleRequestDto);
        return ResponseEntity.ok("초대 성공: ");
    }

    @GetMapping("/battlestatus")
    public BattleResponse.battlestatusDto battlestatus(@RequestBody Map<String, Long> data){
        Long battleId = data.get("battleId");
        BattleResponse.battlestatusDto battleResponse = battleService.battlestatus(battleId);
        return battleResponse;
    }

    @GetMapping("battlelist")
    public List<BattleResponse.battleListDto> battlelist(@RequestBody Map<String, Long> data){
        Long memeberId =data.get("memberId");

        List<BattleResponse.battleListDto> battles = battleService.battlelist(memeberId);

        return battles;
    }

}
