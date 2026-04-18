package com.mgcss.service;

import java.util.Optional;

import com.mgcss.domain.Solicitud;
import com.mgcss.domain.SolicitudRepositorio;
import com.mgcss.domain.Tecnico;
import java.util.List;

public class ServicioSolicitud {

    public final SolicitudRepositorio solicitudRepositorio;
    List<Solicitud> solicitudes;

    public ServicioSolicitud(SolicitudRepositorio solicitudRepositorio) {
        this.solicitudRepositorio = solicitudRepositorio;
    }

    public boolean cerrarSolicitud(Solicitud solicitud) {
        if (solicitud.getEstado() == Solicitud.estadoSolicitudes.ABIERTA)
            return false;

        solicitud.setEstado(Solicitud.estadoSolicitudes.CERRADA);
        return true;
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
        if (solicitudOpt.isPresent()) {
            Solicitud solicitud = solicitudOpt.get();
            solicitud.asignarTecnico(tecnico);
            return solicitudRepositorio.save(solicitud);

        }
        return null;

    }

    public Solicitud cambiarEstado(Long idSolicitud, Solicitud.estadoSolicitudes nuevoEstado) {

        Optional<Solicitud> solicitudOpt = solicitudRepositorio.findById(idSolicitud);
        if (solicitudOpt.isPresent()) {
            Solicitud solicitud = solicitudOpt.get();
            solicitud.setEstado(nuevoEstado);
            return solicitudRepositorio.save(solicitud);

        }
        return null;

    }

}