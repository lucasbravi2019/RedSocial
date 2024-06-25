package com.bravi.constant;

import com.bravi.entity.Cuenta;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum PublicacionLengthEnum {

    EXTENDED_TEXT(300, "Extendido"),
    SHORT_TEXT(150, "Pequeño");

    private final int publicacionLength;
    private final String publicacionDescripcion;
    private static final Map<Integer, PublicacionLengthEnum> types = new HashMap<>();

    static {
        for (PublicacionLengthEnum publicacionLengthEnum : EnumSet.allOf(PublicacionLengthEnum.class)) {
            types.put(publicacionLengthEnum.getPublicacionLength(), publicacionLengthEnum);
        }
    }

    PublicacionLengthEnum(int publicacionLength, String publicacionDescripcion) {
        this.publicacionLength = publicacionLength;
        this.publicacionDescripcion = publicacionDescripcion;
    }

    public int getPublicacionLength() {
        return publicacionLength;
    }

    public String getPublicacionDescripcion() {
        return publicacionDescripcion;
    }

    public static String getDescripcionPublicacionPorCuenta(Cuenta cuenta) {
        int publicacionMaxCharacters = cuenta.getPublicacionMaxCharacters();
        PublicacionLengthEnum publicacionLengthEnum = types.get(publicacionMaxCharacters);
        if (publicacionLengthEnum == null)
            throw new IllegalStateException("Not implemented");

        return publicacionLengthEnum.getPublicacionDescripcion();
    }
}
