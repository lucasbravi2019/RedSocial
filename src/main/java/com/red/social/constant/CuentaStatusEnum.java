package com.red.social.constant;

public enum CuentaStatusEnum {

    ABIERTA("Abierta"),

    SUSPENDIDA("Suspendida");

    private final String status;

    CuentaStatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
