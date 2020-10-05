package com.petprojects.community.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "sub_reply")
@Getter
@Setter
public class SubReply {

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
    @JoinColumn(name = "primary_reply_id")
    private PrimaryReply primaryReply;

    @Override
    public String toString() {
        return "SubReply{" +
                "id=" + id +
                ", gmtCreated=" + gmtCreated +
                ", gmtModified=" + gmtModified +
                ", content='" + content + '\'' +
                ", replyCount=" + replyCount +
                ", likeCount=" + likeCount +
                ", userId=" + user.getId() +
                ", primaryReplyId=" + primaryReply.getId() +
                '}';
    }
}
