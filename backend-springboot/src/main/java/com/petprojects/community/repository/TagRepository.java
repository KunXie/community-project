package com.petprojects.community.repository;

import com.petprojects.community.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Tag findTagByTagName(String tagName);
}
