package com.mgcss.mgcss_track_7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import java.util.Date;
import org.springframework.boot.test.context.SpringBootTest;

import com.mgcss.domain.Cliente;

@SpringBootTest
class ClienteTest {
	/**************************************** */
	// TEST GETTER/SETTERS
	@Test
	void testClienteGettersSetters() {
		Cliente cliente = new Cliente();
		cliente.setId(10L);
		cliente.setNombre("Juan Pérez");

		assertEquals(10L, cliente.getId());
		assertEquals("Juan Pérez", cliente.getNombre());
	}

}
