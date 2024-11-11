package com.sejong.project.pm.battle.repository;

import com.sejong.project.pm.battle.Battle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BattleRepository extends JpaRepository<Battle, Long> {
    Optional<Battle> findByInviteCode(String inviteCode);

    List<Battle> findAll();

}

