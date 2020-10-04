package com.petprojects.community.repository;

import com.petprojects.community.entity.PostList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostListRepository extends JpaRepository<PostList, Integer> {
    Page<PostList> findAll(Pageable pageable);

    Page<PostList> findAllByGmtModifiedGreaterThanEqual(Long gmtModified, Pageable pageable);
}
