package com.bravi.service.impl;

import com.bravi.constant.PublicacionTypeEnum;
import com.bravi.entity.*;
import com.bravi.service.CuentaService;
import com.bravi.service.SocialMediaFacade;
import com.bravi.user.LoggedUser;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class SocialMediaFacadeImpl implements SocialMediaFacade {

    private final CuentaService cuentaService;

    public SocialMediaFacadeImpl(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @Override
    public void logUser(String username) {
        cuentaService.logUser(username);
    }

    @Override
    public boolean listarCuentas() {
        return cuentaService.verCuentas();
    }

    @Override
    public boolean verFeed() {
        Cuenta userLogged = LoggedUser.getUserLogged();
        Feed feed = userLogged.getFeed();

        if (feed.getPublicaciones().isEmpty()) {
            System.out.println("No hay publicaciones");
            return false;
        }

        for (Publicacion<?> publicacion : feed.getPublicaciones()) {
            mostrarPublicacion(publicacion);
        }
        return true;
    }

    private void mostrarPublicacion(Publicacion<?> publicacion) {
        Contenido<?> contenido = publicacion.getContenido();
        if (String.class == contenido.getType()) {
            System.out.println(new String(contenido.getContenidoPublicado()));
            System.out.println("Autor: " + publicacion.getAutor().getNombre());
            System.out.println("Publicado: " + publicacion.getFechaPublicacion());
            System.out.println("Likes: " + publicacion.getLikes().getLikesQuantity());
            publicacion.getLikes().getCuentas().stream()
                    .map(Cuenta::getNombre)
                    .reduce((a, b) -> a + ", " + b)
                    .ifPresent(cuentasLiked -> System.out.println("Les gusta a: " + cuentasLiked));

            Cuenta republicado = publicacion.getRepublicado();
            if (republicado != null) {
                System.out.println("Republicado por: " + republicado.getNombre());
            }
            System.out.println(obtenerAccionesPublicacion(publicacion));
            System.out.println();
        }
    }

    private String obtenerAccionesPublicacion(Publicacion<?> publicacion) {
        StringBuilder sb = new StringBuilder();

        if (publicacion.isLikeable() && publicacion.isReposteable()) {
            sb.append("Dar Like\t\tRepostear");
            return sb.toString();
        }

        if (publicacion.isLikeable()) {
            sb.append("Dar Like");
        }
        if (publicacion.isReposteable()) {
            sb.append("Repostear");
        }

        return sb.toString();
    }

    @Override
    public void verPublicaciones() {
        Cuenta userLogged = LoggedUser.getUserLogged();
        Feed feed = userLogged.getFeed();

        List<Publicacion<?>> publicaciones = feed.getPublicaciones().stream()
                .filter(publicacion -> publicacion.getAutor().equals(userLogged))
                .toList();

        if (publicaciones.isEmpty()) {
            System.out.println("No hay publicaciones");
            return;
        }

        for (Publicacion<?> publicacion : publicaciones) {
            mostrarPublicacion(publicacion);
        }
    }

    @Override
    public void publicar(String contenidoPublicacion, PublicacionTypeEnum tipoPublicacion) {
        cuentaService.publicarContenido(new ContenidoTexto(contenidoPublicacion), tipoPublicacion);
    }

    @Override
    public void verInformacionCuenta() {
        cuentaService.mostrarInformacionCuenta();
    }

    @Override
    public void verAlcanceCuenta() {
        cuentaService.mostrarAlcance();
    }

    @Override
    public void cambiarEstadoCuenta() {
       cuentaService.alternarEstado();
    }

    @Override
    public void likearPublicacion(Publicacion<?> publicacion) {
        cuentaService.likePublicacion(publicacion);
    }

    @Override
    public void repostearPublicacion(Publicacion<?> publicacion) {
        cuentaService.republicarContenido(publicacion);
    }

    @Override
    public Map<Integer, Publicacion<?>> obtenerPublicacionesSegunTipo(PublicacionTypeEnum publicacionType) {
        return cuentaService.obtenerPublicacionesSegunTipo(publicacionType);
    }

    @Override
    public boolean listarCuentasParaSeguir() {
        return cuentaService.verOtrasCuentas();
    }

    @Override
    public void seguirUsuario(String username) {
        cuentaService.seguirUsuario(username);
    }

    @Override
    public void dejarDeSeguir(String username) {
        cuentaService.dejarDeSeguir(username);
    }

    @Override
    public void crearNuevoUsuario(String nombre, String email, LocalDate fechaNacimiento, Character tipoCuenta) {
        cuentaService.crearCuenta(nombre, email, fechaNacimiento, tipoCuenta);
    }

    @Override
    public void borrarUsuario(String username) {
        cuentaService.borrarCuenta(username);
    }

    @Override
    public boolean verPublicacionesParaBorrar() {
        Cuenta userLogged = LoggedUser.getUserLogged();
        Feed feed = userLogged.getFeed();

        List<Publicacion<?>> publicaciones = feed.getPublicaciones().stream()
                .filter(publicacion -> publicacion.getAutor().equals(userLogged))
                .toList();

        if (publicaciones.isEmpty()) {
            System.out.println("No hay publicaciones");
            return false;
        }

        for (Publicacion<?> publicacion : publicaciones) {
            System.out.printf("%d - \"%s\"\n", publicacion.getId(), new String(publicacion.getContenido().getContenidoPublicado()));
        }
        return true;
    }

    @Override
    public void listarSeguidores() {
        Cuenta userLogged = LoggedUser.getUserLogged();
        List<Cuenta> seguidores = userLogged.getSeguidores().getSeguidores();
        for (Cuenta seguidor : seguidores) {
            System.out.println(seguidor.getUsername());
        }
    }

    @Override
    public void borrarPublicacion(Integer publicacionId) {
        Cuenta userLogged = LoggedUser.getUserLogged();
        userLogged.getFeed().getPublicaciones().removeIf(publicacion -> publicacion.getId() == publicacionId);
    }
}
