package com.mgcss.domain;

import java.util.List;

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

    public void crearCliente(Cliente nuevoCliente){

    }

    public void  ModificarCliente(Cliente cliente){

    }

    public List<Cliente> listarClientes(){
        return null;
    }

    public Cliente consultarCliente(Long id){
        return null;
    }   
}
