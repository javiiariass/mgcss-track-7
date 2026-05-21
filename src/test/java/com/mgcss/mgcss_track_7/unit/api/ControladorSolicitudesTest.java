package com.mgcss.mgcss_track_7.unit.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.mgcss.mgcss_track_7.api.controladores.ControladorSolicitud;
import com.mgcss.mgcss_track_7.domain.Solicitud;
import com.mgcss.mgcss_track_7.domain.Tecnico;
import com.mgcss.mgcss_track_7.service.ServicioSolicitud;
import com.mgcss.mgcss_track_7.service.ServicioTecnico;

@WebMvcTest(ControladorSolicitud.class)
class ControladorSolicitudesTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServicioSolicitud solicitudService;

    @MockitoBean
    private ServicioTecnico tecnicoService;

    @Test
    void crearSolicitud() throws Exception {
        Solicitud solicitud = new Solicitud(1L, null, "Descripción Generica", null);
        when(solicitudService.crearSolicitudSinClienteYdescripcionValida(anyString())).thenReturn(solicitud);

        String json = """
                {
                    "descripcion": "Descripción Generica"
                }
                """;
        mockMvc.perform(post("/api/solicitudes")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/api/solicitudes")
                .contentType("application/json").content(json))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.descripcion").value("Descripción Generica"))
            .andExpect(jsonPath("$.estado").value("ABIERTA"));
    }

    @Test
    void cambiarEstadoTest() throws Exception {
        Solicitud solicitud = new Solicitud(1L, null, "Descripción Generica", null);
        when(solicitudService.findById(1L)).thenReturn(java.util.Optional.of(solicitud));
        String json = """
                {
                    "estado": "EN_PROGRESO"
                }
                """;
        mockMvc.perform(patch("/api/solicitudes/1")
                .contentType("application/json").content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.descripcion").value("Descripción Generica"))
                .andExpect(jsonPath("$.estado").value("EN_PROCESO"));
    }

    @Test
    void obtenerSolicitudPorIdEncontrado() throws Exception {
        Solicitud solicitud = new Solicitud(1L, null, "Descripción Generica", null);
        when(solicitudService.findById(1L)).thenReturn(java.util.Optional.of(solicitud));

        mockMvc.perform(get("/api/solicitudes/1").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("ABIERTA"));
    }

    @Test
    void obtenerSolicitudPorIdNoEncontrado() throws Exception {
        when(solicitudService.findById(99L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/api/solicitudes/99").param("id", "99"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerTodasLasSolicitudes() throws Exception {
        List<Solicitud> lista = List.of(
                new Solicitud(1L, null, "Primera", null),
                new Solicitud(2L, null, "Segunda", null));
        when(solicitudService.findAll()).thenReturn(lista);

        mockMvc.perform(get("/api/solicitudes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void cambiarEstadoPut() throws Exception {
        Solicitud solicitud = new Solicitud(1L, null, "Desc", null);
        solicitud.siguienteEstado();
        when(solicitudService.cambiarEstado(1L)).thenReturn(solicitud);

        mockMvc.perform(put("/api/solicitudes/1/estado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("EN_PROCESO"));
    }

    @Test
    void reabrirSolicitudEncontrado() throws Exception {
        Solicitud solicitud = new Solicitud(1L, null, "Desc", null);
        Solicitud solicitudReabierta = new Solicitud(1L, null, "Desc", null);
        when(solicitudService.findById(1L)).thenReturn(java.util.Optional.of(solicitud));
        when(solicitudService.reabrirSolicitud(eq(1L), isNull())).thenReturn(solicitudReabierta);

        mockMvc.perform(put("/api/solicitudes/1/reabrir"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void reabrirSolicitudNoEncontrado() throws Exception {
        when(solicitudService.findById(99L)).thenReturn(java.util.Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () ->
                mockMvc.perform(put("/api/solicitudes/99/reabrir"))
                        .andReturn());
    }

    @Test
    void cambiarEstadoPatchNoEncontrado() throws Exception {
        when(solicitudService.findById(99L)).thenReturn(java.util.Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () ->
                mockMvc.perform(patch("/api/solicitudes/99"))
                        .andReturn());
    }

    @Test
    void asignarTecnicoNoEncontrado() throws Exception {
        when(solicitudService.findById(99L)).thenReturn(java.util.Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () ->
                mockMvc.perform(put("/api/solicitudes/99/tecnico")
                        .contentType("application/json")
                        .content("2"))
                        .andReturn());
    }

    @Test
    void asignarTecnico() throws Exception {
        Solicitud solicitud = new Solicitud(1L, null, "Desc", null);
        Tecnico tecnico = new Tecnico(2L, "Ana", "Redes");
        Solicitud solicitudConTecnico = new Solicitud(1L, null, "Desc", tecnico);
        when(solicitudService.findById(1L)).thenReturn(java.util.Optional.of(solicitud));
        when(tecnicoService.findById(2L)).thenReturn(java.util.Optional.of(tecnico));
        when(solicitudService.asignarTecnico(eq(1L), any(Tecnico.class))).thenReturn(solicitudConTecnico);

        mockMvc.perform(put("/api/solicitudes/1/tecnico")
                .contentType("application/json")
                .content("2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.tecnicoAsignado").value("Ana"));
    }

}
