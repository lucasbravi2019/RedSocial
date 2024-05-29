package com.bravi.entity;

import com.bravi.constant.CuentaTypeEnum;
import com.bravi.constant.PublicacionLengthEnum;
import com.bravi.event.SeguidorCuentaEmpresa;

import java.time.LocalDate;

public class CuentaEmpresa extends Cuenta {

    public CuentaEmpresa() {
        super(new SeguidorCuentaEmpresa());
    }

    @Override
    public void publicar(Publicacion<?> publicacion) {
        feed.publicar(publicacion);

        if (publicacion.getAutor().equals(this))
            seguidores.publicarPublicacion(publicacion);
    }

    @Override
    public CuentaTypeEnum getCuentaType() {
        return CuentaTypeEnum.EMPRESA;
    }

    @Override
    public int getPublicacionMaxCharacters() {
        return PublicacionLengthEnum.EXTENDED_TEXT.getPublicacionLength();
    }

    public static CuentaEmpresaBuilder builder() {
        return new CuentaEmpresaBuilder();
    }

    public static class CuentaEmpresaBuilder {

        private CuentaEmpresa cuentaEmpresa;

        public CuentaEmpresaBuilder() {
            this.cuentaEmpresa = new CuentaEmpresa();
        }

        public CuentaEmpresaBuilder nombre(String nombre) {
            this.cuentaEmpresa.setNombre(nombre);
            return this;
        }

        public CuentaEmpresaBuilder email(String email) {
            this.cuentaEmpresa.setEmail(email);
            return this;
        }

        public CuentaEmpresaBuilder fechaNacimiento(LocalDate fechaNacimiento) {
            this.cuentaEmpresa.setFechaNacimiento(fechaNacimiento);
            return this;
        }

        public CuentaEmpresaBuilder username(String username) {
            this.cuentaEmpresa.setUsername(username);
            return this;
        }

        public CuentaEmpresa build() {
            return this.cuentaEmpresa;
        }

    }

}
