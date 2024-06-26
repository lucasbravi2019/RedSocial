package com.bravi.service;

import com.bravi.entity.Publicacion;

import java.util.Collection;
import java.util.List;

public interface MenuService {

    void mostrarInicio();

    void mostrarMenu();

    void mostrarSubmenu();

    void mostrarMenuFeed();

    void mostrarMenuLogin();

    void mostrarMenuPublicacion();

    void mostrarMenuSeguirUsuario();

    void mostrarMenuDejarDeSeguirUsuario();

    void pedirNombre();

    void pedirEmail();

    void pedirFechaNacimiento();

    void mostrarMenuBorrarCuenta();

    void pedirTipoCuenta();

    void pedirTelefono();

    void pedirDireccion();

    void mostrarMenuBorrarPublicacion();
}
