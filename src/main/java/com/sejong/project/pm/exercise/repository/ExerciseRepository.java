package com.sejong.project.pm.exercise.repository;

import com.sejong.project.pm.exercise.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Optional<Exercise> findById(long id);
    Optional<Exercise> findByExerciseName(String exerciseName);
    List<Exercise> findByExercisieNameStartingWith(String exerciseName);
}
