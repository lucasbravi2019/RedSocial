package com.bravi.service;

import com.bravi.constant.PublicacionTypeEnum;
import com.bravi.entity.Publicacion;

import java.util.Map;

public interface SocialMediaFacade {

    void logUser(String username);

    void listarCuentas();

    void verFeed();

    void verPublicaciones();

    void publicar(String contenidoPublicacion, PublicacionTypeEnum tipoPublicacion);

    void verInformacionCuenta();

    void verAlcanceCuenta();

    void cambiarEstadoCuenta();

    void likearPublicacion(Publicacion<?> publicacion);

    void repostearPublicacion(Publicacion<?> publicacion);

    Map<Integer, Publicacion<?>> obtenerPublicacionesSegunTipo(PublicacionTypeEnum publicacionType);
}
