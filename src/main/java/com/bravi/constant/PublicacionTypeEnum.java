package com.bravi.constant;

public enum PublicacionTypeEnum {

    LIKEABLE("L"),
    REPOSTEABLE("R"),
    AMBAS("A"),
    NINGUNA("N");

    PublicacionTypeEnum(String userInputOperation) {
        this.userInputOperation = userInputOperation;
    }

    private final String userInputOperation;

    public String getUserInputOperation() {
        return userInputOperation;
    }
}
