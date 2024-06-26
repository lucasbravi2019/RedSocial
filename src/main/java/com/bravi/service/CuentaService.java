package com.bravi.service;

import com.bravi.constant.PublicacionTypeEnum;
import com.bravi.entity.Contenido;
import com.bravi.entity.Cuenta;
import com.bravi.entity.Publicacion;

import java.time.LocalDate;
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

    void crearCuenta(String nombre, String email, LocalDate fechaNacimiento, Character tipoCuenta);

    void borrarCuenta(String username);
}
