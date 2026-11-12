package com.zambacare.controller;

import com.zambacare.entity.Cliente;
import com.zambacare.service.CitaService;
import com.zambacare.service.ClienteService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CitaController {
    private final CitaService citaService;
    private final ClienteService clienteService;

    public CitaController(CitaService citaService, ClienteService clienteService) {
        this.citaService = citaService;
        this.clienteService = clienteService;
    }

    @GetMapping("/citas")
    public String agenda(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                         Model model) {
        LocalDate seleccionada = fecha == null ? LocalDate.now() : fecha;
        var citas = citaService.listar(seleccionada);
        Map<Long, String> clientesPorId = clienteService.listar().stream()
            .collect(Collectors.toMap(Cliente::getId, Cliente::getNombreCompleto));
        model.addAttribute("fecha", seleccionada);
        model.addAttribute("citas", citas);
        model.addAttribute("clientesPorId", clientesPorId);
        model.addAttribute("total", citas.size());
        model.addAttribute("confirmadas", citas.stream().filter(cita -> "Confirmada".equals(cita.getEstado())).count());
        model.addAttribute("enAtencion", citas.stream().filter(cita -> "En atención".equals(cita.getEstado())).count());
        model.addAttribute("completadas", citas.stream().filter(cita -> "Completada".equals(cita.getEstado())).count());
        model.addAttribute("disponibles", citaService.disponibles(seleccionada));
        model.addAttribute("capacidad", CitaService.CAPACIDAD_DIARIA);
        model.addAttribute("ocupacion", citaService.ocupacion(seleccionada));
        return "citas";
    }

    @GetMapping("/citas/nueva")
    public String nueva(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                        Model model) {
        model.addAttribute("fecha", fecha == null ? LocalDate.now() : fecha);
        model.addAttribute("clientes", clienteService.listar());
        return "nueva-cita";
    }

    @PostMapping("/citas")
    public String registrar(@RequestParam Long clienteId,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horaInicio,
                            @RequestParam int duracionMinutos,
                            @RequestParam String servicio,
                            @RequestParam String especialista,
                            @RequestParam(required = false, defaultValue = "") String observaciones,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        try {
            citaService.registrar(clienteId, fecha, horaInicio, duracionMinutos, servicio, especialista, observaciones);
            redirectAttributes.addFlashAttribute("mensaje", "Cita registrada correctamente");
            return "redirect:/citas?fecha=" + fecha;
        } catch (IllegalArgumentException exception) {
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("fecha", fecha);
            model.addAttribute("clientes", clienteService.listar());
            return "nueva-cita";
        }
    }

    @PostMapping("/citas/{id}/estado")
    public String cambiarEstado(@PathVariable Long id,
                                @RequestParam String estado,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
                                RedirectAttributes redirectAttributes) {
        citaService.cambiarEstado(id, estado);
        redirectAttributes.addFlashAttribute("mensaje", "Estado actualizado");
        return "redirect:/citas?fecha=" + fecha;
    }
}
