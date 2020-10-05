package com.petprojects.community.repository;

import com.petprojects.community.entity.LikePost;
import com.petprojects.community.entity.LikePostId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikePostRepository extends JpaRepository<LikePost, LikePostId> {

    @Query("SELECT lp FROM LikePost lp WHERE lp.userId = :userId AND lp.postId = :postId")
    LikePost find(@Param("userId") Integer userId, @Param("postId") Integer postId);
}
