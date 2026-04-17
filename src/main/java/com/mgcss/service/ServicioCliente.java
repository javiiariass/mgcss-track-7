package com.mgcss.service;

import com.mgcss.domain.Cliente;
import java.util.List;
import com.mgcss.infraestrucure.ClienteInterfaz;

public class ServicioCliente  implements ClienteInterfaz {
   /* private List<Cliente> clientes = new List<>();
    private long proximoId = 0;
    
    public List<Cliente> listarClientes() {
        return clientes;
    }

    public Cliente save(Cliente nuevoCliente) {
        nuevoCliente.setId(proximoId++);
        clientes.add(nuevoCliente);
        return nuevoCliente;
    }

    public Cliente consultarCliente(Long id) {
        return clientes.stream()
                .filter(cliente -> cliente.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public boolean modificarCliente(Long id, String nombre, String nuevoEmail, CLiente.tipoCliente tipo) {
        boolean clienteCambiado = false;
        Cliente c = consultarCliente(id);
        if(c!=null || c.isPresent()){
            c.setNombre(nombre);
            c.setEmail(nuevoEmail);
            c.setTipo(tipo);
            clienteCambiado = true;
        }
        return clienteCambiado;
    }
 */

}