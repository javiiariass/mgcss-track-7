package com.mgcss.mgcss_track_7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import java.util.Date;
import org.springframework.boot.test.context.SpringBootTest;

import com.mgcss.domain.Cliente;
import com.mgcss.domain.Tecnico;
import com.mgcss.domain.Solicitud;

@SpringBootTest
class ApplicationTests {


	@Test
	void testTecnicoGettersSetters() {
		Tecnico tecnico = new Tecnico();
		tecnico.setId(2L);
		tecnico.setNombre("Ana");
		tecnico.setEspecialidad("Redes");
		tecnico.setActivo(true);

		Solicitud solicitud = new Solicitud();
		tecnico.setSolicitud(solicitud);

		assertEquals(2L, tecnico.getId());
		assertEquals("Ana", tecnico.getNombre());
		assertEquals("Redes", tecnico.getEspecialidad());
		assertEquals(true, tecnico.getActivo());
		assertEquals(solicitud, tecnico.getSolicitud());
	}

	
}
