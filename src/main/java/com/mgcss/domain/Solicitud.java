package com.mgcss.domain;

import java.util.Date;

public class Solicitud {
    public enum estadoSolicitudes {
        ABIERTA, EN_PROCESO, CERRADA
    }

    private Long id;

    private Cliente cliente;
    private String descripcion;
    private Tecnico tecnicoAsignado;
    private Date fechaCreacion;
    private Date fechaCierre = null;

    private estadoSolicitudes estado;

    public Solicitud() {
        // TEST constructor vacío no debería tener esto
        this.estado = estadoSolicitudes.ABIERTA;
    }

    public Solicitud(Long id, Cliente cliente, String descripcion, Tecnico tecnicoAsignado) {
        this.id = id;
        this.cliente = cliente;
        this.descripcion = descripcion;
        this.tecnicoAsignado = tecnicoAsignado;
        this.fechaCreacion = new Date();
        this.estado = estadoSolicitudes.ABIERTA;
    }

    

    public Solicitud(Long id, String descripcion, estadoSolicitudes estado) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public estadoSolicitudes getEstado() {
        return estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Tecnico getTecnicoAsignado() {
        return tecnicoAsignado;
    }

    public void setTecnicoAsignado(Tecnico tecnicoAsignado) {
        this.tecnicoAsignado = tecnicoAsignado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public void setEstado(estadoSolicitudes estado) {
        this.estado = estado;
    }

  

    public boolean asignarTecnico(Tecnico tecnico) {
        boolean asignado = false;
        
        if (tecnico.getActivo() && tecnico.getSolicitud() == null && tecnicoAsignado == null) {
            this.tecnicoAsignado = tecnico;
            tecnico.setSolicitud(this);
            asignado = true;
        }

        return asignado;
    }

}
