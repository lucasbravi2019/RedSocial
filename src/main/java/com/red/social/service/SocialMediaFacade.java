package com.red.social.service;

import com.red.social.constant.PublicacionTypeEnum;
import com.red.social.dto.CreacionCuentaDTO;
import com.red.social.entity.Publicacion;

import java.util.Collection;
import java.util.Map;

public interface SocialMediaFacade {

    void logUser(String username);

    boolean listarCuentas();

    boolean verFeed();

    void verPublicaciones();

    void publicar(String contenidoPublicacion, PublicacionTypeEnum tipoPublicacion);

    void verInformacionCuenta();

    void verAlcanceCuenta();

    void cambiarEstadoCuenta();

    void likearPublicacion(Publicacion<?> publicacion);

    void repostearPublicacion(Publicacion<?> publicacion);

    Map<Integer, Publicacion<?>> obtenerPublicacionesSegunTipo(PublicacionTypeEnum publicacionType);

    boolean listarCuentasParaSeguir();

    void seguirUsuario(String username);

    boolean listarSeguidores();

    void dejarDeSeguir(String username);

    void crearNuevoUsuario(CreacionCuentaDTO creacionCuentaDTO);

    void borrarUsuario(String username);

    boolean verPublicacionesParaBorrar();

    void borrarPublicacion(Integer publicacionId);

    void mostrarPublicaciones(Collection<Publicacion<?>> publicaciones);
}
