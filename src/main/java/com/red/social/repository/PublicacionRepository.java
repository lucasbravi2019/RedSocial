package com.red.social.repository;

import com.red.social.entity.Publicacion;

public interface PublicacionRepository {

    void guardarPublicacion(Publicacion<?> publicacion);

    Publicacion<?> obtenerPublicacionPorId(Integer publicacionId);

}
