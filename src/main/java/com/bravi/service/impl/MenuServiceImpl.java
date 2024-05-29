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
        System.out.println("3) Salir");
    }

    @Override
    public void mostrarSubmenu() {
        System.out.println("1) Ver feed");
        System.out.println("2) Ver publicaciones realizadas");
        System.out.println("3) Publicar");
        System.out.println("4) Ver información de la cuenta");
        System.out.println("5) Ver alcance de la cuenta");
        System.out.println("6) Activar/Suspender la cuenta");
        System.out.println("7) Salir");
    }

    @Override
    public void mostrarMenuFeed() {
        System.out.println("1) Dar like en publicación");
        System.out.println("2) Republicar una publicación");
        System.out.println("3) Salir");
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
    public void mostrarPublicaciones(Collection<Publicacion<?>> publicaciones) {
        publicaciones.forEach(publicacion -> {
            StringBuilder sb = new StringBuilder("Id: ")
                    .append(publicacion.getId());

            byte[] contenidoPublicado = publicacion.getContenido().getContenidoPublicado();
            if (String.class.equals(publicacion.getContenido().getType())) {
                String contenido = new String(contenidoPublicado);

                sb.append(", ").append(contenido);
            }

            System.out.println(sb);
        });
    }
}
