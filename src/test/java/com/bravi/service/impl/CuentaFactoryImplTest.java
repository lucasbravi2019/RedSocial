package com.bravi.service.impl;

import com.bravi.constant.CuentaTypeEnum;
import com.bravi.entity.Cuenta;
import com.bravi.entity.CuentaEmpresa;
import com.bravi.entity.CuentaNormal;
import com.bravi.entity.CuentaPopular;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CuentaFactoryImplTest {

    private final CuentaFactoryImpl cuentaFactory = new CuentaFactoryImpl();

    @Test
    void crearCuentaPopular() {
        Cuenta cuenta = cuentaFactory.crearCuenta("User 1", "user1@mail.com", LocalDate.of(2023, 1, 1),
                CuentaTypeEnum.POPULAR);

        assertEquals("User 1", cuenta.getNombre());
        assertEquals("user1@mail.com", cuenta.getEmail());
        assertEquals("user1", cuenta.getUsername());
        assertEquals(LocalDate.of(2023, 1, 1), cuenta.getFechaNacimiento());
        assertEquals(CuentaTypeEnum.POPULAR, cuenta.getCuentaType());
        assertEquals(CuentaPopular.class, cuenta.getClass());
    }

    @Test
    void crearCuentaEmpresa() {
        Cuenta cuenta = cuentaFactory.crearCuenta("User 1", "user1@mail.com", LocalDate.of(2023, 1, 1),
                CuentaTypeEnum.EMPRESA);

        assertEquals("User 1", cuenta.getNombre());
        assertEquals("user1@mail.com", cuenta.getEmail());
        assertEquals("user1", cuenta.getUsername());
        assertEquals(LocalDate.of(2023, 1, 1), cuenta.getFechaNacimiento());
        assertEquals(CuentaTypeEnum.EMPRESA, cuenta.getCuentaType());
        assertEquals(CuentaEmpresa.class, cuenta.getClass());
    }

    @Test
    void crearCuentaNormal() {
        Cuenta cuenta = cuentaFactory.crearCuenta("User 1", "user1@mail.com", LocalDate.of(2023, 1, 1),
                CuentaTypeEnum.NORMAL);

        assertEquals("User 1", cuenta.getNombre());
        assertEquals("user1@mail.com", cuenta.getEmail());
        assertEquals("user1", cuenta.getUsername());
        assertEquals(LocalDate.of(2023, 1, 1), cuenta.getFechaNacimiento());
        assertEquals(CuentaTypeEnum.NORMAL, cuenta.getCuentaType());
        assertEquals(CuentaNormal.class, cuenta.getClass());
    }

    @Test
    void crearCuentaNormalMailInvalido() {
        Cuenta cuenta = cuentaFactory.crearCuenta("User 1", "user1", LocalDate.of(2023, 1, 1),
                CuentaTypeEnum.NORMAL);

        assertEquals("User 1", cuenta.getNombre());
        assertEquals("user1", cuenta.getEmail());
        assertEquals("user1", cuenta.getUsername());
        assertEquals(LocalDate.of(2023, 1, 1), cuenta.getFechaNacimiento());
        assertEquals(CuentaTypeEnum.NORMAL, cuenta.getCuentaType());
        assertEquals(CuentaNormal.class, cuenta.getClass());
    }

}