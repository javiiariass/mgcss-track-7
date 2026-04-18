package com.mgcss.domain;

import java.util.Optional;



public interface SolicitudRepositorio {
    Solicitud save(Solicitud solicitud);

    Optional<Solicitud> findById(Long id);
}
