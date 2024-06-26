package com.red.social.service;

import com.red.social.constant.PublicacionTypeEnum;
import com.red.social.dto.CreacionCuentaDTO;
import com.red.social.entity.Contenido;
import com.red.social.entity.Cuenta;
import com.red.social.entity.Publicacion;

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

    boolean verCuentas();

    void mostrarInformacionCuenta();

    void alternarEstado();

    Map<Integer, Publicacion<?>> obtenerPublicacionesSegunTipo(PublicacionTypeEnum publicacionType);

    boolean verOtrasCuentas();

    void seguirUsuario(String username);

    List<Cuenta> getSeguidores(Cuenta cuenta);

    void dejarDeSeguir(String username);

    void crearCuenta(CreacionCuentaDTO creacionCuentaDTO);

    void borrarCuenta(String username);
}
