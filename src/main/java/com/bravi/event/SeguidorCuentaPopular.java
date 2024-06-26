package com.bravi.event;

import com.bravi.constant.CuentaTypeEnum;
import com.bravi.entity.Cuenta;
import com.bravi.entity.Publicacion;
import com.bravi.exception.SeguidorInvalidException;

import java.util.Set;

public class SeguidorCuentaPopular extends Seguidores {
    @Override
    public void follow(Set<Publicacion<?>> publicaciones, Cuenta cuenta) {
        if (CuentaTypeEnum.EMPRESA.equals(cuenta.getCuentaType()))
            throw new SeguidorInvalidException("Cuenta de seguidor es cuenta empresa");

        super.follow(publicaciones, cuenta);
    }

}
