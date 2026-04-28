package com.mgcss.mgcss_track_7.service;

import java.util.List;

import com.mgcss.mgcss_track_7.infraestrucure.persistence.ClienteRepositorio;
import com.mgcss.mgcss_track_7.domain.Cliente;
import java.util.Optional;

public class ServicioCliente {
  
    public final ClienteRepositorio clienteRepositorio;
    List<Cliente> clientes;
    public ServicioCliente(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }
    public Optional<Cliente> findById(Long id) {
        return clienteRepositorio.findById(id);
    }
    public Cliente save(Cliente cliente) {
        return clienteRepositorio.save(cliente);
    }
    public Cliente crearCliente(String nombre, String email, Cliente.tipoCliente tipoCliente) {
        Cliente cliente = new Cliente(nombre, email, tipoCliente);
        return clienteRepositorio.save(cliente);
    }

    


}