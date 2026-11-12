package com.zambacare.controller;

import com.zambacare.service.ClienteService;
import com.zambacare.service.MembresiaService;
import com.zambacare.service.SeguimientoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PortalClienteController {
    private final ClienteService clienteService;
    private final SeguimientoService seguimientoService;
    private final MembresiaService membresiaService;

    public PortalClienteController(ClienteService clienteService, SeguimientoService seguimientoService, MembresiaService membresiaService) {
        this.clienteService = clienteService;
        this.seguimientoService = seguimientoService;
        this.membresiaService = membresiaService;
    }

    @GetMapping("/portal/cliente")
    public String inicio(@RequestParam(required = false) Long clienteId, Model model) {
        cargar(clienteId, model);
        return "portal-cliente";
    }

    @GetMapping("/portal/cliente/plan")
    public String plan(@RequestParam(required = false) Long clienteId, Model model) {
        cargar(clienteId, model);
        return "portal-plan";
    }

    @GetMapping("/portal/cliente/evolucion")
    public String evolucion(@RequestParam(required = false) Long clienteId, Model model) {
        cargar(clienteId, model);
        return "portal-evolucion";
    }

    @GetMapping("/portal/cliente/historial")
    public String historial(@RequestParam(required = false) Long clienteId, Model model) {
        cargar(clienteId, model);
        return "portal-historial";
    }

    @GetMapping("/portal/cliente/membresia")
    public String membresia(@RequestParam(required = false) Long clienteId, Model model) {
        cargar(clienteId, model);
        return "portal-membresia";
    }

    @GetMapping("/portal/cliente/rutina")
    public String rutina(@RequestParam(required = false) Long clienteId, Model model) {
        cargar(clienteId, model);
        return "portal-rutina";
    }

    @GetMapping("/portal/cliente/productos")
    public String productos(@RequestParam(required = false) Long clienteId, Model model) {
        cargar(clienteId, model);
        return "portal-productos";
    }

    private void cargar(Long clienteId, Model model) {
        var cliente = clienteId == null ? clienteService.principal() : clienteService.buscar(clienteId);
        model.addAttribute("cliente", cliente);
        model.addAttribute("plan", seguimientoService.planActual(cliente.getId()).orElse(null));
        model.addAttribute("sesiones", seguimientoService.sesiones(cliente.getId()));
        var evoluciones = seguimientoService.evoluciones(cliente.getId());
        model.addAttribute("evoluciones", evoluciones);
        model.addAttribute("ultimaEvolucion", evoluciones.isEmpty() ? null : evoluciones.get(evoluciones.size() - 1));
        model.addAttribute("membresia", membresiaService.actual(cliente.getId()).orElse(null));
        model.addAttribute("pagos", membresiaService.pagos(cliente.getId()));
    }
}
