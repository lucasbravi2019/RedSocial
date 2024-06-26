package com.bravi.service.impl;

import com.bravi.service.UserInputService;

import java.util.Scanner;

public class UserInputServiceImpl implements UserInputService {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public Integer obtenerEntero() throws NumberFormatException {
        return getInteger();
    }

    @Override
    public String obtenerDato() {
        return getString();
    }

    private String getString() {
        return scanner.nextLine();
    }

    private Integer getInteger() {
        try {
            return Integer.valueOf(scanner.nextLine());
        } catch (NumberFormatException ex) {
            System.out.println("Se ingresó un valor no numérico, por favor ingrese un número");
            return getInteger();
        }
    }
}
