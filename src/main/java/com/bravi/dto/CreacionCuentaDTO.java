package com.bravi.dto;

import java.lang.Character;

import java.time.LocalDate;

public class CreacionCuentaDTO {

    private String nombre;
    private String email;
    private LocalDate fechaNacimiento;
    private Character tipoCuenta;
    private String direccion;
    private String telefono;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Character getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(Character tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public static CreacionCuentaDTOBuilder builder() {
        return new CreacionCuentaDTOBuilder();
    }

    public static class CreacionCuentaDTOBuilder {
        private final CreacionCuentaDTO dto;

        public CreacionCuentaDTOBuilder() {
            dto = new CreacionCuentaDTO();
        }

        public CreacionCuentaDTOBuilder nombre(String nombre) {
            this.dto.nombre = nombre;
            return this;
        }

        public CreacionCuentaDTOBuilder email(String email) {
            this.dto.email = email;
            return this;
        }

        public CreacionCuentaDTOBuilder fechaNacimiento(LocalDate fechaNacimiento) {
            this.dto.fechaNacimiento = fechaNacimiento;
            return this;
        }

        public CreacionCuentaDTOBuilder tipoCuenta(Character tipoCuenta) {
            this.dto.tipoCuenta = tipoCuenta;
            return this;
        }

        public CreacionCuentaDTOBuilder direccion(String direccion) {
            this.dto.direccion = direccion;
            return this;
        }

        public CreacionCuentaDTOBuilder telefono(String telefono) {
            this.dto.telefono = telefono;
            return this;
        }

        public CreacionCuentaDTO build() {
            return this.dto;
        }
    }
}
