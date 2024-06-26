package com.red.social.event;

import com.red.social.constant.CuentaTypeEnum;
import com.red.social.entity.Cuenta;
import com.red.social.entity.Publicacion;
import com.red.social.exception.SeguidorInvalidException;

import java.util.Set;

public class SeguidorCuentaNormal extends Seguidores {

    @Override
    public void follow(Set<Publicacion<?>> publicaciones, Cuenta cuenta) {
        if (!CuentaTypeEnum.NORMAL.equals(cuenta.getCuentaType()))
            throw new SeguidorInvalidException("Cuenta de seguidor no es cuenta normal");

        super.follow(publicaciones, cuenta);
    }

}
