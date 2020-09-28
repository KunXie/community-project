package com.petprojects.community.dto;

import lombok.Data;

@Data
public class PostDraft {
    private Integer id;
    private String title;
    private String content;
    /* joined string of tag names separated by ', ' */
    private String tagNames;
}
