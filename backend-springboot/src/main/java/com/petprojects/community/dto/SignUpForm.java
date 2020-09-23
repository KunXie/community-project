package com.petprojects.community.dto;

import lombok.Data;

@Data
public class SignUpForm {
    private String firstname;
    private String lastname;
    private String username;
    private String emailAddress;
    private String password;
}
