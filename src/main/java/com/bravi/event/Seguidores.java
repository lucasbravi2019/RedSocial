package com.bravi.event;

import com.bravi.entity.Cuenta;
import com.bravi.entity.Publicacion;
import com.bravi.exception.SeguidorInvalidException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Seguidores {

    private final Set<Cuenta> seguidores = new HashSet<>();
    private Cuenta cuenta;

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public List<Cuenta> getSeguidores() {
        return new ArrayList<>(seguidores);
    }

    public void follow(Set<Publicacion<?>> publicaciones, Cuenta cuenta) {
        if (!cuenta.canFollow())
            throw new SeguidorInvalidException(String.format("La cuenta %s no tiene permitido seguir cuentas", cuenta.getUsername()));

        this.seguidores.add(cuenta);
        for (Publicacion<?> publicacion : publicaciones) {
            cuenta.getFeed().publicar(publicacion);
        }
    }

    public void unfollow(Set<Publicacion<?>> publicaciones, Cuenta cuenta) {
        if (cuenta != null) {
            this.seguidores.remove(cuenta);
            for (Publicacion<?> publicacion : publicaciones) {
                cuenta.getFeed().borrarPublicacion(publicacion);
            }
        }
    }

    public void publicarPublicacion(Publicacion<?> publicacion) {
        for (Cuenta cuenta : getSeguidores()) {
            cuenta.publicar(publicacion);
        }
    }

}
