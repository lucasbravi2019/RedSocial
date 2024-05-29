package com.bravi.service;

import com.bravi.constant.PublicacionTypeEnum;
import com.bravi.entity.Contenido;
import com.bravi.entity.Cuenta;
import com.bravi.entity.Publicacion;

import java.util.List;
import java.util.Map;

public interface CuentaService {

    void guardarCuenta(Cuenta cuenta);

    void guardarCuentas(List<Cuenta> cuentas);

    void logUser(String username);

    Cuenta findCuenta(String username);

    void addSeguidor(String accountUsername, String followerUsername);

    Publicacion<?> publicarContenido(Contenido<?> contenido, PublicacionTypeEnum tipoPublicacion);

    void republicarContenido(Publicacion<?> publicacion);

    void likePublicacion(Publicacion<?> publicacion);

    void mostrarAlcance();

    void verCuentas();

    void mostrarInformacionCuenta();

    void alternarEstado();

    Map<Integer, Publicacion<?>> obtenerPublicacionesSegunTipo(PublicacionTypeEnum publicacionType);

}
