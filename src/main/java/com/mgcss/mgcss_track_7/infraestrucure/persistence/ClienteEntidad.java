package com.mgcss.mgcss_track_7.infraestrucure.persistence;

import com.mgcss.mgcss_track_7.domain.Cliente;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class ClienteEntidad {
    @Id
    private Long id;

    @Column(name = "nombre")
    private String nombre;
       @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cliente")
    private Cliente.tipoCliente tipoCliente;

    public ClienteEntidad() {
    }

    public ClienteEntidad(Long id, String nombre, String email, Cliente.tipoCliente tipoCliente) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.tipoCliente = tipoCliente;
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

    public Cliente.tipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(Cliente.tipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

}
