package com.mgcss.mgcss_track_7.service;

import java.util.Optional;

import com.mgcss.mgcss_track_7.domain.Solicitud;
import com.mgcss.mgcss_track_7.domain.Tecnico;
import com.mgcss.mgcss_track_7.domain.Solicitud.estadoSolicitudes;
import com.mgcss.mgcss_track_7.infraestrucure.persistence.SolicitudRepositorio;

import java.util.List;

public class ServicioSolicitud {

    public final SolicitudRepositorio solicitudRepositorio;
    List<Solicitud> solicitudes;
    

    public ServicioSolicitud(SolicitudRepositorio solicitudRepositorio) {
        this.solicitudRepositorio = solicitudRepositorio;
    }

    public Optional<Solicitud> findById(Long id) {

        return solicitudRepositorio.findById(id);

    }

    public Solicitud save(Solicitud solicitud) {
        return solicitudRepositorio.save(solicitud);
    }

    public Solicitud crearSolicitud(Long id, String descripcion) {
        Solicitud solicitud = new Solicitud(id, descripcion, Solicitud.estadoSolicitudes.ABIERTA);
        return solicitudRepositorio.save(solicitud);
    }

    public Solicitud asignarTecnico(Long idSolicitud, Tecnico tecnico) {

        Optional<Solicitud> solicitudOpt = solicitudRepositorio.findById(idSolicitud);
        if (solicitudOpt.isEmpty()) {
            throw new IllegalArgumentException("Solicitud no encontrada con id: " + idSolicitud);
        }

        Solicitud solicitud = solicitudOpt.get();
        solicitud.asignarTecnico(tecnico);
        return solicitudRepositorio.save(solicitud);

    }

    public Solicitud cambiarEstado(Long idSolicitud) {

        Optional<Solicitud> solicitudOpt = solicitudRepositorio.findById(idSolicitud);
        if (solicitudOpt.isPresent()) {
            Solicitud solicitud = solicitudOpt.get();

            // Si cerrada, quitamos tecnico
            solicitud.siguienteEstado();
            if(solicitud.getEstado() == Solicitud.estadoSolicitudes.CERRADA){
                solicitud.getTecnicoAsignado().setTrabajando(false);
                solicitud.setTecnicoAsignado(null);
            }
            return solicitudRepositorio.save(solicitud);

        }
        return null;

    }

}