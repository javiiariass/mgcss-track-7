package com.mgcss.mgcss_track_7.unit.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.mgcss.mgcss_track_7.api.dto.ClientePeticionDTO;
import com.mgcss.mgcss_track_7.api.dto.ClienteRespuestaDTO;
import com.mgcss.mgcss_track_7.api.dto.SolicitudPeticionDTO;
import com.mgcss.mgcss_track_7.api.dto.SolicitudRespuestaDTO;
import com.mgcss.mgcss_track_7.api.dto.TecnicoPeticionDTO;
import com.mgcss.mgcss_track_7.api.dto.TecnicoRespuestaDTO;

class DtosTest {

    @Test
    void clientePeticionDTO() {
        ClientePeticionDTO dto = new ClientePeticionDTO();
        dto.setNombre("Ana");
        dto.setEmail("ana@mail.com");
        dto.setTipo("STANDARD");
        assertEquals("Ana", dto.getNombre());
        assertEquals("ana@mail.com", dto.getEmail());
        assertEquals("STANDARD", dto.getTipo());

        ClientePeticionDTO dto2 = new ClientePeticionDTO("Luis", "luis@mail.com", "PREMIUM");
        assertEquals("Luis", dto2.getNombre());
        assertEquals("luis@mail.com", dto2.getEmail());
        assertEquals("PREMIUM", dto2.getTipo());
    }

    @Test
    void clienteRespuestaDTO() {
        ClienteRespuestaDTO dto = new ClienteRespuestaDTO();
        dto.setId(1L);
        dto.setNombre("Ana");
        dto.setEmail("ana@mail.com");
        dto.setTipo("STANDARD");
        assertEquals(1L, dto.getId());
        assertEquals("Ana", dto.getNombre());
        assertEquals("ana@mail.com", dto.getEmail());
        assertEquals("STANDARD", dto.getTipo());

        ClienteRespuestaDTO dto2 = new ClienteRespuestaDTO(2L, "Luis", "luis@mail.com", "PREMIUM");
        assertEquals(2L, dto2.getId());
        assertEquals("Luis", dto2.getNombre());
        assertEquals("PREMIUM", dto2.getTipo());
    }

    @Test
    void tecnicoPeticionDTO() {
        TecnicoPeticionDTO dto = new TecnicoPeticionDTO();
        dto.setNombre("Carlos");
        dto.setEspecialidad("Redes");
        assertEquals("Carlos", dto.getNombre());
        assertEquals("Redes", dto.getEspecialidad());

        TecnicoPeticionDTO dto2 = new TecnicoPeticionDTO("Pedro", "Hardware");
        assertEquals("Pedro", dto2.getNombre());
        assertEquals("Hardware", dto2.getEspecialidad());
    }

    @Test
    void tecnicoRespuestaDTO() {
        TecnicoRespuestaDTO dto = new TecnicoRespuestaDTO();
        dto.setId(10L);
        dto.setNombre("Julia");
        dto.setEspecialidad("Software");
        dto.setActivo(true);
        dto.setTrabajando(false);
        assertEquals(10L, dto.getId());
        assertEquals("Julia", dto.getNombre());
        assertEquals("Software", dto.getEspecialidad());
        assertTrue(dto.isActivo());
        assertFalse(dto.isTrabajando());

        TecnicoRespuestaDTO dto2 = new TecnicoRespuestaDTO(11L, "Tom", "Redes", false, true);
        assertEquals(11L, dto2.getId());
        assertEquals("Tom", dto2.getNombre());
        assertFalse(dto2.isActivo());
        assertTrue(dto2.isTrabajando());
    }

    @Test
    void solicitudPeticionDTO() {
        SolicitudPeticionDTO dto = new SolicitudPeticionDTO();
        dto.setDescripcion("Fallo crítico");
        assertEquals("Fallo crítico", dto.getDescripcion());

        SolicitudPeticionDTO dto2 = new SolicitudPeticionDTO("Otro fallo");
        assertEquals("Otro fallo", dto2.getDescripcion());
    }

    @Test
    void solicitudRespuestaDTO() {
        SolicitudRespuestaDTO dto = new SolicitudRespuestaDTO();
        dto.setId(5L);
        dto.setDescripcion("Error");
        dto.setEstado("ABIERTA");
        dto.setTecnicoAsignado("Juan");
        assertEquals(5L, dto.getId());
        assertEquals("Error", dto.getDescripcion());
        assertEquals("ABIERTA", dto.getEstado());
        assertEquals("Juan", dto.getTecnicoAsignado());

        SolicitudRespuestaDTO dto2 = new SolicitudRespuestaDTO(6L, "desc", "CERRADA", null);
        assertEquals(6L, dto2.getId());
        assertEquals("CERRADA", dto2.getEstado());
        assertNull(dto2.getTecnicoAsignado());
    }
}
