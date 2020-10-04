package com.petprojects.community.repository;

import com.petprojects.community.entity.TagType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagTypeRepository extends JpaRepository<TagType, Integer> {
}
