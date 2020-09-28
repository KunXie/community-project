package com.petprojects.community.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "username")
    private String username;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "password")
    private String password;

    @Column(name = "gmt_created")
    private Long gmtCreated;

    @Column(name = "gmt_modified")
    private Long gmtModified;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "token")
    private String token;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Post> posts = new HashSet<>();
}
