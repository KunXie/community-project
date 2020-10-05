package com.petprojects.community.repository;

import com.petprojects.community.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Page<Post> findAll(Pageable pageable);

    //    void saveOrUpdate(Post post);
    Optional<Post> findById(Integer id);

    @Transactional // this annotation required for DML
    @Modifying // this annotation required for DML
    @Query(value = "UPDATE Post SET viewCount = viewCount + 1 WHERE id = :postId")
    void incrementViewCount(@Param("postId") Integer postId);
}
