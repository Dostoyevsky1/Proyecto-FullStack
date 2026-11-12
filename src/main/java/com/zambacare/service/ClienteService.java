package com.zambacare.service;

import com.zambacare.entity.Cliente;
import com.zambacare.repository.ClienteRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    public Cliente buscar(Long id) {
        return clienteRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
    }

    public Cliente principal() {
        return clienteRepository.findByCorreoIgnoreCase("valeria.mendoza@zambacare.pe")
            .or(() -> clienteRepository.findAll().stream().findFirst())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay clientes registrados"));
    }

    @Transactional
    public Cliente guardar(Cliente cliente) {
        if (cliente.getFechaRegistro() == null) cliente.setFechaRegistro(LocalDate.now());
        if (cliente.getTextura() == null || cliente.getTextura().isBlank()) cliente.setTextura(texturaPara(cliente.getTipoCabello()));
        if (cliente.getPorosidad() == null || cliente.getPorosidad().isBlank()) cliente.setPorosidad("Media");
        if (cliente.getDensidad() == null || cliente.getDensidad().isBlank()) cliente.setDensidad("Media");
        if (cliente.getGrosor() == null || cliente.getGrosor().isBlank()) cliente.setGrosor("Medio");
        if (cliente.getEstadoCapilar() == null || cliente.getEstadoCapilar().isBlank()) cliente.setEstadoCapilar("En evaluación");
        return clienteRepository.save(cliente);
    }

    private String texturaPara(String tipo) {
        if (tipo == null || tipo.isBlank()) return "Sin definir";
        return switch (tipo.charAt(0)) {
            case '2' -> "Ondulado";
            case '3' -> "Rizado";
            case '4' -> "Afro";
            default -> "Liso";
        };
    }
}
