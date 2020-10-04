package com.petprojects.community.enums;

public enum IndexTabEnum {
    INTERESTING("interesting"),
    HOT("hot"),
    WEEK("week"),
    MONTH("month")
    ;
    private final String value;

    IndexTabEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
