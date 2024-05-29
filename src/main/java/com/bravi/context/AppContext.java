package com.bravi.context;

import com.bravi.repository.CuentaRepository;
import com.bravi.repository.PublicacionRepository;
import com.bravi.repository.impl.CuentaRepositoryImpl;
import com.bravi.repository.impl.PublicacionRepositoryImpl;
import com.bravi.service.*;
import com.bravi.service.impl.*;

public class AppContext {

    private CuentaRepository cuentaRepository;
    private CuentaService cuentaService;
    private MenuService menuService;
    private UserInputService userInputService;
    private SocialMediaFacade socialMediaFacade;
    private EjecucionService ejecucionService;
    private SeedService seedService;
    private PublicacionRepository publicacionRepository;

    public CuentaRepository getCuentaRepository() {
        if (cuentaRepository == null)
            cuentaRepository = new CuentaRepositoryImpl();

        return cuentaRepository;
    }

    public CuentaService getCuentaService() {
        if (cuentaService == null)
            cuentaService = new CuentaServiceImpl(getCuentaRepository(), getPublicacionRepository());

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

}
