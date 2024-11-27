package com.sejong.project.pm.weight.Repository;

import com.sejong.project.pm.weight.Weight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface WeightRepository extends JpaRepository<Weight, Long> {
    Weight findWeightByMember_IdAndToday(Long memberId, LocalDate time);
    @Query("SELECT w FROM Weight w WHERE MONTH(w.today) = :month AND w.member.id = :memberId")
    List<Weight> findWeightsByCurrentMonthAndMember_Id(@Param("month") int month, @Param("memberId") Long memberId);

    Weight findTopByMember_IdOrderByTodayDesc(Long memberId);

}
