package com.red.social.entity;

import java.util.LinkedHashSet;
import java.util.Set;

public class Feed {

    private Set<Publicacion<?>> publicaciones = new LinkedHashSet<>();

    public Set<Publicacion<?>> getPublicaciones() {
        return publicaciones;
    }

    public void publicar(Publicacion<?> publicacion) {
        if (publicacion != null) {
            this.publicaciones.add(publicacion);
        }
    }

    public void borrarPublicacion(Publicacion<?> publicacionABorrar) {
        if (publicacionABorrar != null) {
            this.publicaciones.removeIf(publicacion -> publicacion.getId() == publicacionABorrar.getId());
        }
    }

}
