package com.bravi.event;

import com.bravi.constant.CuentaTypeEnum;
import com.bravi.entity.Cuenta;
import com.bravi.exception.SeguidorInvalidException;

public class SeguidorCuentaNormal extends Seguidores {

    @Override
    public void follow(Cuenta cuenta) {
        if (!CuentaTypeEnum.NORMAL.equals(cuenta.getCuentaType()))
            throw new SeguidorInvalidException("Cuenta de seguidor no es cuenta normal");

        super.follow(cuenta);
    }

}
