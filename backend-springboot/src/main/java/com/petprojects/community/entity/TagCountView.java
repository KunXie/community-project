package com.petprojects.community.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tag_count_view")
@Getter
public class TagCountView {

    @Id
    @Column(name = "tag_name")
    private String tagName;

    @Column(name = "tag_count")
    private Integer tagCount;
}
