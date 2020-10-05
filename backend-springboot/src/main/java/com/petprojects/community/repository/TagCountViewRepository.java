package com.petprojects.community.repository;

import com.petprojects.community.entity.TagCountView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagCountViewRepository extends JpaRepository<TagCountView, String> {
    List<TagCountView> findTop10ByOrderByTagCountDesc();
}
