package com.bravi.repository.impl;

import com.bravi.entity.Publicacion;
import com.bravi.exception.PublicacionNotFoundException;
import com.bravi.repository.PublicacionRepository;

import java.util.ArrayList;
import java.util.List;

public class PublicacionRepositoryImpl implements PublicacionRepository {

    private final List<Publicacion<?>> publicaciones = new ArrayList<>();

    @Override
    public void guardarPublicacion(Publicacion<?> publicacion) {
        if (publicacion != null)
            publicaciones.add(publicacion);
    }

    @Override
    public Publicacion<?> obtenerPublicacionPorId(Integer publicacionId) {
        return publicaciones.stream().filter(publicacion -> publicacion.getId() == publicacionId).findFirst()
                .orElseThrow(() -> new PublicacionNotFoundException("Publicación no encontrada para id: " + publicacionId));
    }
}
