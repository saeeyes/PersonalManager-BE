package com.sejong.project.pm.post.repository;

import com.sejong.project.pm.member.Member;
import com.sejong.project.pm.post.Post;
import com.sejong.project.pm.post.PostApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostApplicationRepository extends JpaRepository<PostApplication, Long> {
    int countByPostId(Post postId);
    List<PostApplication> findByMemberId(Member memberId);

    int countByMemberIdAndPostId(Member memberId, Post postId);
}
