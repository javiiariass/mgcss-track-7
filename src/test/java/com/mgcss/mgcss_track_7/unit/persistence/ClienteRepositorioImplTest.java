package com.mgcss.mgcss_track_7.unit.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.mgcss.mgcss_track_7.domain.Cliente;
import com.mgcss.mgcss_track_7.infraestrucure.persistence.ClienteEntidad;
import com.mgcss.mgcss_track_7.infraestrucure.persistence.ClienteRepositorioImpl;
import com.mgcss.mgcss_track_7.infraestrucure.persistence.JpaClienteRepositorio;

class ClienteRepositorioImplTest {

    @Test
    void findByIdEncontrado() {
        JpaClienteRepositorio jpa = Mockito.mock(JpaClienteRepositorio.class);
        ClienteRepositorioImpl impl = new ClienteRepositorioImpl(jpa);

        ClienteEntidad entidad = new ClienteEntidad(1L, "Ana", "ana@mail.com", Cliente.tipoCliente.PREMIUM);
        when(jpa.findById(1L)).thenReturn(Optional.of(entidad));

        Optional<Cliente> result = impl.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Ana", result.get().getNombre());
        assertEquals("ana@mail.com", result.get().getEmail());
        assertEquals(Cliente.tipoCliente.PREMIUM, result.get().getTipo());
    }

    @Test
    void findByIdNoEncontrado() {
        JpaClienteRepositorio jpa = Mockito.mock(JpaClienteRepositorio.class);
        ClienteRepositorioImpl impl = new ClienteRepositorioImpl(jpa);
        when(jpa.findById(99L)).thenReturn(Optional.empty());

        assertTrue(impl.findById(99L).isEmpty());
    }

    @Test
    void save() {
        JpaClienteRepositorio jpa = Mockito.mock(JpaClienteRepositorio.class);
        ClienteRepositorioImpl impl = new ClienteRepositorioImpl(jpa);

        Cliente cliente = new Cliente("Juan", "juan@mail.com", Cliente.tipoCliente.STANDARD);
        cliente.setId(1L);
        ClienteEntidad saved = new ClienteEntidad(1L, "Juan", "juan@mail.com", Cliente.tipoCliente.STANDARD);
        when(jpa.save(any(ClienteEntidad.class))).thenReturn(saved);

        Cliente result = impl.save(cliente);
        assertEquals(1L, result.getId());
        assertEquals("Juan", result.getNombre());
        assertEquals("juan@mail.com", result.getEmail());
        assertEquals(Cliente.tipoCliente.STANDARD, result.getTipo());
    }
}
