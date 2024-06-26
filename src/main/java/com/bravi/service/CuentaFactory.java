package com.bravi.service;

import com.bravi.constant.CuentaTypeEnum;
import com.bravi.dto.CreacionCuentaDTO;
import com.bravi.entity.Cuenta;

import java.time.LocalDate;

public interface CuentaFactory {

    Cuenta crearCuenta(CreacionCuentaDTO creacionCuentaDTO, CuentaTypeEnum tipoCuenta);

}
