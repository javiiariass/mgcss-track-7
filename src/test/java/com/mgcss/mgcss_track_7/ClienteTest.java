package com.mgcss.mgcss_track_7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.Cliente;

class ClienteTest {

	@Test
	void testClienteConstructorVacioEstadoInicial() {
		Cliente cliente = new Cliente();
		assertNull(cliente.getId());
		assertNull(cliente.getNombre());
		assertNull(cliente.getEmail());
		assertNull(cliente.getTipo());
	}

	@Test
	void testClienteGettersSetters() {
		Cliente cliente = new Cliente();
		cliente.setId(10L);
		cliente.setNombre("Juan Pérez");
		cliente.setEmail("juan@mail.com");
		cliente.setTipo(Cliente.tipoCliente.PREMIUM);

		assertEquals(10L, cliente.getId());
		assertEquals("Juan Pérez", cliente.getNombre());
		assertEquals("juan@mail.com", cliente.getEmail());
		assertEquals(Cliente.tipoCliente.PREMIUM, cliente.getTipo());
	}

	@Test
	void testClienteConstructorConParametros() {
		Cliente cliente = new Cliente("Ana", "ana@mail.com", Cliente.tipoCliente.STANDARD);

		assertEquals("Ana", cliente.getNombre());
		assertEquals("ana@mail.com", cliente.getEmail());
		assertEquals(Cliente.tipoCliente.STANDARD, cliente.getTipo());
	}

}
