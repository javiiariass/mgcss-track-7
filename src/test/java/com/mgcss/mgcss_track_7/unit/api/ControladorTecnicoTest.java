package com.mgcss.mgcss_track_7.unit.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.mgcss.mgcss_track_7.api.controladores.ControladorTecnico;
import com.mgcss.mgcss_track_7.domain.Tecnico;
import com.mgcss.mgcss_track_7.service.ServicioTecnico;

import java.util.Optional;

@WebMvcTest(ControladorTecnico.class)
class ControladorTecnicoTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServicioTecnico servicioTecnico;

    @Test
    void crearTecnico() throws Exception {
        Tecnico tecnico = new Tecnico(1L, "Pedro", "Redes");
        when(servicioTecnico.crearTecnico(any(), any(), any())).thenReturn(tecnico);

        String json = """
            {
                \"nombre\": \"Pedro\",
                \"especialidad\": \"Redes\"
            }
            """;
        mockMvc.perform(post("/api/tecnicos")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Pedro"))
                .andExpect(jsonPath("$.especialidad").value("Redes"));
    }

    @Test
    void obtenerTecnicoPorId() throws Exception {
        Tecnico tecnico = new Tecnico(2L, "Laura", "Soporte");
        when(servicioTecnico.findById(2L)).thenReturn(Optional.of(tecnico));

        mockMvc.perform(get("/api/tecnicos/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nombre").value("Laura"))
                .andExpect(jsonPath("$.especialidad").value("Soporte"));
    }

    @Test
    void establecerActivo() throws Exception {
        Tecnico tecnico = new Tecnico(3L, "Mario", "Infraestructura");
        tecnico.setActivo(true);
        when(servicioTecnico.findById(3L)).thenReturn(Optional.of(tecnico));

        mockMvc.perform(put("/api/tecnicos/3/activo")
                .contentType("application/json")
                .content("true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.activo").value(true));
    }

    @Test
    void obtenerTecnicoPorIdNoEncontrado() throws Exception {
        when(servicioTecnico.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/tecnicos/99"))
                .andExpect(status().isOk());
    }

    @Test
    void establecerActivoNoEncontrado() throws Exception {
        when(servicioTecnico.findById(99L)).thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () ->
                mockMvc.perform(put("/api/tecnicos/99/activo")
                        .contentType("application/json")
                        .content("true"))
                        .andReturn());
    }

    @Test
    void establecerTrabajando() throws Exception {
        Tecnico tecnico = new Tecnico(4L, "Sara", "Redes");
        tecnico.setTrabajando(true);
        when(servicioTecnico.findById(4L)).thenReturn(Optional.of(tecnico));

        mockMvc.perform(put("/api/tecnicos/4/trabajando")
                .contentType("application/json")
                .content("true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.trabajando").value(true));
    }

    @Test
    void establecerTrabajandoNoEncontrado() throws Exception {
        when(servicioTecnico.findById(99L)).thenReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () ->
                mockMvc.perform(put("/api/tecnicos/99/trabajando")
                        .contentType("application/json")
                        .content("true"))
                        .andReturn());
    }
}
