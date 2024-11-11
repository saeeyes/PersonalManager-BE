package com.sejong.project.pm.battle.repository;

import com.sejong.project.pm.battle.service.BattlePhrase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BattlePhraseRepository extends JpaRepository<BattlePhrase, Long> {
    List<BattlePhrase> findByState(BattlePhrase.State state);
}
