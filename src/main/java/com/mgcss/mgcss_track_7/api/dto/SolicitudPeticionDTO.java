package com.mgcss.mgcss_track_7.api.dto;

public class SolicitudPeticionDTO {
    private String descripcion;

    public SolicitudPeticionDTO() {
    }

    public SolicitudPeticionDTO(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
