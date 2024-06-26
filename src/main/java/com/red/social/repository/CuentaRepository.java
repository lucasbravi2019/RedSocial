package com.red.social.repository;

import com.red.social.entity.Cuenta;

import java.util.List;

public interface CuentaRepository {

    List<Cuenta> findAll();

    Cuenta findByUsername(String username);

    void save(Cuenta cuenta);

    void delete(Cuenta cuenta);

    boolean existsByEmail(String email);
}
