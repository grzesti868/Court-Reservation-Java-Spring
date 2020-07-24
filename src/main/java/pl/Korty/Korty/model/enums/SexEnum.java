package com.squash.squashcourts.model.enums;

public enum SexEnum {
    Male("M"), Female("M");

    private String code;

    private SexEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
