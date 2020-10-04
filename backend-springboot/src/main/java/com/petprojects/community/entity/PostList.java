package com.petprojects.community.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "post_list_view")
@Entity
@Getter
@Setter
public class PostList {
    @Id
    @Column(name = "post_id")
    private Integer postId;

    @Column(name = "title")
    private String title;

    @Column(name = "gmt_modified")
    private Long gmtModified;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "reply_count")
    private Integer replyCount;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @JsonIgnoreProperties(value = {"tagType", "posts"})
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"), // 我把这个写反了, 真是坑爹
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private final Set<Tag> tags = new HashSet<>();
}
