package com.sejong.project.pm.post.repository;

import com.sejong.project.pm.battle.Battle;
import com.sejong.project.pm.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
