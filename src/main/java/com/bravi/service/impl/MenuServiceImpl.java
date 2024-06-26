package com.bravi.service.impl;

import com.bravi.entity.Publicacion;
import com.bravi.service.MenuService;

import java.util.Collection;
import java.util.List;

public class MenuServiceImpl implements MenuService {

    @Override
    public void mostrarInicio() {
        System.out.println("1) Inicializar datos");
        System.out.println("2) Ejecutar aplicación normalmente");
        System.out.println("3) Salir");
    }

    @Override
    public void mostrarMenu() {
        System.out.println("1) Listar todas las cuentas de la red");
        System.out.println("2) Seleccionar una cuenta");
        System.out.println("3) Agregar una cuenta");
        System.out.println("4) Borrar una cuenta");
        System.out.println("5) Salir");
    }

    @Override
    public void mostrarSubmenu() {
        System.out.println("1) Ver feed");
        System.out.println("2) Ver publicaciones realizadas");
        System.out.println("3) Publicar");
        System.out.println("4) Ver información de la cuenta");
        System.out.println("5) Ver alcance de la cuenta");
        System.out.println("6) Activar/Suspender la cuenta");
        System.out.println("7) Seguir usuario");
        System.out.println("8) Dejar de seguir a usuario");
        System.out.println("9) Salir");
    }

    @Override
    public void mostrarMenuFeed() {
        System.out.println("1) Dar like en publicación");
        System.out.println("2) Republicar una publicación");
        System.out.println("3) Borrar una publicación");
        System.out.println("4) Salir");
    }

    @Override
    public void mostrarMenuLogin() {
        System.out.println("Escriba el nombre de usuario que quiere logear");
    }

    @Override
    public void mostrarMenuPublicacion() {
        System.out.println("Escriba lo que desea publicar");
        System.out.println("Luego ingrese R (Reposteable), L (Likeable), A (Ambos), N (Ninguno)");
    }

    @Override
    public void mostrarMenuSeguirUsuario() {
        System.out.println("Por favor ingrese el nombre de usuario de la cuenta que desea seguir");
    }

    @Override
    public void mostrarMenuDejarDeSeguirUsuario() {
        System.out.println("Por favor ingrese el nombre de usuario de la cuenta que desea dejar de seguir");
    }

    @Override
    public void pedirNombre() {
        System.out.println("Por favor ingrese su nombre");
    }

    @Override
    public void pedirEmail() {
        System.out.println("Por favor ingrese su email");
    }

    @Override
    public void pedirFechaNacimiento() {
        System.out.println("Por favor ingrese su fecha de nacimiento en formato yyyy-MM-dd (2004-06-15)");
    }

    @Override
    public void mostrarMenuBorrarCuenta() {
        System.out.println("Ingrese el nombre de usuario de la cuenta a borrar");
    }

    @Override
    public void pedirTipoCuenta() {
        System.out.println("Por favor ingrese la letra del tipo de cuenta desea crear [E] = Empresa, [P] = Popular, [N] = Normal");
    }

    @Override
    public void pedirTelefono() {
        System.out.println("Por favor ingrese su teléfono");
    }

    @Override
    public void pedirDireccion() {
        System.out.println("Por favor ingrese su dirección");
    }

    @Override
    public void mostrarMenuBorrarPublicacion() {
        System.out.println("Por favor ingrese el id de publicación que desea eliminar");
    }
}
