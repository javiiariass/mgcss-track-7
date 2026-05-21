package com.mgcss.mgcss_track_7.api.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgcss.mgcss_track_7.api.dto.TecnicoPeticionDTO;
import com.mgcss.mgcss_track_7.api.dto.TecnicoRespuestaDTO;
import com.mgcss.mgcss_track_7.api.mapper.TecnicoMapeo;
import com.mgcss.mgcss_track_7.domain.Tecnico;
import com.mgcss.mgcss_track_7.service.ServicioTecnico;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/tecnicos")
public class ControladorTecnico {
    private final ServicioTecnico servicioTecnico;

    public ControladorTecnico(ServicioTecnico servicioTecnico) {
        this.servicioTecnico = servicioTecnico;
    }

    @PostMapping
    public TecnicoRespuestaDTO crearTecnico(@RequestBody TecnicoPeticionDTO tecnicoPeticionDTO) {
        Tecnico tecnico = servicioTecnico.crearTecnico(
                null,
                tecnicoPeticionDTO.getNombre(),
                tecnicoPeticionDTO.getEspecialidad());
        return TecnicoMapeo.toTecnicoRespuestaDTO(tecnico);
    }

    @GetMapping("/{id}")
    public TecnicoRespuestaDTO obtenerTecnicoPorId(@PathVariable Long id) {
        Optional<Tecnico> tecnico = servicioTecnico.findById(id);
        if (tecnico.isPresent()) {
            return TecnicoMapeo.toTecnicoRespuestaDTO(tecnico.get());
        } else {
            return null;
        }
    }

    @PutMapping("/{id}/activo")
    public TecnicoRespuestaDTO establecerActivo(@PathVariable Long id, @RequestBody boolean activo) {
        servicioTecnico.estableceTecnicoActivo(id, activo);
        Optional<Tecnico> tecnico = servicioTecnico.findById(id);
        if (tecnico.isPresent()) {
            return TecnicoMapeo.toTecnicoRespuestaDTO(tecnico.get());
        } else {
            throw new IllegalArgumentException("Tecnico no encontrado con id: " + id);
        }
    }

    @PutMapping("/{id}/trabajando")
    public TecnicoRespuestaDTO establecerTrabajando(@PathVariable Long id, @RequestBody boolean trabajando) {
        servicioTecnico.establecerTecnicoTrabajando(id, trabajando);
        Optional<Tecnico> tecnico = servicioTecnico.findById(id);
        if (tecnico.isPresent()) {
            return TecnicoMapeo.toTecnicoRespuestaDTO(tecnico.get());
        } else {
            throw new IllegalArgumentException("Tecnico no encontrado con id: " + id);
        }
    }
}
