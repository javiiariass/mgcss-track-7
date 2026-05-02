package com.mgcss.mgcss_track_7.domain;

import java.util.Date;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Solicitud {
    public enum estadoSolicitudes {
        ABIERTA, EN_PROCESO, CERRADA;

        public estadoSolicitudes siguiente() {
            estadoSolicitudes[] vals = values();
            if ((this.ordinal() + 1) == vals.length)
                return CERRADA;
            else
                return vals[(this.ordinal() + 1)];
        }
    }

    private Long id;

    private Cliente cliente;
    private String descripcion;
    @Setter(AccessLevel.NONE)
    private Tecnico tecnicoAsignado;
    private Date fechaCreacion;
    private Date fechaCierre = null;

    // Quitamos setter para no permitir mutar estado fuera de los métodos\
    // que cumplan las reglas de negocio
    @Setter(AccessLevel.NONE)
    private estadoSolicitudes estado = estadoSolicitudes.ABIERTA;

    public Solicitud(Long id, Cliente cliente, String descripcion, Tecnico tecnicoAsignado) {
        this.id = id;
        this.cliente = cliente;
        this.descripcion = descripcion;
        this.tecnicoAsignado = tecnicoAsignado;
        this.fechaCreacion = new Date();
    }

    public boolean asignarTecnico(Tecnico tecnico) {
        boolean asignado = false;
        if (tecnico == null) {
            if (tecnicoAsignado != null) {
                tecnicoAsignado.setTrabajando(false);
            }
            this.tecnicoAsignado = null;
            asignado = true;
        } else if (tecnico.isActivo() && !tecnico.isTrabajando() && tecnicoAsignado == null) {
            this.tecnicoAsignado = tecnico;
            tecnico.setTrabajando(true);
            asignado = true;
        }
        return asignado;
    }

    public void cerrar() {
        if (this.estado != estadoSolicitudes.EN_PROCESO) {
            throw new IllegalStateException("No se puede cerrar una solititud que no esté en proceso\n");
        }
        this.estado = estadoSolicitudes.CERRADA;
    }

    public void siguienteEstado() {
        this.estado = this.estado.siguiente();
    }

}
