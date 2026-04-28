package com.mgcss.mgcss_track_7.infraestrucure.persistence;

import com.mgcss.mgcss_track_7.domain.Solicitud;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "solicitudes")
public class SolicitudEntidad {
    @Id
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Solicitud.estadoSolicitudes estado;

    public SolicitudEntidad() {
    }

    public SolicitudEntidad(Long id, String descripcion, Solicitud.estadoSolicitudes estado) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
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

    public Solicitud.estadoSolicitudes getEstado() {
        return estado;
    }

    public void setEstado(Solicitud.estadoSolicitudes estado) {
        this.estado = estado;
    }


}
