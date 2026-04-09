package com.mgcss.domain;
import java.util.Date;

public class Solicitud {
    private Long id;
    private enum estados{ABIERTA, EN_PROCESO, CERRADA};
    private Cliente cliente;
    private String descripcion;
    private Tecnico tecnicoAsignado;
    private Date fechaCreacion;
    private Date fechaCierre = null;// Todo cambiar a null @Column(nullable = true)
    private estados estado;

    public Solicitud() {
    }
    
    public Solicitud(Long id, Cliente cliente, String descripcion, Tecnico tecnicoAsignado) {
        this.id = id;
        this.cliente = cliente;
        this.descripcion = descripcion;
        this.tecnicoAsignado = tecnicoAsignado;
        this.fechaCreacion = new Date();
        this.estado = estados.ABIERTA;
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

    public estados getEstado() {
        return estado;
    }

    public void setEstado(estados estado) {
        this.estado = estado;
    }

    public boolean solicitudAbierta(){
        return this.estado == estados.EN_PROCESO;
    }
}

