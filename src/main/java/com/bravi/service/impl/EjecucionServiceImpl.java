package com.bravi.service.impl;

import com.bravi.constant.PublicacionTypeEnum;
import com.bravi.entity.Publicacion;
import com.bravi.exception.AccountNotFoundException;
import com.bravi.exception.LikeNotAvailableException;
import com.bravi.exception.PublicacionNotFoundException;
import com.bravi.exception.RepostNotAvailableException;
import com.bravi.service.*;

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
        String contenidoPublicacion = userInputService.obtenerContenidoPublicacion();
        PublicacionTypeEnum publicacionTypeEnum = obtenerTipoPublicacion();

        socialMediaFacade.publicar(contenidoPublicacion, publicacionTypeEnum);
    }

    private PublicacionTypeEnum obtenerTipoPublicacion() {
        String tipoPublicacion = userInputService.obtenerTipoPublicacion();

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
        String username = userInputService.obtenerUsername();

        socialMediaFacade.logUser(username);
    }

    private void ejecutarOperacionesSubmenu() {
        Integer operacion = userInputService.obtenerOperacion();

        switch (operacion) {
            case 1:
                socialMediaFacade.verFeed();
                ejecutarMenuFeed();
                ejecutarSubmenu();
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
                break;

            default:
                System.out.println("Seleccionó una opción incorrecta, por favor vuelva a intentarlo");
                ejecutarOperacionesSubmenu();
        }
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

        menuService.mostrarPublicaciones(publicacionesPorId.values());
        Integer publicacionId = userInputService.obtenerIdPublicacion();

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
        Integer operacion = userInputService.obtenerOperacion();

        switch (operacion) {
            case 1:
                obtenerPublicacionLikeada();
                break;

            case 2:
                obtenerPublicacionReposteada();
                break;

            case 3:
                break;

            default:
                System.out.println("Opción inválida, por favor intenta nuevamente");
                ejecutarOperacionesMenuFeed();
        }
    }

    private void ejecutarOperacionesMenuPrincipal() {
        Integer operacion = userInputService.obtenerOperacion();

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
        Integer operacion = userInputService.obtenerOperacion();

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
