package com.petprojects.community.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "like_post")
@Data
@Entity
@IdClass(LikePostId.class)
public class LikePost {
    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Id
    @Column(name = "post_id")
    private Integer postId;

    public LikePost() {

    }

    public LikePost(Integer userId, Integer postId) {
        this.userId = userId;
        this.postId = postId;
    }


}
