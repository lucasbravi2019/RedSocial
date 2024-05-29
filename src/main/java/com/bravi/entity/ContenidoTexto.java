package com.bravi.entity;

import com.bravi.exception.InvalidPublicacionLengthException;
import com.bravi.util.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContenidoTexto implements Contenido<String> {

    private final String texto;

    public ContenidoTexto(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    @Override
    public List<String> getEtiquetas() {
        Pattern pattern = Pattern.compile("@\\w+");
        Matcher matcher = pattern.matcher(texto);

        List<String> cleanTags = new ArrayList<>();
        while (matcher.find()) {
            cleanTags.add(matcher.group().replace(Strings.TAG, Strings.EMPTY));
        }

        return cleanTags;
    }

    @Override
    public void validarContenido(Cuenta cuenta) {
        int publicacionMaxCharacters = cuenta.getPublicacionMaxCharacters();

        if (texto.length() > publicacionMaxCharacters)
            throw new InvalidPublicacionLengthException("La publicación excede los " + publicacionMaxCharacters + " caracteres");
    }

    @Override
    public byte[] getContenidoPublicado() {
        return texto.getBytes();
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }
}
