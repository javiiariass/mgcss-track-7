package com.mgcss.mgcss_track_7.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Cliente{
    public enum tipoCliente {
        STANDARD, PREMIUM
    }

    private Long id;
    private String nombre;
    private String email;
    private tipoCliente tipo;

    public Cliente(String nombre, String email, tipoCliente tipo) {
        this.nombre = nombre;
        this.email = email;
        this.tipo = tipo;
    }
    public Cliente() {
        this.tipo = tipoCliente.STANDARD;
    }
}
