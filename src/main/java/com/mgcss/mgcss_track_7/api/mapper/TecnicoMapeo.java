package com.mgcss.mgcss_track_7.api.mapper;

import com.mgcss.mgcss_track_7.api.dto.TecnicoRespuestaDTO;
import com.mgcss.mgcss_track_7.domain.Tecnico;

public class TecnicoMapeo {
    private TecnicoMapeo() {
    }

    public static TecnicoRespuestaDTO toTecnicoRespuestaDTO(Tecnico tecnico) {
        return new TecnicoRespuestaDTO(
                tecnico.getId(),
                tecnico.getNombre(),
                tecnico.getEspecialidad(),
                tecnico.isActivo(),
                tecnico.isTrabajando());
    }
}
