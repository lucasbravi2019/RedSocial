package com.bravi.repository;

import com.bravi.entity.Publicacion;

public interface PublicacionRepository {

    void guardarPublicacion(Publicacion<?> publicacion);

    Publicacion<?> obtenerPublicacionPorId(Integer publicacionId);

}
