package com.bravi.constant;

public enum PublicacionLengthEnum {

    EXTENDED_TEXT(300),
    SHORT_TEXT(150);

    private final int publicacionLength;


    PublicacionLengthEnum(int publicacionLength) {
        this.publicacionLength = publicacionLength;
    }

    public int getPublicacionLength() {
        return publicacionLength;
    }
}
