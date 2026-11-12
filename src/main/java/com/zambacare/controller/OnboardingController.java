package com.zambacare.controller;

import com.zambacare.entity.Cliente;
import com.zambacare.service.ClienteService;
import com.zambacare.service.CuentaClienteService;
import com.zambacare.service.MembresiaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OnboardingController {
    private final ClienteService clienteService;
    private final CuentaClienteService cuentaService;
    private final MembresiaService membresiaService;

    public OnboardingController(ClienteService clienteService, CuentaClienteService cuentaService, MembresiaService membresiaService) {
        this.clienteService = clienteService;
        this.cuentaService = cuentaService;
        this.membresiaService = membresiaService;
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro-cliente";
    }

    @PostMapping("/registro")
    public String crearCuenta(@RequestParam String nombres,
                              @RequestParam String apellidos,
                              @RequestParam String correo,
                              @RequestParam String telefono,
                              @RequestParam String clave) {
        Cliente cliente = new Cliente();
        cliente.setNombres(nombres);
        cliente.setApellidos(apellidos);
        cliente.setCorreo(correo);
        cliente.setTelefono(telefono);
        cliente.setTipoCabello("3B");
        Cliente guardado = clienteService.guardar(cliente);
        cuentaService.crear(guardado.getId(), correo, clave);
        return "redirect:/membresia/planes?clienteId=" + guardado.getId();
    }

    @GetMapping("/membresia/planes")
    public String planes(@RequestParam Long clienteId, Model model) {
        model.addAttribute("cliente", clienteService.buscar(clienteId));
        model.addAttribute("planes", membresiaService.planes());
        return "planes-membresia";
    }

    @PostMapping("/membresia/seleccionar")
    public String seleccionar(@RequestParam Long clienteId, @RequestParam String codigo) {
        var membresia = membresiaService.elegirPlan(clienteId, codigo);
        return "redirect:/pago?clienteId=" + clienteId + "&membresiaId=" + membresia.getId();
    }

    @GetMapping("/pago")
    public String pago(@RequestParam Long clienteId, @RequestParam Long membresiaId, Model model) {
        model.addAttribute("cliente", clienteService.buscar(clienteId));
        model.addAttribute("membresia", membresiaService.actual(clienteId).orElseThrow());
        return "pago-membresia";
    }

    @PostMapping("/pago")
    public String confirmarPago(@RequestParam Long clienteId,
                                @RequestParam Long membresiaId,
                                @RequestParam String metodo) {
        var pago = membresiaService.confirmarPago(clienteId, membresiaId, metodo);
        return "redirect:/pago/confirmado?clienteId=" + clienteId + "&pagoId=" + pago.getId();
    }

    @GetMapping("/pago/confirmado")
    public String pagoConfirmado(@RequestParam Long clienteId, @RequestParam Long pagoId, Model model) {
        model.addAttribute("cliente", clienteService.buscar(clienteId));
        model.addAttribute("pago", membresiaService.pagos(clienteId).stream().filter(item -> item.getId().equals(pagoId)).findFirst().orElseThrow());
        return "pago-confirmado";
    }
}
