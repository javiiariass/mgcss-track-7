package com.mgcss.domain;
import java.util.List;
import java.util.Optional;

public interface SolicitudRepository {

    Solicitud save(Solicitud solicitud);
    Optional<Solicitud> findById(Long id);
    List<Solicitud> obtenerSolicitudes();
    // void actualizarSolicitud(Solicitud solicitud);
    // void eliminarSolicitud(Long id);    

}
