package com.petprojects.community.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//@Data // Lombok @Data will throw stackoverflow error when generating toString() method
@Getter
@Setter
@Entity
@Table(name = "tag_type")
public class TagType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "tag_type_name")
    private String tagTypeName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tagType")
    private Set<Tag> tags = new HashSet<>();
}
