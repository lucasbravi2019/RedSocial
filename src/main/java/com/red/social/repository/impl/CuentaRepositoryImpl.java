package com.red.social.repository.impl;

import com.red.social.entity.Cuenta;
import com.red.social.exception.AccountNotFoundException;
import com.red.social.repository.CuentaRepository;

import java.util.*;

public class CuentaRepositoryImpl implements CuentaRepository {

    private final List<Cuenta> cuentas = new ArrayList<>();
    private final Map<String, Cuenta> cuentasCache = new HashMap<>();


    @Override
    public List<Cuenta> findAll() {
        return cuentas;
    }

    @Override
    public boolean existsByEmail(String email) {
        return cuentas.stream().anyMatch(cuenta -> email.equals(cuenta.getEmail()));
    }

    @Override
    public Cuenta findByUsername(String username) {
        Cuenta accountFound = cuentasCache.get(username.toLowerCase());

        if (accountFound != null) {
            return accountFound;
        }

        Optional<Cuenta> cuentaBuscada = cuentas.stream().filter(cuenta -> username.equalsIgnoreCase(cuenta.getUsername()))
                .findFirst();

        cuentaBuscada.ifPresent(account -> cuentasCache.put(account.getUsername().toLowerCase(), account));

        return cuentaBuscada
                .orElseThrow(() -> new AccountNotFoundException("Cuenta no encontrada para username: " + username));
    }

    @Override
    public void save(Cuenta cuenta) {
        if (cuenta != null)
            cuentas.add(cuenta);
    }

    @Override
    public void delete(Cuenta cuenta) {
        if (cuenta != null)
            cuentas.remove(cuenta);
    }
}
