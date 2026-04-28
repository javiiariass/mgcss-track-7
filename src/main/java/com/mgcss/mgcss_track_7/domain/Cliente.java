package com.mgcss.mgcss_track_7.domain;


public class Cliente{
    public enum tipoCliente {
        STANDARD, PREMIUM
    }

    private Long id;
    private String nombre;
    private String email;
    private tipoCliente tipo;

    public Cliente() {
    }
    
    public Cliente(String nombre, String email, tipoCliente tipo) {
        this.nombre = nombre;
        this.email = email;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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
    public tipoCliente getTipo() {
        return tipo;
    }
    public void setTipo(tipoCliente tipo) {
        this.tipo = tipo;
    }
}
