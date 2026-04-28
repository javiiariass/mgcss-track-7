package com.mgcss.mgcss_track_7.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.mgcss.mgcss_track_7.domain.Cliente;
import com.mgcss.mgcss_track_7.infraestrucure.persistence.ClienteRepositorio;
import com.mgcss.mgcss_track_7.service.ServicioCliente;

class ServicioClienteTest {

    @Test
    void encontrarClientePorId() {
        ClienteRepositorio repositorio = Mockito.mock(ClienteRepositorio.class);
        ServicioCliente servicio = new ServicioCliente(repositorio);

        Cliente cliente = new Cliente();
        cliente.setId(1L);

        when(repositorio.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> resultado = servicio.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        verify(repositorio).findById(1L);
    }

    @Test
    void guardarCliente() {
        ClienteRepositorio repositorio = Mockito.mock(ClienteRepositorio.class);
        ServicioCliente servicio = new ServicioCliente(repositorio);

        Cliente cliente = new Cliente();
        cliente.setId(2L);

        when(repositorio.save(cliente)).thenReturn(cliente);

        Cliente resultado = servicio.save(cliente);

        assertEquals(2L, resultado.getId());
        verify(repositorio).save(cliente);
    }

    @Test
    void crearClienteData() {
        ClienteRepositorio repositorio = Mockito.mock(ClienteRepositorio.class);
        ServicioCliente servicio = new ServicioCliente(repositorio);

        when(repositorio.save(Mockito.any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cliente resultado = servicio.crearCliente("Juan Perez", "juan@example.com", Cliente.tipoCliente.STANDARD);

        assertEquals("Juan Perez", resultado.getNombre());
        assertEquals("juan@example.com", resultado.getEmail());
        assertEquals(Cliente.tipoCliente.STANDARD, resultado.getTipo());
        verify(repositorio).save(Mockito.any(Cliente.class));
    }
}