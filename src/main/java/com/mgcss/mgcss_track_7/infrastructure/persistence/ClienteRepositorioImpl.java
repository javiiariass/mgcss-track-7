package com.mgcss.mgcss_track_7.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mgcss.mgcss_track_7.domain.Cliente;

@Repository
public class ClienteRepositorioImpl implements ClienteRepositorio {
    private final JpaClienteRepositorio jpaCliente;

    public ClienteRepositorioImpl(JpaClienteRepositorio jpaCliente) {
        this.jpaCliente = jpaCliente;
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return jpaCliente.findById(id).map(this::toDomain);
    }

    @Override
    public Cliente save(Cliente cliente) {
        ClienteEntidad entidad = toEntity(cliente);
        ClienteEntidad saved = jpaCliente.save(entidad);
        return toDomain(saved);
    }

    private ClienteEntidad toEntity(Cliente cliente) {
        return new ClienteEntidad(
            cliente.getId(),
            cliente.getNombre(),
            cliente.getEmail(),
            cliente.getTipo()
        );
    }

    private Cliente toDomain(ClienteEntidad entidad) {
        Cliente cliente = new Cliente();
        cliente.setId(entidad.getId());
        cliente.setNombre(entidad.getNombre());
        cliente.setEmail(entidad.getEmail());
        cliente.setTipo(entidad.getTipoCliente());
        return cliente;
    }
}
