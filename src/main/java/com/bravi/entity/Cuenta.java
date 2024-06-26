package com.bravi.entity;

import com.bravi.constant.CuentaStatusEnum;
import com.bravi.constant.CuentaTypeEnum;
import com.bravi.event.Seguidores;
import com.bravi.exception.LikeNotAvailableException;
import com.bravi.exception.PublicacionNotFoundException;
import com.bravi.exception.RepostNotAvailableException;

public abstract class Cuenta extends Usuario {

    protected CuentaStatusEnum status;
    protected Feed feed;
    protected Seguidores seguidores;

    public Cuenta(Seguidores seguidores) {
        this.status = CuentaStatusEnum.ABIERTA;
        this.feed = new Feed();
        this.seguidores = seguidores;
        this.seguidores.setCuenta(this);
    }

    public abstract void publicar(Publicacion<?> publicacion);

    public void like(Publicacion<?> publicacion) {
        Publicacion<?> publicacionToLike = feed.getPublicaciones().stream()
                .filter(publi -> publi.equals(publicacion)).findFirst()
                .orElseThrow(() -> new PublicacionNotFoundException("Publicación no encontrada"));

        if (!publicacion.isLikeable()) {
            String nombre = getNombre();
            String contenido = new String(publicacion.getContenido().getContenidoPublicado());
            throw new LikeNotAvailableException(String.format("%s intentó likear una publicación no likeable: \"%s\"",
                    nombre, contenido));
        }

        publicacionToLike.likearPublicacion(this);
    }

    public void republicar(Publicacion<?> publicacion) {
        if (publicacion == null)
            throw new PublicacionNotFoundException("Publicación no encontrada");

        if (!publicacion.isReposteable() || CuentaStatusEnum.SUSPENDIDA.equals(this.status))
            throw new RepostNotAvailableException("Repostear la publicación no está permitido");

        publicacion.setRepublicado(this);
        publicar(publicacion);
    }

    public abstract int getPublicacionMaxCharacters();

    public abstract CuentaTypeEnum getCuentaType();

    public String getStatus() {
        return status.getStatus();
    }

    public void setStatus(CuentaStatusEnum status) {
        this.status = status;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public Seguidores getSeguidores() {
        return seguidores;
    }

    public void addSeguidor(Cuenta cuenta) {
        seguidores.follow(feed.getPublicaciones(), cuenta);
        System.out.printf("Se añadió como seguidor de la cuenta %s a %s\n", this.getUsername(), cuenta.getUsername());
    }

    public void removeSeguidor(Cuenta cuenta) {
        seguidores.unfollow(feed.getPublicaciones(), cuenta);
    }

    public void alternarEstadoCuenta() {
        if (CuentaStatusEnum.ABIERTA.equals(status)) {
            setStatus(CuentaStatusEnum.SUSPENDIDA);
            return;
        }

        if (CuentaStatusEnum.SUSPENDIDA.equals(status)) {
            setStatus(CuentaStatusEnum.ABIERTA);
        }
    }

    public abstract boolean canFollow();

    public void mostrarCuenta() {
        System.out.println("Id: " + this.getId());
        System.out.println("Email: " + this.getEmail());
        System.out.println("Nombre: " + this.getNombre());
        System.out.println("Username: " + this.getUsername());
        System.out.println("Tipo de cuenta: " + this.getCuentaType().getTipoCuenta());
        System.out.println("Status: " + this.getStatus());
        System.out.println("Fecha de nacimiento: " + this.getFechaNacimiento());
    }
}
