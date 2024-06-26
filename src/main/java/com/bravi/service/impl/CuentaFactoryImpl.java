package com.bravi.service.impl;

import com.bravi.constant.CuentaTypeEnum;
import com.bravi.entity.Cuenta;
import com.bravi.entity.CuentaEmpresa;
import com.bravi.entity.CuentaNormal;
import com.bravi.entity.CuentaPopular;
import com.bravi.service.CuentaFactory;

import java.time.LocalDate;

public class CuentaFactoryImpl implements CuentaFactory {

    @Override
    public Cuenta crearCuenta(String nombre, String email, LocalDate fechaNacimiento, CuentaTypeEnum tipoCuenta) {
        String username = email.split("@")[0];
        if (CuentaTypeEnum.EMPRESA.equals(tipoCuenta))
            return CuentaEmpresa.builder()
                    .nombre(nombre)
                    .email(email)
                    .username(username)
                    .fechaNacimiento(fechaNacimiento)
                    .build();

        if (CuentaTypeEnum.POPULAR.equals(tipoCuenta))
            return CuentaPopular.builder()
                    .nombre(nombre)
                    .email(email)
                    .username(username)
                    .fechaNacimiento(fechaNacimiento)
                    .build();

        else
            return CuentaNormal.builder()
                    .nombre(nombre)
                    .email(email)
                    .username(username)
                    .fechaNacimiento(fechaNacimiento)
                    .build();
    }
}
