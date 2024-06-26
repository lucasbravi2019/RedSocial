package com.red.social.context;

import com.red.social.repository.CuentaRepository;
import com.red.social.repository.PublicacionRepository;
import com.red.social.repository.impl.CuentaRepositoryImpl;
import com.red.social.repository.impl.PublicacionRepositoryImpl;
import com.red.social.service.*;
import com.red.social.service.impl.*;

public class AppContext {

    private CuentaRepository cuentaRepository;
    private CuentaService cuentaService;
    private MenuService menuService;
    private UserInputService userInputService;
    private SocialMediaFacade socialMediaFacade;
    private EjecucionService ejecucionService;
    private SeedService seedService;
    private PublicacionRepository publicacionRepository;
    private CuentaFactory cuentaFactory;

    public CuentaRepository getCuentaRepository() {
        if (cuentaRepository == null)
            cuentaRepository = new CuentaRepositoryImpl();

        return cuentaRepository;
    }

    public CuentaService getCuentaService() {
        if (cuentaService == null)
            cuentaService = new CuentaServiceImpl(getCuentaRepository(), getPublicacionRepository(), getCuentaFactory());

        return cuentaService;
    }

    public MenuService getMenuService() {
        if (menuService == null)
            menuService = new MenuServiceImpl();

        return menuService;
    }

    public UserInputService getUserInputService() {
        if (userInputService == null)
            userInputService = new UserInputServiceImpl();

        return userInputService;
    }

    public SocialMediaFacade getSocialMediaFacade() {
        if (socialMediaFacade == null)
            socialMediaFacade = new SocialMediaFacadeImpl(getCuentaService());

        return socialMediaFacade;
    }

    public EjecucionService getEjecucionService() {
        if (ejecucionService == null)
            ejecucionService = new EjecucionServiceImpl(getSocialMediaFacade(), getMenuService(), getUserInputService(),
                    getSeedService());

        return ejecucionService;
    }

    public SeedService getSeedService() {
        if (seedService == null)
            seedService = new SeedServiceImpl(getCuentaService());

        return seedService;
    }

    public PublicacionRepository getPublicacionRepository() {
        if (publicacionRepository == null)
            publicacionRepository = new PublicacionRepositoryImpl();

        return publicacionRepository;
    }

    public CuentaFactory getCuentaFactory() {
        if (cuentaFactory == null)
            cuentaFactory = new CuentaFactoryImpl();

        return cuentaFactory;
    }

}
