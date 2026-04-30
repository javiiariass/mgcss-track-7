package com.mgcss.mgcss_track_7.infraestrucure.persistence;

import com.mgcss.mgcss_track_7.domain.Solicitud;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

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


    public SolicitudEntidad(Long id, String descripcion, Solicitud.estadoSolicitudes estado) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
    }

}
