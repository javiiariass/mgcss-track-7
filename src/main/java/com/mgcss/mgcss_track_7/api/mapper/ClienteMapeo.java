package com.mgcss.mgcss_track_7.api.mapper;

import com.mgcss.mgcss_track_7.api.dto.ClienteRespuestaDTO;
import com.mgcss.mgcss_track_7.domain.Cliente;

public class ClienteMapeo {
    private ClienteMapeo() {
    }

    public static ClienteRespuestaDTO toClienteRespuestaDTO(Cliente cliente) {
        return new ClienteRespuestaDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getTipo().name());
    }
}
