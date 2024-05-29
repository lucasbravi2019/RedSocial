package com.bravi.entity;

import java.util.List;

public interface Contenido<T> {

    List<String> getEtiquetas();

    void validarContenido(Cuenta cuenta);

    byte[] getContenidoPublicado();

    Class<T> getType();
}
