package com.petprojects.community.repository;

import com.petprojects.community.entity.Post;
import com.petprojects.community.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    Integer countRepliesByPost(Post post);
}
