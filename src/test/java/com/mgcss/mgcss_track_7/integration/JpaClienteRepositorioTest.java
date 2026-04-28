package com.mgcss.mgcss_track_7.integration;

import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import com.mgcss.mgcss_track_7.infraestrucure.persistence.JpaClienteRepositorio;
import com.mgcss.mgcss_track_7.infraestrucure.persistence.ClienteEntidad;
import com.mgcss.mgcss_track_7.domain.Cliente;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DataJpaTest
@ActiveProfiles("test")
@Tag("integration")
class JpaClienteRepositorioTest {

    @Autowired
    private JpaClienteRepositorio clienteRepositorio;

    @Test
    void guardarEntidad() {
        ClienteEntidad nuevoCliente = new ClienteEntidad();
        nuevoCliente.setId(1L);

        ClienteEntidad clienteBD = clienteRepositorio.save(nuevoCliente);

        Optional<ClienteEntidad> clienteEncontrado = clienteRepositorio.findById(clienteBD.getId());

        assertTrue(clienteEncontrado.isPresent());
        assertEquals(clienteBD.getId(), clienteEncontrado.get().getId());
    }

    @Test
    void getterAndSettersAll() {
        ClienteEntidad entidad = new ClienteEntidad(1L, "Juan", "juan@gmail.com", Cliente.tipoCliente.PREMIUM);
        entidad.setId(1L);
        entidad.setNombre("Juan");
        entidad.setEmail("juan@gmail.com");
        entidad.setTipoCliente(Cliente.tipoCliente.PREMIUM);


        // Getters
        assertEquals(1L, entidad.getId());
        assertEquals("Juan", entidad.getNombre());
        assertEquals("juan@gmail.com", entidad.getEmail());
        assertEquals(Cliente.tipoCliente.PREMIUM, entidad.getTipoCliente());
    }
}
