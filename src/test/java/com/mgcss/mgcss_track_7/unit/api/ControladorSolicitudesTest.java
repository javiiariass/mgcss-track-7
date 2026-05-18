package com.mgcss.mgcss_track_7.unit.api;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import com.mgcss.mgcss_track_7.api.controladores.ControladorSolicitud;
import com.mgcss.mgcss_track_7.domain.Solicitud;
import com.mgcss.mgcss_track_7.service.ServicioSolicitud;
import com.mgcss.mgcss_track_7.service.ServicioTecnico;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
            .andExpect(status().isOk())
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
                .andExpect(jsonPath("$.estado").value("EN_PROGRESO"));
    }

    

}
