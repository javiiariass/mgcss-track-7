package com.mgcss.mgcss_track_7.unit.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.mgcss.mgcss_track_7.api.controladores.ControladorCliente;
import com.mgcss.mgcss_track_7.domain.Cliente;
import com.mgcss.mgcss_track_7.service.ServicioCliente;

@WebMvcTest(ControladorCliente.class)
class ControladorClienteCoberturaTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ServicioCliente servicioCliente;

    @Test
    void obtenerClientePorIdNoExiste() throws Exception {
        when(servicioCliente.findById(99L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/clientes/99"))
                .andExpect(status().isOk()); // El controlador retorna null, pero status es 200
    }
}
