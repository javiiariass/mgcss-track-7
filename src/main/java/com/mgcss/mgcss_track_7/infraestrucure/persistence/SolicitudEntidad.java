package com.mgcss.mgcss_track_7.infraestrucure.persistence;

import com.mgcss.mgcss_track_7.domain.Solicitud;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import com.mgcss.mgcss_track_7.domain.Cliente;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "solicitudes")
public class SolicitudEntidad {
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntidad cliente;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToOne
    @JoinColumn(name = "tecnico_id")
    private TecnicoEntidad tecnicoAsignado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Solicitud.estadoSolicitudes estado;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "solicitud_historial", joinColumns = @JoinColumn(name = "solicitud_id"))
    @Column(name = "historico_estado")

    private List<Solicitud.estadoSolicitudes> historico;

    @Column(name = "FechaCreacion")
    private Date fechaCreacion;

    @Column(name = "FechaCierre")
    private Date fechaCierre;

    @Column(name = "FechaReapertura")
    private Date fechaReapertura;

    @Column(name = "TiempoResolucionDias")
    private long tiempoResolucionDias;

    public SolicitudEntidad(Long id, ClienteEntidad cliente, String descripcion, TecnicoEntidad tecnicoAsignado) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = Solicitud.estadoSolicitudes.ABIERTA;
        this.cliente = cliente;
        this.tecnicoAsignado = tecnicoAsignado;
        this.historico = new ArrayList<>();
        this.historico.add(this.estado);
        this.fechaCreacion = new Date();

        if (this.cliente.getTipoCliente() == Cliente.tipoCliente.PREMIUM) {
            this.tiempoResolucionDias = 24;
        } else {
            this.tiempoResolucionDias = 48;
        }
        this.fechaCierre = new Date(this.fechaCreacion.getTime() + this.tiempoResolucionDias * 24 * 60 * 60 * 1000);
    }

}
