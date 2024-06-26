package com.red.social.repository.impl;

import com.red.social.entity.Publicacion;
import com.red.social.exception.PublicacionNotFoundException;
import com.red.social.repository.PublicacionRepository;

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
