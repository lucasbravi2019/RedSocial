package com.red.social.entity;

import com.red.social.constant.CuentaTypeEnum;
import com.red.social.constant.PublicacionLengthEnum;
import com.red.social.event.SeguidorCuentaPopular;

import java.time.LocalDate;

public class CuentaPopular extends Cuenta {

    public CuentaPopular() {
        super(new SeguidorCuentaPopular());
    }

    @Override
    public void publicar(Publicacion<?> publicacion) {
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
        return CuentaTypeEnum.POPULAR;
    }

    @Override
    public int getPublicacionMaxCharacters() {
        return PublicacionLengthEnum.EXTENDED_TEXT.getPublicacionLength();
    }

    public static CuentaPopularBuilder builder() {
        return new CuentaPopularBuilder();
    }

    public static class CuentaPopularBuilder {

        private CuentaPopular cuentaPopular;

        public CuentaPopularBuilder() {
            this.cuentaPopular = new CuentaPopular();
        }

        public CuentaPopularBuilder nombre(String name) {
            this.cuentaPopular.setNombre(name);
            return this;
        }

        public CuentaPopularBuilder email(String mail) {
            this.cuentaPopular.setEmail(mail);
            return this;
        }

        public CuentaPopularBuilder fechaNacimiento(LocalDate fechaNacimientoUsuario) {
            this.cuentaPopular.setFechaNacimiento(fechaNacimientoUsuario);
            return this;
        }

        public CuentaPopularBuilder username(String username) {
            this.cuentaPopular.setUsername(username);
            return this;
        }

        public CuentaPopular build() {
            return this.cuentaPopular;
        }

    }


}
