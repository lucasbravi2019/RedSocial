package com.red.social.user;

import com.red.social.entity.Cuenta;

public class LoggedUser {

    private static Cuenta cuenta = null;

    public static Cuenta getUserLogged() {
        return cuenta;
    }

    private static void setCuenta(Cuenta cuenta) {
        LoggedUser.cuenta = cuenta;
    }

    public static void logUser(Cuenta cuenta) {
        setCuenta(cuenta);
    }
}
