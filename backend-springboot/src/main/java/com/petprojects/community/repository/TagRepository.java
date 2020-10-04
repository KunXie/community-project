package com.petprojects.community.repository;

import com.petprojects.community.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    Tag findTagByTagName(String tagName);
}
