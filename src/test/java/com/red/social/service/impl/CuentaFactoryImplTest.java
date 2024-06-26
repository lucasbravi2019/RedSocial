package com.red.social.service.impl;

import com.red.social.constant.CuentaTypeEnum;
import com.red.social.dto.CreacionCuentaDTO;
import com.red.social.entity.Cuenta;
import com.red.social.entity.CuentaEmpresa;
import com.red.social.entity.CuentaNormal;
import com.red.social.entity.CuentaPopular;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CuentaFactoryImplTest {

    private final CuentaFactoryImpl cuentaFactory = new CuentaFactoryImpl();

    @Test
    void crearCuentaPopular() {
        CreacionCuentaDTO dto = CreacionCuentaDTO.builder()
                .nombre("User 1")
                .email("user1@mail.com")
                .fechaNacimiento(LocalDate.of(2023, 1, 1))
                .tipoCuenta('P')
                .build();

        Cuenta cuenta = cuentaFactory.crearCuenta(dto, CuentaTypeEnum.POPULAR);

        assertEquals("User 1", cuenta.getNombre());
        assertEquals("user1@mail.com", cuenta.getEmail());
        assertEquals("user1", cuenta.getUsername());
        assertEquals(LocalDate.of(2023, 1, 1), cuenta.getFechaNacimiento());
        Assertions.assertEquals(CuentaTypeEnum.POPULAR, cuenta.getCuentaType());
        assertEquals(CuentaPopular.class, cuenta.getClass());
    }

    @Test
    void crearCuentaEmpresa() {
        CreacionCuentaDTO dto = CreacionCuentaDTO.builder()
                .nombre("User 1")
                .email("user1@mail.com")
                .fechaNacimiento(LocalDate.of(2023, 1, 1))
                .tipoCuenta('E')
                .direccion("Dirección 1")
                .telefono("+54 261 999 9999")
                .build();

        CuentaEmpresa cuenta = (CuentaEmpresa) cuentaFactory.crearCuenta(dto, CuentaTypeEnum.EMPRESA);

        assertEquals("User 1", cuenta.getNombre());
        assertEquals("user1@mail.com", cuenta.getEmail());
        assertEquals("user1", cuenta.getUsername());
        assertEquals(LocalDate.of(2023, 1, 1), cuenta.getFechaNacimiento());
        assertEquals("Dirección 1", cuenta.getDireccion());
        assertEquals("+54 261 999 9999", cuenta.getTelefono());
        Assertions.assertEquals(CuentaTypeEnum.EMPRESA, cuenta.getCuentaType());
        assertEquals(CuentaEmpresa.class, cuenta.getClass());
    }

    @Test
    void crearCuentaNormal() {
        CreacionCuentaDTO dto = CreacionCuentaDTO.builder()
                .nombre("User 1")
                .email("user1@mail.com")
                .fechaNacimiento(LocalDate.of(2023, 1, 1))
                .tipoCuenta('N')
                .build();

        Cuenta cuenta = cuentaFactory.crearCuenta(dto, CuentaTypeEnum.NORMAL);

        assertEquals("User 1", cuenta.getNombre());
        assertEquals("user1@mail.com", cuenta.getEmail());
        assertEquals("user1", cuenta.getUsername());
        assertEquals(LocalDate.of(2023, 1, 1), cuenta.getFechaNacimiento());
        Assertions.assertEquals(CuentaTypeEnum.NORMAL, cuenta.getCuentaType());
        assertEquals(CuentaNormal.class, cuenta.getClass());
    }

    @Test
    void crearCuentaNormalMailInvalido() {
        CreacionCuentaDTO dto = CreacionCuentaDTO.builder()
                .nombre("User 1")
                .email("user1")
                .fechaNacimiento(LocalDate.of(2023, 1, 1))
                .tipoCuenta('N')
                .build();

        Cuenta cuenta = cuentaFactory.crearCuenta(dto, CuentaTypeEnum.NORMAL);

        assertEquals("User 1", cuenta.getNombre());
        assertEquals("user1", cuenta.getEmail());
        assertEquals("user1", cuenta.getUsername());
        assertEquals(LocalDate.of(2023, 1, 1), cuenta.getFechaNacimiento());
        Assertions.assertEquals(CuentaTypeEnum.NORMAL, cuenta.getCuentaType());
        assertEquals(CuentaNormal.class, cuenta.getClass());
    }

}