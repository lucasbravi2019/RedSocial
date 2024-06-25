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
        int maximosCaracteresPermitidos = cuenta.getPublicacionMaxCharacters();

        int longitudTextoParaPublicar = texto.length();
        if (longitudTextoParaPublicar > maximosCaracteresPermitidos)
            throw new InvalidPublicacionLengthException(String.format("La cuenta %s intentó publicar un contenido " +
                    "inválido. Esta cuenta sólo puede publicar un texto de %d caracteres como máximo y se intentó " +
                    "publicar un texto de %d caracteres", cuenta.getUsername(), maximosCaracteresPermitidos,
                    longitudTextoParaPublicar));
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
