package com.bravi;

import com.bravi.context.AppContext;
import com.bravi.exception.AccountNotFoundException;
import com.bravi.exception.LikeNotAvailableException;
import com.bravi.service.EjecucionService;

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