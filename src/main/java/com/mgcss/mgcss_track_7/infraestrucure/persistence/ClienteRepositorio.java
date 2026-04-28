package com.mgcss.mgcss_track_7.infraestrucure.persistence;
import java.util.Optional;

import com.mgcss.mgcss_track_7.domain.Cliente;

public interface ClienteRepositorio {
    Cliente save(Cliente cliente);

    Optional<Cliente> findById(Long id);
}
