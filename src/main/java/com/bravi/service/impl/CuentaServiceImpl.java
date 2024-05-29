package com.bravi.service.impl;

import com.bravi.constant.PublicacionTypeEnum;
import com.bravi.entity.Contenido;
import com.bravi.entity.Cuenta;
import com.bravi.entity.Publicacion;
import com.bravi.exception.LikeNotAvailableException;
import com.bravi.exception.PublicacionNotFoundException;
import com.bravi.exception.UserAuthenticationException;
import com.bravi.repository.CuentaRepository;
import com.bravi.repository.PublicacionRepository;
import com.bravi.service.CuentaService;
import com.bravi.user.LoggedUser;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.bravi.constant.PublicacionTypeEnum.*;

public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository cuentaRepository;
    private final PublicacionRepository publicacionRepository;

    public CuentaServiceImpl(CuentaRepository cuentaRepository, PublicacionRepository publicacionRepository) {
        this.cuentaRepository = cuentaRepository;
        this.publicacionRepository = publicacionRepository;
    }

    @Override
    public void guardarCuenta(Cuenta cuenta) {
        cuentaRepository.save(cuenta);
    }

    @Override
    public void guardarCuentas(List<Cuenta> cuentas) {
        cuentas.forEach(this::guardarCuenta);
    }

    @Override
    public Cuenta findCuenta(String username) {
        return cuentaRepository.findByUsername(username);
    }

    @Override
    public void logUser(String username) {
        Cuenta cuenta = findCuenta(username);
        LoggedUser.logUser(cuenta);
    }

    @Override
    public void addSeguidor(String accountUsername, String followerUsername) {
        Cuenta cuenta = findCuenta(accountUsername);
        Cuenta seguidor = findCuenta(followerUsername);

        cuenta.addSeguidor(seguidor);
    }

    @Override
    public Publicacion<?> publicarContenido(Contenido<?> contenido, PublicacionTypeEnum tipoPublicacion) {
        Cuenta userLogged = LoggedUser.getUserLogged();

        if (userLogged == null)
            throw new UserAuthenticationException("El usuario no está logeado");

        boolean likeable = LIKEABLE.equals(tipoPublicacion) || AMBAS.equals(tipoPublicacion);
        boolean reposteable = REPOSTEABLE.equals(tipoPublicacion) || AMBAS.equals(tipoPublicacion);

        Publicacion<?> publicacion = new Publicacion<>(contenido, userLogged, likeable, reposteable);

        publicacionRepository.guardarPublicacion(publicacion);

        userLogged.publicar(publicacion);
        for (String etiqueta : contenido.getEtiquetas()) {
            Cuenta cuenta = findCuenta(etiqueta);
            cuenta.publicar(publicacion);
        }

        return publicacion;
    }

    @Override
    public void likePublicacion(Publicacion<?> publicacion) {
        Cuenta cuenta = LoggedUser.getUserLogged();

        cuenta.like(publicacion);
    }

    @Override
    public void republicarContenido(Publicacion<?> publicacion) {
        Cuenta userLogged = LoggedUser.getUserLogged();
        userLogged.republicar(publicacion);
    }

    @Override
    public void mostrarAlcance() {
        Cuenta cuenta = LoggedUser.getUserLogged();
        int alcance = 0;
        Set<Cuenta> seguidores = new HashSet<>(List.of(cuenta));
        boolean finished = false;

        List<Cuenta> seguidoresNodo = cuenta.getSeguidores().getSeguidores();

        while (!finished) {
            List<Cuenta> seguidoresUnicos = seguidoresNodo.stream()
                    .filter(follower -> !seguidores.contains(follower))
                    .distinct()
                    .toList();

            if (seguidoresUnicos.isEmpty()) {
                finished = true;
            } else {
                alcance += seguidoresUnicos.size();
                seguidores.addAll(seguidoresUnicos);
                seguidoresNodo = seguidoresNodo.stream().map(seguidor -> seguidor.getSeguidores().getSeguidores())
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());
            }
        }

        System.out.println("El alcance de la cuenta " + LoggedUser.getUserLogged().getUsername()
                + " es de " + alcance + " personas directas e indirectas");
    }

    @Override
    public void verCuentas() {
        List<Cuenta> cuentas = cuentaRepository.findAll();

        for (Cuenta cuenta : cuentas) {
            System.out.println(cuenta.getUsername());
        }
    }

    @Override
    public void mostrarInformacionCuenta() {
        Cuenta userLogged = LoggedUser.getUserLogged();

        System.out.println("Id: " + userLogged.getId());
        System.out.println("Email: " + userLogged.getEmail());
        System.out.println("Nombre: " + userLogged.getNombre());
        System.out.println("Username: " + userLogged.getUsername());
        System.out.println("Tipo de cuenta: " + userLogged.getCuentaType().getTipoCuenta());
        System.out.println("Status: " + userLogged.getStatus());
        System.out.println("Fecha de nacimiento: " + userLogged.getFechaNacimiento());
    }

    @Override
    public void alternarEstado() {
        Cuenta userLogged = LoggedUser.getUserLogged();

        userLogged.alternarEstadoCuenta();
    }

    @Override
    public Map<Integer, Publicacion<?>> obtenerPublicacionesSegunTipo(PublicacionTypeEnum publicacionType) {
        boolean likeable = PublicacionTypeEnum.LIKEABLE.equals(publicacionType)
                || PublicacionTypeEnum.AMBAS.equals(publicacionType);

        boolean reposteable = PublicacionTypeEnum.REPOSTEABLE.equals(publicacionType)
                || PublicacionTypeEnum.AMBAS.equals(publicacionType);

        Cuenta userLogged = LoggedUser.getUserLogged();
        Set<Publicacion<?>> publicaciones = userLogged.getFeed().getPublicaciones();
        return publicaciones.stream()
                .filter(publicacion -> publicacion.isReposteable() == reposteable || publicacion.isLikeable() == likeable)
                .collect(Collectors.groupingBy(Publicacion::getId,
                        Collectors.collectingAndThen(Collectors.mapping(Function.identity(),
                                Collectors.toList()), a -> a.get(0))));
    }
}
