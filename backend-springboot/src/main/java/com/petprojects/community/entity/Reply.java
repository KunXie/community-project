package com.petprojects.community.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "reply")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "content")
    private String content;

    @Column(name = "gmt_created")
    private Long gmtCreated;

    @Column(name = "gmt_modified")
    private Long gmtModified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_from_user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "reply_to_post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "reply_to_reply_id")
    private Reply reply;

    // self relationship
    @JsonIgnoreProperties(value = {"post", "reply", "subReplies"})
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reply")
    private Set<Reply> subReplies = new HashSet<>();
}
