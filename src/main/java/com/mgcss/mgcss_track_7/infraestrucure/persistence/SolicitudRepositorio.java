package com.mgcss.mgcss_track_7.infraestrucure.persistence;
import java.util.Optional;

import com.mgcss.mgcss_track_7.domain.Solicitud;



public interface SolicitudRepositorio {
    Solicitud save(Solicitud solicitud);

    Optional<Solicitud> findById(Long id);
}
