package com.red.social.service.impl;

import com.red.social.constant.CuentaTypeEnum;
import com.red.social.dto.CreacionCuentaDTO;
import com.red.social.entity.Cuenta;
import com.red.social.entity.CuentaEmpresa;
import com.red.social.entity.CuentaNormal;
import com.red.social.entity.CuentaPopular;
import com.red.social.repository.CuentaRepository;
import com.red.social.repository.PublicacionRepository;
import com.red.social.service.CuentaFactory;
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