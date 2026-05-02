package com.mgcss.mgcss_track_7.infraestrucure.persistence;

import com.mgcss.mgcss_track_7.domain.Solicitud;
import com.mgcss.mgcss_track_7.domain.Cliente;
import com.mgcss.mgcss_track_7.domain.Tecnico;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "solicitudes")
public class SolicitudEntidad {
    @Id
    private Long id;
     @Column(name = "Cliente")
    private Cliente cliente;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "tecnicoAsignado")
    private Tecnico tecnicoAsignado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Solicitud.estadoSolicitudes estado;
    @Column(name = "listaEstado")
    private List<Solicitud.estadoSolicitudes> historico;


    public SolicitudEntidad(Long id, Cliente cliente, String descripcion, Tecnico tecnicoAsignado) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = Solicitud.estadoSolicitudes.ABIERTA;
        this.cliente = cliente;
        this.tecnicoAsignado = tecnicoAsignado;
        historico.add(estado);
    }

}
