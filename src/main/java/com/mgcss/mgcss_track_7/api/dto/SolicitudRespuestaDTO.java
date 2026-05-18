package com.mgcss.mgcss_track_7.api.dto;

public class SolicitudRespuestaDTO {
    private Long id;
    private String descripcion;
    private String estado;
    private String tecnicoAsignado;

    public SolicitudRespuestaDTO() {
    }

    public SolicitudRespuestaDTO(Long id, String descripcion, String estado, String tecnicoAsignado) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
        this.tecnicoAsignado = tecnicoAsignado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTecnicoAsignado() {
        return tecnicoAsignado;
    }

    public void setTecnicoAsignado(String tecnicoAsignado) {
        this.tecnicoAsignado = tecnicoAsignado;
    }

}