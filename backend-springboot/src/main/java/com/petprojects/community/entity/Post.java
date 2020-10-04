package com.petprojects.community.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "post")
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "gmt_created")
    private Long gmtCreated;

    @Column(name = "gmt_modified")
    private Long gmtModified;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "like_count")
    private Integer likeCount;

    // 这里如果用FetchType.EAGER 会报错
    // No serializer found for class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor
    @JsonIgnoreProperties(value = {"emailAddress", "password", "token", "posts"})
    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnoreProperties(value = {"tagType", "posts"})
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"), // 我把这个写反了, 真是坑爹
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private Set<Reply> replies = new HashSet<>();
}
