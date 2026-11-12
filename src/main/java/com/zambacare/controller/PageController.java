package com.zambacare.controller;

import com.zambacare.repository.DiagnosticoRepository;
import com.zambacare.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    private final ClienteService clienteService;
    private final DiagnosticoRepository diagnosticoRepository;

    public PageController(ClienteService clienteService, DiagnosticoRepository diagnosticoRepository) {
        this.clienteService = clienteService;
        this.diagnosticoRepository = diagnosticoRepository;
    }

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        var clientes = clienteService.listar();
        model.addAttribute("clientes", clientes);
        model.addAttribute("totalClientes", clientes.size());
        model.addAttribute("totalDiagnosticos", diagnosticoRepository.count());
        return "dashboard";
    }

    @GetMapping("/seguimiento/rutina")
    public String rutinaPersonalizada() {
        return "rutina-personalizada";
    }

    @GetMapping("/productos")
    public String productosRecomendados() {
        return "productos-recomendados";
    }
}
