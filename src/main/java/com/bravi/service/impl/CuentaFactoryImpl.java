package com.bravi.service.impl;

import com.bravi.constant.CuentaTypeEnum;
import com.bravi.dto.CreacionCuentaDTO;
import com.bravi.entity.Cuenta;
import com.bravi.entity.CuentaEmpresa;
import com.bravi.entity.CuentaNormal;
import com.bravi.entity.CuentaPopular;
import com.bravi.service.CuentaFactory;

import java.time.LocalDate;

public class CuentaFactoryImpl implements CuentaFactory {

    @Override
    public Cuenta crearCuenta(CreacionCuentaDTO creacionCuentaDTO, CuentaTypeEnum tipoCuenta) {
        String username = creacionCuentaDTO.getEmail().split("@")[0];
        String nombre = creacionCuentaDTO.getNombre();
        String email = creacionCuentaDTO.getEmail();
        LocalDate fechaNacimiento = creacionCuentaDTO.getFechaNacimiento();
        if (CuentaTypeEnum.EMPRESA.equals(tipoCuenta)) {
            String direccion = creacionCuentaDTO.getDireccion();
            String telefono = creacionCuentaDTO.getTelefono();
            return CuentaEmpresa.builder()
                    .nombre(nombre)
                    .email(email)
                    .username(username)
                    .fechaNacimiento(fechaNacimiento)
                    .direccion(direccion)
                    .telefono(telefono)
                    .build();
        }

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
