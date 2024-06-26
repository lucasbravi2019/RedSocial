package com.bravi.service.impl;

import com.bravi.constant.CuentaTypeEnum;
import com.bravi.entity.Cuenta;
import com.bravi.entity.CuentaEmpresa;
import com.bravi.entity.CuentaNormal;
import com.bravi.entity.CuentaPopular;
import com.bravi.repository.CuentaRepository;
import com.bravi.repository.PublicacionRepository;
import com.bravi.service.CuentaFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CuentaServiceImplTest {

    @Mock
    private CuentaRepository cuentaRepository;
    @Mock
    private PublicacionRepository publicacionRepository;
    @Mock
    private CuentaFactory cuentaFactory;
    @InjectMocks
    private CuentaServiceImpl cuentaService;

    @Test
    void crearCuentaPopular() {
        Cuenta cuenta = new CuentaPopular();
        when(cuentaFactory.crearCuenta("User 1", "user1@mail.com", LocalDate.of(2023, 1, 1),
                CuentaTypeEnum.POPULAR)).thenReturn(cuenta);

        cuentaService.crearCuenta("User 1", "user1@mail.com", LocalDate.of(2023, 1, 1), 'P');

        verify(cuentaRepository, times(1)).save(cuenta);
    }

    @Test
    void crearCuentaEmpresa() {
        Cuenta cuenta = new CuentaEmpresa();
        when(cuentaFactory.crearCuenta("User 1", "user1@mail.com", LocalDate.of(2023, 1, 1),
                CuentaTypeEnum.EMPRESA)).thenReturn(cuenta);

        cuentaService.crearCuenta("User 1", "user1@mail.com", LocalDate.of(2023, 1, 1), 'E');

        verify(cuentaRepository, times(1)).save(cuenta);
    }

    @Test
    void crearCuentaNormal() {
        Cuenta cuenta = new CuentaNormal();
        when(cuentaFactory.crearCuenta("User 1", "user1@mail.com", LocalDate.of(2023, 1, 1),
                CuentaTypeEnum.NORMAL)).thenReturn(cuenta);

        cuentaService.crearCuenta("User 1", "user1@mail.com", LocalDate.of(2023, 1, 1), 'N');

        verify(cuentaRepository, times(1)).save(cuenta);
    }

    @Test
    void crearCuentaInvalida() {
        assertDoesNotThrow(() ->
                cuentaService.crearCuenta("User 1", "user1@mail.com", LocalDate.of(2023, 1, 1), 'S'));

        verify(cuentaRepository, never()).save(any());
    }

}