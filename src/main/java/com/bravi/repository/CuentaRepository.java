package com.bravi.repository;

import com.bravi.entity.Cuenta;

import java.util.List;
import java.util.Optional;

public interface CuentaRepository {

    List<Cuenta> findAll();

    Cuenta findByUsername(String username);

    void save(Cuenta cuenta);

    void delete(Cuenta cuenta);

    boolean existsByEmail(String email);
}
