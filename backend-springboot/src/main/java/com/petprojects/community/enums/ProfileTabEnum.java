package com.petprojects.community.enums;

public enum ProfileTabEnum {
    QUESTION("question"),
    NOTIFICATION("notification"),
    PERSONAL_INFO("personal-info")
    ;
    private final String value;

    ProfileTabEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
