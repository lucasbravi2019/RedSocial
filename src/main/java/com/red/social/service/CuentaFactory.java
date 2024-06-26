package com.red.social.service;

import com.red.social.constant.CuentaTypeEnum;
import com.red.social.dto.CreacionCuentaDTO;
import com.red.social.entity.Cuenta;

public interface CuentaFactory {

    Cuenta crearCuenta(CreacionCuentaDTO creacionCuentaDTO, CuentaTypeEnum tipoCuenta);

}
