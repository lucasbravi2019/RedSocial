package com.red.social.entity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Publicacion<T> {

    private static int id = 0;
    private final int publicacionId;
    private Contenido<T> contenido;
    private Like likes;
    private Cuenta autor;
    private Cuenta republicado;
    private boolean likeable;
    private boolean reposteable;
    private LocalDateTime fechaPublicacion;


    public Publicacion(Contenido<T> contenido, Cuenta autor, boolean likeable, boolean reposteable) {
        this.contenido = contenido;
        this.publicacionId = ++id;
        this.autor = autor;
        this.likes = new Like();
        this.likeable = likeable;
        this.reposteable = reposteable;
        this.fechaPublicacion = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    public int getId() {
        return publicacionId;
    }

    public Like getLikes() {
        return likes;
    }

    public void setLikes(Like likes) {
        this.likes = likes;
    }

    public Cuenta getAutor() {
        return autor;
    }

    public void setAutor(Cuenta autor) {
        this.autor = autor;
    }

    public Cuenta getRepublicado() {
        return republicado;
    }

    public Contenido<T> getContenido() {
        return contenido;
    }

    public void setContenido(Contenido<T> contenido) {
        this.contenido = contenido;
    }

    public void setRepublicado(Cuenta republicado) {
        this.republicado = republicado;
    }

    public boolean isLikeable() {
        return likeable;
    }

    public void setLikeable(boolean likeable) {
        this.likeable = likeable;
    }

    public boolean isReposteable() {
        return reposteable;
    }

    public void setReposteable(boolean reposteable) {
        this.reposteable = reposteable;
    }

    public void likearPublicacion(Cuenta cuenta) {
        likes.addLike(cuenta);
    }

    public void quitarLike(Cuenta cuenta) {
        likes.removeLike(cuenta);
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
}
