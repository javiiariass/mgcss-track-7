package com.mgcss.mgcss_track_7.api.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgcss.mgcss_track_7.api.dto.ClientePeticionDTO;
import com.mgcss.mgcss_track_7.api.dto.ClienteRespuestaDTO;
import com.mgcss.mgcss_track_7.api.mapper.ClienteMapeo;
import com.mgcss.mgcss_track_7.domain.Cliente;
import com.mgcss.mgcss_track_7.service.ServicioCliente;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Clientes", description = "Gestión de clientes del sistema")
@RestController
@RequestMapping("/api/clientes")
public class ControladorCliente {
    private final ServicioCliente servicioCliente;

    public ControladorCliente(ServicioCliente servicioCliente) {
        this.servicioCliente = servicioCliente;
    }

    @Operation(summary = "Crear un cliente", description = "Registra un nuevo cliente con nombre, email y tipo (STANDARD o PREMIUM)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ClienteRespuestaDTO crearCliente(@RequestBody ClientePeticionDTO clientePeticionDTO) {
        Cliente.tipoCliente tipo = Cliente.tipoCliente.valueOf(clientePeticionDTO.getTipo());
        Cliente cliente = servicioCliente.crearCliente(
                clientePeticionDTO.getNombre(),
                clientePeticionDTO.getEmail(),
                tipo);
        return ClienteMapeo.toClienteRespuestaDTO(cliente);
    }

    @Operation(summary = "Obtener cliente por ID", description = "Devuelve los datos de un cliente a partir de su identificador")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado o null si no existe")
    })
    @GetMapping("/{id}")
    public ClienteRespuestaDTO obtenerClientePorId(
            @Parameter(description = "ID del cliente", example = "1") @PathVariable Long id) {
        Optional<Cliente> cliente = servicioCliente.findById(id);
        if (cliente.isPresent()) {
            return ClienteMapeo.toClienteRespuestaDTO(cliente.get());
        } else {
            return null;
        }
    }
}
