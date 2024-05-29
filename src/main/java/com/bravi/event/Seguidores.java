package com.bravi.event;

import com.bravi.entity.Cuenta;
import com.bravi.entity.Publicacion;

import java.util.ArrayList;
import java.util.List;

public abstract class Seguidores {

    private final List<Cuenta> seguidores = new ArrayList<>();
    public List<Cuenta> getSeguidores() {
        return seguidores;
    }

    public void follow(Cuenta cuenta) {
        if (cuenta != null)
            this.seguidores.add(cuenta);
    }

    public void unfollow(Cuenta cuenta) {
        if (cuenta != null)
            this.seguidores.remove(cuenta);
    }

    public void publicarPublicacion(Publicacion<?> publicacion) {
        for (Cuenta cuenta : getSeguidores()) {
            cuenta.publicar(publicacion);
        }
    }

}
