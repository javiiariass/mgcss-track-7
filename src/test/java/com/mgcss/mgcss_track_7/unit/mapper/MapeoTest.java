package com.mgcss.mgcss_track_7.unit.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mgcss.mgcss_track_7.api.dto.ClienteRespuestaDTO;
import com.mgcss.mgcss_track_7.api.dto.SolicitudRespuestaDTO;
import com.mgcss.mgcss_track_7.api.dto.TecnicoRespuestaDTO;
import com.mgcss.mgcss_track_7.api.mapper.ClienteMapeo;
import com.mgcss.mgcss_track_7.api.mapper.SolicitudMapeo;
import com.mgcss.mgcss_track_7.api.mapper.TecnicoMapeo;
import com.mgcss.mgcss_track_7.domain.Cliente;
import com.mgcss.mgcss_track_7.domain.Solicitud;
import com.mgcss.mgcss_track_7.domain.Tecnico;

class MapeoTest {

    @Test
    void clienteMapeoToDTO() {
        Cliente cliente = new Cliente("Ana", "ana@mail.com", Cliente.tipoCliente.PREMIUM);
        cliente.setId(1L);

        ClienteRespuestaDTO dto = ClienteMapeo.toClienteRespuestaDTO(cliente);

        assertEquals(1L, dto.getId());
        assertEquals("Ana", dto.getNombre());
        assertEquals("ana@mail.com", dto.getEmail());
        assertEquals("PREMIUM", dto.getTipo());
    }

    @Test
    void tecnicoMapeoToDTO() {
        Tecnico tecnico = new Tecnico(2L, "Luis", "Redes");

        TecnicoRespuestaDTO dto = TecnicoMapeo.toTecnicoRespuestaDTO(tecnico);

        assertEquals(2L, dto.getId());
        assertEquals("Luis", dto.getNombre());
        assertEquals("Redes", dto.getEspecialidad());
        assertTrue(dto.isActivo());
        assertFalse(dto.isTrabajando());
    }

    @Test
    void solicitudMapeoToDTOSinTecnico() {
        Solicitud solicitud = new Solicitud(3L, null, "Descripción", null);

        SolicitudRespuestaDTO dto = SolicitudMapeo.toSolicitudRespuestaDTO(solicitud);

        assertEquals(3L, dto.getId());
        assertEquals("Descripción", dto.getDescripcion());
        assertEquals("ABIERTA", dto.getEstado());
        assertNull(dto.getTecnicoAsignado());
    }

    @Test
    void solicitudMapeoToDTOConTecnico() {
        Tecnico tecnico = new Tecnico(1L, "Pedro", "Hardware");
        Solicitud solicitud = new Solicitud(4L, null, "Fallo hardware", tecnico);

        SolicitudRespuestaDTO dto = SolicitudMapeo.toSolicitudRespuestaDTO(solicitud);

        assertEquals(4L, dto.getId());
        assertEquals("Pedro", dto.getTecnicoAsignado());
    }
}
