package com.zambacare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/clientes")
    public String clientes() {
        return "clientes";
    }

    @GetMapping("/clientes/nuevo")
    public String nuevoCliente() {
        return "nuevo-cliente";
    }

    @GetMapping("/clientes/perfil")
    public String perfilCliente() {
        return "perfil-cliente";
    }

    @GetMapping("/diagnosticos/nuevo")
    public String nuevoDiagnostico() {
        return "nuevo-diagnostico";
    }

    @GetMapping("/diagnosticos/resultado")
    public String resultadoDiagnostico() {
        return "resultado-diagnostico";
    }

    @GetMapping("/historial")
    public String historialServicios() {
        return "historial-servicios";
    }

    @GetMapping("/tratamientos/nuevo")
    public String nuevoTratamiento() {
        return "nuevo-tratamiento";
    }

    @GetMapping("/seguimiento/evolucion")
    public String evolucionCapilar() {
        return "evolucion-capilar";
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
