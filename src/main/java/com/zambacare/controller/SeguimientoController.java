package com.zambacare.controller;

import com.zambacare.entity.Diagnostico;
import com.zambacare.entity.PlanTratamiento;
import com.zambacare.entity.SesionTratamiento;
import com.zambacare.service.ClienteService;
import com.zambacare.service.SeguimientoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SeguimientoController {
    private final ClienteService clienteService;
    private final SeguimientoService seguimientoService;

    public SeguimientoController(ClienteService clienteService, SeguimientoService seguimientoService) {
        this.clienteService = clienteService;
        this.seguimientoService = seguimientoService;
    }

    @GetMapping("/diagnosticos/nuevo")
    public String nuevoDiagnostico(@RequestParam(required = false) Long clienteId, Model model) {
        var cliente = clienteId == null ? clienteService.principal() : clienteService.buscar(clienteId);
        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setClienteId(cliente.getId());
        diagnostico.setPatronCapilar(cliente.getTipoCabello());
        diagnostico.setPorosidad(cliente.getPorosidad());
        diagnostico.setDensidad(cliente.getDensidad());
        diagnostico.setGrosor(cliente.getGrosor());
        model.addAttribute("cliente", cliente);
        model.addAttribute("diagnostico", diagnostico);
        return "nuevo-diagnostico";
    }

    @PostMapping("/diagnosticos")
    public String guardarDiagnostico(@ModelAttribute Diagnostico diagnostico) {
        seguimientoService.guardarDiagnostico(diagnostico);
        return "redirect:/diagnosticos/resultado?clienteId=" + diagnostico.getClienteId();
    }

    @GetMapping("/diagnosticos/resultado")
    public String resultado(@RequestParam(required = false) Long clienteId, Model model) {
        var cliente = clienteId == null ? clienteService.principal() : clienteService.buscar(clienteId);
        model.addAttribute("cliente", cliente);
        model.addAttribute("diagnostico", seguimientoService.ultimoDiagnostico(cliente.getId()).orElse(null));
        return "resultado-diagnostico";
    }

    @GetMapping("/historial")
    public String historial(@RequestParam(required = false) Long clienteId, Model model) {
        var cliente = clienteId == null ? clienteService.principal() : clienteService.buscar(clienteId);
        model.addAttribute("cliente", cliente);
        model.addAttribute("sesiones", seguimientoService.sesiones(cliente.getId()));
        return "historial-servicios";
    }

    @GetMapping("/tratamientos/nuevo")
    public String nuevoTratamiento(@RequestParam(required = false) Long clienteId, Model model) {
        var cliente = clienteId == null ? clienteService.principal() : clienteService.buscar(clienteId);
        SesionTratamiento sesion = new SesionTratamiento();
        sesion.setClienteId(cliente.getId());
        seguimientoService.planActual(cliente.getId()).ifPresent(plan -> sesion.setPlanTratamientoId(plan.getId()));
        model.addAttribute("cliente", cliente);
        model.addAttribute("sesion", sesion);
        model.addAttribute("planes", seguimientoService.planes(cliente.getId()));
        return "nuevo-tratamiento";
    }

    @PostMapping("/tratamientos")
    public String guardarTratamiento(@ModelAttribute SesionTratamiento sesion, RedirectAttributes redirectAttributes) {
        seguimientoService.guardarSesion(sesion);
        redirectAttributes.addFlashAttribute("mensaje", "Tratamiento registrado correctamente");
        return "redirect:/historial?clienteId=" + sesion.getClienteId();
    }

    @GetMapping("/seguimiento/evolucion")
    public String evolucion(@RequestParam(required = false) Long clienteId, Model model) {
        var cliente = clienteId == null ? clienteService.principal() : clienteService.buscar(clienteId);
        model.addAttribute("cliente", cliente);
        model.addAttribute("evoluciones", seguimientoService.evoluciones(cliente.getId()));
        model.addAttribute("plan", seguimientoService.planActual(cliente.getId()).orElse(null));
        return "evolucion-capilar";
    }

    @GetMapping("/planes/nuevo")
    public String nuevoPlan(@RequestParam(required = false) Long clienteId, Model model) {
        var cliente = clienteId == null ? clienteService.principal() : clienteService.buscar(clienteId);
        PlanTratamiento plan = new PlanTratamiento();
        plan.setClienteId(cliente.getId());
        plan.setTotalSesiones(6);
        plan.setDuracionEstimadaMinutos(90);
        model.addAttribute("cliente", cliente);
        model.addAttribute("plan", plan);
        return "nuevo-plan-tratamiento";
    }

    @PostMapping("/planes")
    public String guardarPlan(@ModelAttribute PlanTratamiento plan, RedirectAttributes redirectAttributes) {
        seguimientoService.guardarPlan(plan);
        redirectAttributes.addFlashAttribute("mensaje", "Plan de tratamiento creado");
        return "redirect:/clientes/perfil/" + plan.getClienteId();
    }
}
