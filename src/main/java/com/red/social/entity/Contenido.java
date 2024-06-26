package com.red.social.entity;

import java.util.List;

public interface Contenido<T> {

    List<String> getEtiquetas();

    void validarContenido(Cuenta cuenta);

    byte[] getContenidoPublicado();

    Class<T> getType();
}
