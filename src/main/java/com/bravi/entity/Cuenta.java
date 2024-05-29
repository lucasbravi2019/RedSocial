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
    }

    public abstract void publicar(Publicacion<?> publicacion);

    public void like(Publicacion<?> publicacion) {
        Publicacion<?> publicacionToLike = feed.getPublicaciones().stream()
                .filter(publi -> publi.equals(publicacion)).findFirst()
                .orElseThrow(() -> new PublicacionNotFoundException("Publicación no encontrada"));

        if (!publicacion.isLikeable())
            throw new LikeNotAvailableException("Dar like en la publicación no está permitido");

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
        seguidores.follow(cuenta);
    }

    public void removeSeguidor(Cuenta cuenta) {
        seguidores.unfollow(cuenta);
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
}
