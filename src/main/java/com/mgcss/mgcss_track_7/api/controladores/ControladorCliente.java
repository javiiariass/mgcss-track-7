package com.mgcss.mgcss_track_7.api.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgcss.mgcss_track_7.api.dto.ClientePeticionDTO;
import com.mgcss.mgcss_track_7.api.dto.ClienteRespuestaDTO;
import com.mgcss.mgcss_track_7.api.mapper.ClienteMapeo;
import com.mgcss.mgcss_track_7.domain.Cliente;
import com.mgcss.mgcss_track_7.service.ServicioCliente;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/clientes")
public class ControladorCliente {
    private final ServicioCliente servicioCliente;

    public ControladorCliente(ServicioCliente servicioCliente) {
        this.servicioCliente = servicioCliente;
    }

    @PostMapping
    public ClienteRespuestaDTO crearCliente(@RequestBody ClientePeticionDTO clientePeticionDTO) {
        Cliente.tipoCliente tipo = Cliente.tipoCliente.valueOf(clientePeticionDTO.getTipo());
        Cliente cliente = servicioCliente.crearCliente(
                clientePeticionDTO.getNombre(),
                clientePeticionDTO.getEmail(),
                tipo);
        return ClienteMapeo.toClienteRespuestaDTO(cliente);
    }

    @GetMapping("/{id}")
    public ClienteRespuestaDTO obtenerClientePorId(@PathVariable Long id) {
        Optional<Cliente> cliente = servicioCliente.findById(id);
        if (cliente.isPresent()) {
            return ClienteMapeo.toClienteRespuestaDTO(cliente.get());
        } else {
            return null;
        }
    }
}
