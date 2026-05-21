package com.mgcss.mgcss_track_7.api.mapper;

import com.mgcss.mgcss_track_7.api.dto.SolicitudRespuestaDTO;
import com.mgcss.mgcss_track_7.domain.Solicitud;

public class SolicitudMapeo {
    private SolicitudMapeo() {
    }


    public static SolicitudRespuestaDTO toSolicitudRespuestaDTO(Solicitud solicitud) {
        String tecnicoControladorSolicitud = null; // Logica de mapeo para hacer una pregunta valida al DTO
        
        if (solicitud.getTecnicoAsignado() != null) {
            tecnicoControladorSolicitud = solicitud.getTecnicoAsignado().getNombre();
        }

        return new SolicitudRespuestaDTO(
                solicitud.getId(),
                solicitud.getDescripcion(),
                solicitud.getEstado().name(),                
                tecnicoControladorSolicitud);
    }
}
