package com.bravi.service;

import com.bravi.constant.CuentaTypeEnum;
import com.bravi.entity.Cuenta;

import java.time.LocalDate;

public interface CuentaFactory {

    Cuenta crearCuenta(String nombre, String email, LocalDate fechaNacimiento, CuentaTypeEnum tipoCuenta);

}
