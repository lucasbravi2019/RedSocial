package com.red.social.service.impl;

import com.red.social.constant.CuentaTypeEnum;
import com.red.social.dto.CreacionCuentaDTO;
import com.red.social.entity.Cuenta;
import com.red.social.entity.CuentaEmpresa;
import com.red.social.entity.CuentaNormal;
import com.red.social.entity.CuentaPopular;
import com.red.social.service.CuentaFactory;

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
