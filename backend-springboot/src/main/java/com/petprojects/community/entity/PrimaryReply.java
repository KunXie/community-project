package com.petprojects.community.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "primary_reply")
@Getter
@Setter
public class PrimaryReply {

    public PrimaryReply() {
        replyCount = 0;
        likeCount = 0;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "gmt_created")
    private Long gmtCreated;

    @Column(name = "gmt_modified")
    private Long gmtModified;

    @Column(name = "content")
    private String content;

    @Column(name = "reply_count")
    private Integer replyCount;

    @Column(name = "like_count")
    private Integer likeCount;

    @JsonIgnoreProperties(value = {"post"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @JsonIgnoreProperties(value = {"primaryReply"})
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "primaryReply")
    private Set<SubReply> subReplies = new HashSet<>();

    @Override
    public String toString() {
        return "PrimaryReply{" +
                "id=" + id +
                ", gmtCreated=" + gmtCreated +
                ", gmtModified=" + gmtModified +
                ", content='" + content + '\'' +
                ", replyCount=" + replyCount +
                ", likeCount=" + likeCount +
                ", user=" + user.getId() +
                ", post=" + post.getId() +
                ", subReplies=" + subReplies.size() +
                '}';
    }
}
