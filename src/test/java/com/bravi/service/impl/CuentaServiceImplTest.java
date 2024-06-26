package com.bravi.service.impl;

import com.bravi.constant.CuentaTypeEnum;
import com.bravi.dto.CreacionCuentaDTO;
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
        CreacionCuentaDTO dto = CreacionCuentaDTO.builder()
                .nombre("User 1")
                .email("user1@mail.com")
                .fechaNacimiento(LocalDate.of(2023, 1, 1))
                .tipoCuenta('P')
                .build();
        Cuenta cuenta = new CuentaPopular();
        when(cuentaFactory.crearCuenta(dto, CuentaTypeEnum.POPULAR)).thenReturn(cuenta);

        cuentaService.crearCuenta(dto);

        verify(cuentaRepository, times(1)).save(cuenta);
    }

    @Test
    void crearCuentaEmpresa() {
        CreacionCuentaDTO dto = CreacionCuentaDTO.builder()
                .nombre("User 1")
                .email("user1@mail.com")
                .fechaNacimiento(LocalDate.of(2023, 1, 1))
                .tipoCuenta('E')
                .build();
        Cuenta cuenta = new CuentaEmpresa();
        when(cuentaFactory.crearCuenta(dto, CuentaTypeEnum.EMPRESA)).thenReturn(cuenta);

        cuentaService.crearCuenta(dto);

        verify(cuentaRepository, times(1)).save(cuenta);
    }

    @Test
    void crearCuentaNormal() {
        CreacionCuentaDTO dto = CreacionCuentaDTO.builder()
                .nombre("User 1")
                .email("user1@mail.com")
                .fechaNacimiento(LocalDate.of(2023, 1, 1))
                .tipoCuenta('N')
                .build();
        Cuenta cuenta = new CuentaNormal();
        when(cuentaFactory.crearCuenta(dto, CuentaTypeEnum.NORMAL)).thenReturn(cuenta);

        cuentaService.crearCuenta(dto);

        verify(cuentaRepository, times(1)).save(cuenta);
    }

    @Test
    void crearCuentaInvalida() {
        CreacionCuentaDTO dto = CreacionCuentaDTO.builder()
                .nombre("User 1")
                .email("user1@mail.com")
                .fechaNacimiento(LocalDate.of(2023, 1, 1))
                .tipoCuenta('S')
                .build();

        assertDoesNotThrow(() -> cuentaService.crearCuenta(dto));

        verify(cuentaRepository, never()).save(any());
    }

}