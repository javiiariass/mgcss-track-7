package com.mgcss.mgcss_track_7.api.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgcss.mgcss_track_7.api.dto.TecnicoPeticionDTO;
import com.mgcss.mgcss_track_7.api.dto.TecnicoRespuestaDTO;
import com.mgcss.mgcss_track_7.api.mapper.TecnicoMapeo;
import com.mgcss.mgcss_track_7.domain.Tecnico;
import com.mgcss.mgcss_track_7.service.ServicioTecnico;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Técnicos", description = "Gestión de técnicos de soporte")
@RestController
@RequestMapping("/api/tecnicos")
public class ControladorTecnico {
    private final ServicioTecnico servicioTecnico;

    public ControladorTecnico(ServicioTecnico servicioTecnico) {
        this.servicioTecnico = servicioTecnico;
    }

    @Operation(summary = "Crear un técnico", description = "Registra un nuevo técnico con nombre y especialidad")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Técnico creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public TecnicoRespuestaDTO crearTecnico(@RequestBody TecnicoPeticionDTO tecnicoPeticionDTO) {
        Tecnico tecnico = servicioTecnico.crearTecnico(
                null,
                tecnicoPeticionDTO.getNombre(),
                tecnicoPeticionDTO.getEspecialidad());
        return TecnicoMapeo.toTecnicoRespuestaDTO(tecnico);
    }

    @Operation(summary = "Obtener técnico por ID", description = "Devuelve los datos de un técnico a partir de su identificador")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Técnico encontrado o null si no existe")
    })
    @GetMapping("/{id}")
    public TecnicoRespuestaDTO obtenerTecnicoPorId(
            @Parameter(description = "ID del técnico", example = "1") @PathVariable Long id) {
        Optional<Tecnico> tecnico = servicioTecnico.findById(id);
        if (tecnico.isPresent()) {
            return TecnicoMapeo.toTecnicoRespuestaDTO(tecnico.get());
        } else {
            return null;
        }
    }

    @Operation(summary = "Activar o desactivar un técnico", description = "Actualiza el estado activo/inactivo de un técnico. Un técnico inactivo no puede recibir solicitudes")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Técnico no encontrado")
    })
    @PutMapping("/{id}/activo")
    public TecnicoRespuestaDTO establecerActivo(
            @Parameter(description = "ID del técnico", example = "1") @PathVariable Long id,
            @Parameter(description = "true para activar, false para desactivar") @RequestBody boolean activo) {
        servicioTecnico.estableceTecnicoActivo(id, activo);
        Optional<Tecnico> tecnico = servicioTecnico.findById(id);
        if (tecnico.isPresent()) {
            return TecnicoMapeo.toTecnicoRespuestaDTO(tecnico.get());
        } else {
            throw new IllegalArgumentException("Tecnico no encontrado con id: " + id);
        }
    }

    @Operation(summary = "Marcar técnico como trabajando", description = "Actualiza el estado de trabajo de un técnico. Un técnico trabajando no puede ser asignado a nuevas solicitudes")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Técnico no encontrado")
    })
    @PutMapping("/{id}/trabajando")
    public TecnicoRespuestaDTO establecerTrabajando(
            @Parameter(description = "ID del técnico", example = "1") @PathVariable Long id,
            @Parameter(description = "true si está trabajando, false si está disponible") @RequestBody boolean trabajando) {
        servicioTecnico.establecerTecnicoTrabajando(id, trabajando);
        Optional<Tecnico> tecnico = servicioTecnico.findById(id);
        if (tecnico.isPresent()) {
            return TecnicoMapeo.toTecnicoRespuestaDTO(tecnico.get());
        } else {
            throw new IllegalArgumentException("Tecnico no encontrado con id: " + id);
        }
    }
}
