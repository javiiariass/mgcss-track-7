package com.mgcss.mgcss_track_7.unit.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.mgcss.mgcss_track_7.api.controladores.ControladorCliente;
import com.mgcss.mgcss_track_7.domain.Cliente;
import com.mgcss.mgcss_track_7.service.ServicioCliente;

import java.util.Optional;

@WebMvcTest(ControladorCliente.class)
class ControladorClienteTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServicioCliente servicioCliente;

    @Test
    void crearCliente() throws Exception {
        Cliente cliente = new Cliente("Juan", "juan@email.com", Cliente.tipoCliente.PREMIUM);
        cliente.setId(1L);
        when(servicioCliente.crearCliente(any(), any(), any())).thenReturn(cliente);

        String json = """
            {
                \"nombre\": \"Juan\",
                \"email\": \"juan@email.com\",
                \"tipo\": \"PREMIUM\"
            }
            """;
        mockMvc.perform(post("/api/clientes")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.email").value("juan@email.com"))
                .andExpect(jsonPath("$.tipo").value("PREMIUM"));
    }

    @Test
    void obtenerClientePorId() throws Exception {
        Cliente cliente = new Cliente("Ana", "ana@email.com", Cliente.tipoCliente.STANDARD);
        cliente.setId(2L);
        when(servicioCliente.findById(2L)).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/api/clientes/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nombre").value("Ana"))
                .andExpect(jsonPath("$.email").value("ana@email.com"))
                .andExpect(jsonPath("$.tipo").value("STANDARD"));
    }
}
