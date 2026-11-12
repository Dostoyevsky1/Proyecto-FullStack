package com.zambacare.controller;

import com.zambacare.repository.DiagnosticoRepository;
import com.zambacare.service.ClienteService;
import com.zambacare.service.CitaService;
import java.time.LocalDate;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    private final ClienteService clienteService;
    private final DiagnosticoRepository diagnosticoRepository;
    private final CitaService citaService;

    public PageController(ClienteService clienteService, DiagnosticoRepository diagnosticoRepository, CitaService citaService) {
        this.clienteService = clienteService;
        this.diagnosticoRepository = diagnosticoRepository;
        this.citaService = citaService;
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
        var hoy = LocalDate.now();
        var citas = citaService.listar(hoy);
        model.addAttribute("citasHoy", citas);
        model.addAttribute("totalCitas", citas.size());
        model.addAttribute("cuposDisponibles", citaService.disponibles(hoy));
        model.addAttribute("ocupacionAgenda", citaService.ocupacion(hoy));
        model.addAttribute("clientesPorId", clientes.stream().collect(Collectors.toMap(cliente -> cliente.getId(), cliente -> cliente.getNombreCompleto())));
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
