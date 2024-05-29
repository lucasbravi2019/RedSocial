package com.bravi.event;

import com.bravi.constant.CuentaTypeEnum;
import com.bravi.entity.Cuenta;
import com.bravi.exception.SeguidorInvalidException;

public class SeguidorCuentaPopular extends Seguidores {
    @Override
    public void follow(Cuenta cuenta) {
        if (CuentaTypeEnum.EMPRESA.equals(cuenta.getCuentaType()))
            throw new SeguidorInvalidException("Cuenta de seguidor es cuenta empresa");

        super.follow(cuenta);
    }

}
