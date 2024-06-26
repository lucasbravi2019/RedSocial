package com.bravi.service.impl;

import com.bravi.constant.CuentaTypeEnum;
import com.bravi.dto.CreacionCuentaDTO;
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
        assertEquals(CuentaTypeEnum.POPULAR, cuenta.getCuentaType());
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
        assertEquals(CuentaTypeEnum.EMPRESA, cuenta.getCuentaType());
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
        assertEquals(CuentaTypeEnum.NORMAL, cuenta.getCuentaType());
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
        assertEquals(CuentaTypeEnum.NORMAL, cuenta.getCuentaType());
        assertEquals(CuentaNormal.class, cuenta.getClass());
    }

}