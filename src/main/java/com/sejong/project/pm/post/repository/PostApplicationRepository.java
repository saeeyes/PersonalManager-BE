package com.sejong.project.pm.post.repository;

import com.sejong.project.pm.post.Post;
import com.sejong.project.pm.post.PostApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostApplicationRepository extends JpaRepository<PostApplication, Long> {
    int countByPost_Id(Long post);
    List<PostApplication> findByMember_Id(Long member);
    int countByMember_IdAndPost_Id(Long member, Long post);

    Optional<PostApplication> findByMember_IdAndPost_Id(Long member, Long post);
}
