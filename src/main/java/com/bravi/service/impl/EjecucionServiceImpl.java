package com.bravi.service.impl;

import com.bravi.constant.CuentaTypeEnum;
import com.bravi.constant.PublicacionTypeEnum;
import com.bravi.dto.CreacionCuentaDTO;
import com.bravi.entity.Cuenta;
import com.bravi.entity.Publicacion;
import com.bravi.exception.AccountNotFoundException;
import com.bravi.exception.LikeNotAvailableException;
import com.bravi.exception.PublicacionNotFoundException;
import com.bravi.exception.RepostNotAvailableException;
import com.bravi.service.*;
import com.bravi.util.Strings;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public class EjecucionServiceImpl implements EjecucionService {

    private final SocialMediaFacade socialMediaFacade;
    private final MenuService menuService;
    private final UserInputService userInputService;
    private final SeedService seedService;

    public EjecucionServiceImpl(SocialMediaFacade socialMediaFacade, MenuService menuService,
                                UserInputService userInputService, SeedService seedService) {
        this.socialMediaFacade = socialMediaFacade;
        this.menuService = menuService;
        this.userInputService = userInputService;
        this.seedService = seedService;
    }


    @Override
    public void ejecutarInicio() {
        menuService.mostrarInicio();
        ejecutarOperacionesInicio();
    }

    @Override
    public void ejecutarPrincipal() {
        try {
            menuService.mostrarMenu();
            ejecutarOperacionesMenuPrincipal();
        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
            ejecutarPrincipal();
        }
    }

    @Override
    public void ejecutarPublicacionUsuario() {
        menuService.mostrarMenuPublicacion();
        String contenidoPublicacion = userInputService.obtenerDato();
        PublicacionTypeEnum publicacionTypeEnum = obtenerTipoPublicacion();

        socialMediaFacade.publicar(contenidoPublicacion, publicacionTypeEnum);
    }

    private PublicacionTypeEnum obtenerTipoPublicacion() {
        String tipoPublicacion = userInputService.obtenerDato();

        if (PublicacionTypeEnum.AMBAS.getUserInputOperation().equalsIgnoreCase(tipoPublicacion)) {
            return PublicacionTypeEnum.AMBAS;
        }

        if (PublicacionTypeEnum.LIKEABLE.getUserInputOperation().equalsIgnoreCase(tipoPublicacion)) {
            return PublicacionTypeEnum.LIKEABLE;
        }

        if (PublicacionTypeEnum.REPOSTEABLE.getUserInputOperation().equalsIgnoreCase(tipoPublicacion)) {
            return PublicacionTypeEnum.REPOSTEABLE;
        }

        if (PublicacionTypeEnum.NINGUNA.getUserInputOperation().equalsIgnoreCase(tipoPublicacion)) {
            return PublicacionTypeEnum.NINGUNA;
        }

        System.out.println("Ingresó una opción inválida, por favor pruebe nuevamente");
        System.out.println("Los valores válidos son: A (Ambas), R (Reposteable), L (Likeable), N (Ninguna)");

        return obtenerTipoPublicacion();
    }

    private void ejecutarSubmenu() {
        menuService.mostrarSubmenu();
        ejecutarOperacionesSubmenu();
    }

    private void logUser() {
        menuService.mostrarMenuLogin();
        String username = userInputService.obtenerDato();

        socialMediaFacade.logUser(username);
    }


    private void ejecutarOperacionesSubmenu() {
        Integer operacion = userInputService.obtenerEntero();

        switch (operacion) {
            case 1:
                boolean hayPublicaciones = socialMediaFacade.verFeed();
                if (!hayPublicaciones)
                    ejecutarSubmenu();
                else {
                    ejecutarMenuFeed();
                    ejecutarSubmenu();
                }
                break;

            case 2:
                socialMediaFacade.verPublicaciones();
                ejecutarSubmenu();
                break;

            case 3:
                ejecutarPublicacionUsuario();
                ejecutarSubmenu();
                break;

            case 4:
                socialMediaFacade.verInformacionCuenta();
                ejecutarSubmenu();
                break;

            case 5:
                socialMediaFacade.verAlcanceCuenta();
                ejecutarSubmenu();
                break;

            case 6:
                socialMediaFacade.cambiarEstadoCuenta();
                ejecutarSubmenu();
                break;

            case 7:
                ejecutarSeguirUsuario();
                ejecutarSubmenu();
                break;

            case 8:
                ejecutarDejarSeguirUsuario();
                ejecutarSubmenu();
                break;

            case 9:
                break;

            default:
                System.out.println("Seleccionó una opción incorrecta, por favor vuelva a intentarlo");
                ejecutarOperacionesSubmenu();
        }
    }

    private void ejecutarDejarSeguirUsuario() {
        boolean haySeguidores = socialMediaFacade.listarSeguidores();
        if (!haySeguidores)
            return;

        menuService.mostrarMenuDejarDeSeguirUsuario();
        String username = userInputService.obtenerDato();
        socialMediaFacade.dejarDeSeguir(username);
    }

    private void ejecutarSeguirUsuario() {
        boolean hayCuentas = socialMediaFacade.listarCuentasParaSeguir();
        if (!hayCuentas) {
            System.out.println("No hay cuentas para seguir");
            return;
        }

        menuService.mostrarMenuSeguirUsuario();
        String username = userInputService.obtenerDato();
        socialMediaFacade.seguirUsuario(username);
    }

    private void ejecutarMenuFeed() {
        menuService.mostrarMenuFeed();

        ejecutarOperacionesMenuFeed();
    }

    private void obtenerPublicacionLikeada() {
        try {
            Publicacion<?> publicacion = obtenerPublicacionSeleccionada(PublicacionTypeEnum.LIKEABLE);
            socialMediaFacade.likearPublicacion(publicacion);
        } catch (LikeNotAvailableException | PublicacionNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private Publicacion<?> obtenerPublicacionSeleccionada(PublicacionTypeEnum publicacionType) {
        Map<Integer, Publicacion<?>> publicacionesPorId = socialMediaFacade
                .obtenerPublicacionesSegunTipo(publicacionType);

        socialMediaFacade.mostrarPublicaciones(publicacionesPorId.values());
        Integer publicacionId = userInputService.obtenerEntero();

        Publicacion<?> publicacion = publicacionesPorId.get(publicacionId);
        if (publicacion == null)
            throw new PublicacionNotFoundException("Ingresó un id de publicación inválido");

        return publicacion;
    }

    private void obtenerPublicacionReposteada() {
        try {
            Publicacion<?> publicacion = obtenerPublicacionSeleccionada(PublicacionTypeEnum.REPOSTEABLE);
            socialMediaFacade.repostearPublicacion(publicacion);
        } catch (RepostNotAvailableException | PublicacionNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void ejecutarOperacionesMenuFeed() {
        Integer operacion = userInputService.obtenerEntero();

        switch (operacion) {
            case 1:
                obtenerPublicacionLikeada();
                break;

            case 2:
                obtenerPublicacionReposteada();
                break;

            case 3:
                borrarPublicacion();
                break;

            case 4:
                break;

            default:
                System.out.println("Opción inválida, por favor intenta nuevamente");
                ejecutarOperacionesMenuFeed();
        }
    }

    private void borrarPublicacion() {
        boolean hayPublicaciones = socialMediaFacade.verPublicacionesParaBorrar();
        if (!hayPublicaciones)
            return;

        menuService.mostrarMenuBorrarPublicacion();
        Integer publicacionId = userInputService.obtenerEntero();
        socialMediaFacade.borrarPublicacion(publicacionId);
    }

    private void borrarUsuario() {
        boolean hayCuentas = socialMediaFacade.listarCuentas();
        if (!hayCuentas)
            return;

        menuService.mostrarMenuBorrarCuenta();
        String username = userInputService.obtenerDato();
        socialMediaFacade.borrarUsuario(username);
    }

    private void crearNuevoUsuario() {
        menuService.pedirNombre();
        String nombre = userInputService.obtenerDato();
        String email = pedirEmail();
        LocalDate fechaNacimiento = pedirFechaNacimiento();
        Character tipoCuenta = pedirTipoCuenta();

        CreacionCuentaDTO creacionCuentaDTO = CreacionCuentaDTO.builder()
                .nombre(nombre)
                .email(email)
                .fechaNacimiento(fechaNacimiento)
                .tipoCuenta(tipoCuenta)
                .build();

        if (CuentaTypeEnum.EMPRESA.equals(CuentaTypeEnum.getTipoCuentaPorCaracter(tipoCuenta))) {
            menuService.pedirDireccion();
            String direccion = userInputService.obtenerDato();
            menuService.pedirTelefono();
            String telefono = userInputService.obtenerDato();
            creacionCuentaDTO.setDireccion(direccion);
            creacionCuentaDTO.setTelefono(telefono);
        }
        socialMediaFacade.crearNuevoUsuario(creacionCuentaDTO);
    }

    private String pedirEmail() {
        menuService.pedirEmail();
        String email = userInputService.obtenerDato();
        if (!email.contains("@")) {
            System.out.println("Ingresó un email inválido");
            return pedirEmail();
        }
        return email;
    }

    private Character pedirTipoCuenta() {
        menuService.pedirTipoCuenta();
        String tipoCuenta = userInputService.obtenerDato();
        List<Character> validTypes = CuentaTypeEnum.getValidTypes();
        if (tipoCuenta == null || Strings.EMPTY.equals(tipoCuenta) || !validTypes.contains(tipoCuenta.toLowerCase().charAt(0))) {
            System.out.println("Ingresó un tipo de cuenta inválido");
            return pedirTipoCuenta();
        }

        return tipoCuenta.toLowerCase().charAt(0);
    }

    private LocalDate pedirFechaNacimiento() {
        menuService.pedirFechaNacimiento();
        try {
            return LocalDate.parse(userInputService.obtenerDato());
        } catch (DateTimeParseException ex) {
            System.out.println("Ingresó una fecha inválida");
            return pedirFechaNacimiento();
        }
    }

    private void ejecutarOperacionesMenuPrincipal() {
        Integer operacion = userInputService.obtenerEntero();

        switch (operacion) {
            case 1:
                socialMediaFacade.listarCuentas();
                ejecutarPrincipal();
                break;

            case 2:
                logUser();
                ejecutarSubmenu();
                ejecutarPrincipal();
                break;

            case 3:
                crearNuevoUsuario();
                ejecutarPrincipal();
                break;

            case 4:
                borrarUsuario();
                ejecutarPrincipal();
                break;

            case 5:
                System.out.println("Finalizado");
                break;

            default:
                System.out.println("Seleccionó una opción incorrecta, por favor vuelva a intentarlo");
                ejecutarOperacionesMenuPrincipal();
        }
    }

    private void inicializarDatos() {
        seedService.addCuentas();
        seedService.publicar();
    }

    private void ejecutarOperacionesInicio() {
        Integer operacion = userInputService.obtenerEntero();

        switch (operacion) {
            case 1:
                inicializarDatos();
                ejecutarPrincipal();
                break;

            case 2:
                ejecutarPrincipal();
                break;

            case 3:
                System.out.println("Finalizado");
                break;

            default:
                System.out.println("Seleccionó una opción incorrecta, por favor vuelva a intentarlo");
                ejecutarOperacionesInicio();
        }
    }
}
