package com.bravi.constant;

public enum CuentaTypeEnum {

    EMPRESA("Empresa"),
    POPULAR("Popular"),
    NORMAL("Normal");

    private final String tipoCuenta;

    CuentaTypeEnum(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }
}
