package com.bravi.constant;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum CuentaTypeEnum {

    EMPRESA("Empresa"),
    POPULAR("Popular"),
    NORMAL("Normal");

    private final String tipoCuenta;
    private static final Map<Character, CuentaTypeEnum> types = new HashMap<>();

    static {
        for (CuentaTypeEnum cuentaTypeEnum : EnumSet.allOf(CuentaTypeEnum.class)) {
            types.put(cuentaTypeEnum.getTipoCuenta().toLowerCase().charAt(0), cuentaTypeEnum);
        }
    }


    CuentaTypeEnum(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public static List<Character> getValidTypes() {
        return EnumSet.allOf(CuentaTypeEnum.class).stream()
                .map(type -> type.getTipoCuenta().toLowerCase().charAt(0))
                .collect(Collectors.toList());
    }

    public static CuentaTypeEnum getTipoCuentaPorCaracter(Character tipoCuenta) {
        CuentaTypeEnum cuentaTypeEnum = types.get(Character.toLowerCase(tipoCuenta));
        if (cuentaTypeEnum == null)
            throw new IllegalStateException("Tipo de cuenta inválido");

        return cuentaTypeEnum;
    }
}
