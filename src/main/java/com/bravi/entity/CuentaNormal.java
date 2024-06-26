package com.bravi.entity;

import com.bravi.constant.CuentaTypeEnum;
import com.bravi.constant.PublicacionLengthEnum;
import com.bravi.event.SeguidorCuentaNormal;

import java.time.LocalDate;

public class CuentaNormal extends Cuenta {

    public CuentaNormal() {
        super(new SeguidorCuentaNormal());
    }

    @Override
    public void publicar(Publicacion<?> publicacion) {
        Contenido<?> contenido = publicacion.getContenido();
        contenido.validarContenido(this);
        feed.publicar(publicacion);

        if (publicacion.getAutor().equals(this))
            seguidores.publicarPublicacion(publicacion);
    }

    @Override
    public boolean canFollow() {
        return true;
    }

    @Override
    public CuentaTypeEnum getCuentaType() {
        return CuentaTypeEnum.NORMAL;
    }

    @Override
    public int getPublicacionMaxCharacters() {
        return PublicacionLengthEnum.SHORT_TEXT.getPublicacionLength();
    }

    public static CuentaNormalBuilder builder() {
        return new CuentaNormalBuilder();
    }

    public static class CuentaNormalBuilder {

        private CuentaNormal cuentaNormal;

        public CuentaNormalBuilder() {
            this.cuentaNormal = new CuentaNormal();
        }

        public CuentaNormalBuilder nombre(String name) {
            this.cuentaNormal.setNombre(name);
            return this;
        }

        public CuentaNormalBuilder email(String email) {
            this.cuentaNormal.setEmail(email);
            return this;
        }

        public CuentaNormalBuilder fechaNacimiento(LocalDate fechaNacimiento) {
            this.cuentaNormal.setFechaNacimiento(fechaNacimiento);
            return this;
        }

        public CuentaNormalBuilder username(String username) {
            this.cuentaNormal.setUsername(username);
            return this;
        }

        public CuentaNormal build() {
            return this.cuentaNormal;
        }

    }
}
