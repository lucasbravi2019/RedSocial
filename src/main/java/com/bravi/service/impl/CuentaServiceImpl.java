package com.bravi.service.impl;

import com.bravi.constant.CuentaTypeEnum;
import com.bravi.constant.PublicacionLengthEnum;
import com.bravi.constant.PublicacionTypeEnum;
import com.bravi.entity.Contenido;
import com.bravi.entity.Cuenta;
import com.bravi.entity.Publicacion;
import com.bravi.exception.AccountNotFoundException;
import com.bravi.exception.SeguidorInvalidException;
import com.bravi.exception.UserAuthenticationException;
import com.bravi.repository.CuentaRepository;
import com.bravi.repository.PublicacionRepository;
import com.bravi.service.CuentaFactory;
import com.bravi.service.CuentaService;
import com.bravi.user.LoggedUser;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.bravi.constant.PublicacionTypeEnum.*;

public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository cuentaRepository;
    private final PublicacionRepository publicacionRepository;
    private final CuentaFactory cuentaFactory;

    public CuentaServiceImpl(CuentaRepository cuentaRepository, PublicacionRepository publicacionRepository,
                             CuentaFactory cuentaFactory) {
        this.cuentaRepository = cuentaRepository;
        this.publicacionRepository = publicacionRepository;
        this.cuentaFactory = cuentaFactory;
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
        mostrarPublicacion(publicacion);
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

        List<Cuenta> seguidores = getSeguidores(cuenta);
        int alcance = seguidores.size();
        String username = LoggedUser.getUserLogged().getUsername();
        System.out.printf("El alcance de la cuenta %s es de %d personas directas e indirectas\n", username, alcance);
    }

    @Override
    public boolean verCuentas() {
        List<Cuenta> cuentas = cuentaRepository.findAll();

        if (cuentas == null || cuentas.isEmpty()) {
            System.out.println("No hay cuentas");
            return false;
        }

        for (Cuenta cuenta : cuentas) {
            System.out.println(cuenta.getUsername());
        }

        return true;
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

    @Override
    public boolean verOtrasCuentas() {
        List<Cuenta> cuentas = cuentaRepository.findAll();
        Cuenta userLogged = LoggedUser.getUserLogged();
        cuentas.remove(userLogged);

        if (cuentas.isEmpty())
            return false;

        for (Cuenta cuenta : cuentas) {
            System.out.println(cuenta.getUsername());
        }

        return true;
    }

    @Override
    public void seguirUsuario(String username) {
        Cuenta userLogged = LoggedUser.getUserLogged();
        try {
            Cuenta cuentaASeguir = findCuenta(username);
            cuentaASeguir.addSeguidor(userLogged);
        } catch (AccountNotFoundException ex) {
            System.out.printf("Cuenta no encontrada para username: %s\n", username);
        } catch (SeguidorInvalidException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Cuenta> getSeguidores(Cuenta cuenta) {
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
                seguidores.addAll(seguidoresUnicos);
                seguidoresNodo = seguidoresNodo.stream().map(seguidor -> seguidor.getSeguidores().getSeguidores())
                        .flatMap(Collection::stream)
                        .collect(Collectors.toList());
            }
        }

        return new ArrayList<>(seguidores);
    }

    @Override
    public void dejarDeSeguir(String username) {
        Cuenta userLogged = LoggedUser.getUserLogged();
        Cuenta cuenta = findCuenta(username);
        cuenta.removeSeguidor(userLogged);
    }

    @Override
    public void crearCuenta(String nombre, String email, LocalDate fechaNacimiento, Character tipoCuenta) {
        CuentaTypeEnum tipoCuentaACrear;
        try {
            tipoCuentaACrear = CuentaTypeEnum.getTipoCuentaPorCaracter(tipoCuenta);
        } catch (IllegalStateException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        Cuenta cuenta = cuentaFactory.crearCuenta(nombre, email, fechaNacimiento, tipoCuentaACrear);
        cuentaRepository.save(cuenta);
    }

    @Override
    public void borrarCuenta(String username) {
        Cuenta cuenta = findCuenta(username);
        cuentaRepository.delete(cuenta);
    }

    private void mostrarPublicacion(Publicacion<?> publicacion) {
        Cuenta userLogged = LoggedUser.getUserLogged();

        boolean reposteable = publicacion.isReposteable();
        boolean likeable = publicacion.isLikeable();

        String contenidoPublicado = new String(publicacion.getContenido().getContenidoPublicado());
        String nombre = userLogged.getNombre();
        String descripcionPublicacionPorCuenta = PublicacionLengthEnum.getDescripcionPublicacionPorCuenta(userLogged).toLowerCase();
        if (reposteable && likeable) {
            System.out.printf("%s publica un texto %s likeable y reposteable con contenido: \"%s\"\n", nombre,
                    descripcionPublicacionPorCuenta, contenidoPublicado);
            return;
        }

        if (reposteable) {
            System.out.printf("%s publica un texto %s reposteable con contenido: \"%s\"\n", nombre,
                    descripcionPublicacionPorCuenta, contenidoPublicado);
            return;
        }

        if (likeable) {
            System.out.printf("%s publica un texto %s likeable con contenido: \"%s\"\n", nombre,
                    descripcionPublicacionPorCuenta, contenidoPublicado);
            return;
        }

        System.out.printf("%s publica un texto %s con contenido: \"%s\"\n", nombre, descripcionPublicacionPorCuenta,
                contenidoPublicado);
    }
}
