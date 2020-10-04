package com.petprojects.community.repository;

import com.petprojects.community.entity.Post;
import com.petprojects.community.entity.PrimaryReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrimaryReplyRepository extends JpaRepository<PrimaryReply, Integer> {
    Page<PrimaryReply> findAllByPost(Post post, Pageable pageable);
}
