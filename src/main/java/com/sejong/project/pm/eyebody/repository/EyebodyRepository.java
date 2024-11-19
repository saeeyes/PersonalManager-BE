package com.sejong.project.pm.eyebody.repository;

import com.sejong.project.pm.eyebody.Eyebody;
import com.sejong.project.pm.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EyebodyRepository extends JpaRepository<Eyebody,Long> {
    List<Eyebody> findByMember(Member member);
}
