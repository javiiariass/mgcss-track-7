package com.mgcss.mgcss_track_7.domain;

import java.util.Date;

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
    private Tecnico tecnicoAsignado;
    private Date fechaCreacion;
    private Date fechaCierre = null;

    private estadoSolicitudes estado;

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

    public boolean asignarTecnico(Tecnico tecnico) {
        boolean asignado = false;

        if (tecnico.isActivo() && !tecnico.isTrabajando() && tecnicoAsignado == null) {
            this.tecnicoAsignado = tecnico;
            tecnico.setTrabajando(true);
            asignado = true;
        }
        return asignado;
    }

    public void siguienteEstado(){
        this.setEstado(estado.siguiente());
    }

}
