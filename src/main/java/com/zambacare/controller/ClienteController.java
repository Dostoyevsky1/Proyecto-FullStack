package com.zambacare.controller;

import com.zambacare.entity.Cliente;
import com.zambacare.service.ClienteService;
import com.zambacare.service.SeguimientoService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClienteController {
    private final ClienteService clienteService;
    private final SeguimientoService seguimientoService;

    public ClienteController(ClienteService clienteService, SeguimientoService seguimientoService) {
        this.clienteService = clienteService;
        this.seguimientoService = seguimientoService;
    }

    @GetMapping("/clientes")
    public String clientes(Model model) {
        model.addAttribute("clientes", clienteService.listar());
        return "clientes";
    }

    @GetMapping("/clientes/nuevo")
    public String nuevoCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "nuevo-cliente";
    }

    @PostMapping("/clientes")
    public String registrarCliente(@Valid @ModelAttribute("cliente") Cliente cliente,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) return "nuevo-cliente";
        Cliente guardado = clienteService.guardar(cliente);
        redirectAttributes.addFlashAttribute("mensaje", "Cliente registrado correctamente");
        return "redirect:/clientes/perfil/" + guardado.getId();
    }

    @GetMapping("/clientes/perfil")
    public String perfilPrincipal() {
        return "redirect:/clientes/perfil/" + clienteService.principal().getId();
    }

    @GetMapping("/clientes/perfil/{id}")
    public String perfil(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.buscar(id);
        model.addAttribute("cliente", cliente);
        model.addAttribute("edad", edad(cliente));
        model.addAttribute("diagnosticos", seguimientoService.diagnosticos(id));
        model.addAttribute("diagnostico", seguimientoService.ultimoDiagnostico(id).orElse(null));
        model.addAttribute("plan", seguimientoService.planActual(id).orElse(null));
        model.addAttribute("sesiones", seguimientoService.sesiones(id));
        model.addAttribute("evoluciones", seguimientoService.evoluciones(id));
        return "perfil-cliente";
    }

    private int edad(Cliente cliente) {
        return cliente.getFechaNacimiento() == null ? 0 : Period.between(cliente.getFechaNacimiento(), LocalDate.now()).getYears();
    }
}
