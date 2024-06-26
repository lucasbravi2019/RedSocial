package com.red.social;

import com.red.social.context.AppContext;
import com.red.social.exception.AccountNotFoundException;
import com.red.social.service.EjecucionService;

public class Main {
    public static void main(String[] args) {
        AppContext appContext = new AppContext();
        EjecucionService ejecucionService = appContext.getEjecucionService();

        execute(ejecucionService);
    }

    public static void execute(EjecucionService ejecucionService) {
        try {
            ejecucionService.ejecutarInicio();
        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
            execute(ejecucionService);
        }
    }

}