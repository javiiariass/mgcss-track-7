package com.mgcss.mgcss_track_7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.mgcss.domain.Cliente;
import com.mgcss.domain.Tecnico;
import com.mgcss.domain.Solicitud;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void dominioTest() {
		// Probamos instanciar las clases para darles cobertura
		Cliente cliente = new Cliente();
		cliente.setId(1L);
		cliente.setNombre("Paco");
		assertEquals(1L, cliente.getId());
		assertEquals("Paco", cliente.getNombre());

		Tecnico tecnico = new Tecnico(1L, "María", "Hardware", true);
		assertEquals("María", tecnico.getNombre());

		Solicitud solicitud = new Solicitud(1L, cliente, "Problema PC", tecnico);
		assertEquals("Problema PC", solicitud.getDescripcion());
		assertNotNull(solicitud.getFechaCreacion());
		assertEquals(cliente, solicitud.getCliente());
	}

}
