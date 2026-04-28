package com.mgcss.mgcss_track_7.infraestrucure.persistence;
import java.util.Optional;

import com.mgcss.mgcss_track_7.domain.Tecnico;



public interface TecnicoRepositorio{
    Tecnico save(Tecnico tecnico);

    Optional<Tecnico> findById(Long id);
}
