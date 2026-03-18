package com.mgcss.domain;

public class Solicitud {
    private Long id;
    private enum estado{ABIERTA, EN_PROCESO, CERRADA};
    private Cliente cliente;
    private String descripcion;
    private Tecnico tecnicoAsignado;
    private Date fechaCreacion;
    private Date fechaCierre = null;// Todo cambiar a null @Column(nullable = true)

    
    public Solicitud(Long id, Cliente cliente, String descripcion, Tecnico tecnicoAsignado) {
        this.id = id;
        this.cliente = cliente;
        this.descripcion = descripcion;
        this.tecnicoAsignado = tecnicoAsignado;
        this.fechaCreacion = new Date();
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
}