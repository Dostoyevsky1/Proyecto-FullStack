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
                              @RequestParam String clave,
                              Model model) {
        if (clienteService.existePorCorreo(correo) || cuentaService.existePorCorreo(correo)) {
            model.addAttribute("error", "Usuario ya registrado");
            model.addAttribute("nombres", nombres);
            model.addAttribute("apellidos", apellidos);
            model.addAttribute("correo", correo);
            model.addAttribute("telefono", telefono);
            return "registro-cliente";
        }
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
        agregarCampana(model);
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
        agregarCampana(model);
        return "pago-membresia";
    }

    @PostMapping("/pago")
    public String confirmarPago(@RequestParam Long clienteId,
                                @RequestParam Long membresiaId,
                                @RequestParam String metodo,
                                @RequestParam(required = false, defaultValue = "") String cupon,
                                Model model) {
        if (!membresiaService.cuponValido(cupon)) {
            model.addAttribute("cliente", clienteService.buscar(clienteId));
            model.addAttribute("membresia", membresiaService.actual(clienteId).orElseThrow());
            model.addAttribute("error", "Cupón no válido");
            agregarCampana(model);
            return "pago-membresia";
        }
        var pago = membresiaService.confirmarPago(clienteId, membresiaId, metodo, cupon);
        return "redirect:/pago/confirmado?clienteId=" + clienteId + "&pagoId=" + pago.getId();
    }

    @GetMapping("/pago/confirmado")
    public String pagoConfirmado(@RequestParam Long clienteId, @RequestParam Long pagoId, Model model) {
        model.addAttribute("cliente", clienteService.buscar(clienteId));
        model.addAttribute("pago", membresiaService.pagos(clienteId).stream().filter(item -> item.getId().equals(pagoId)).findFirst().orElseThrow());
        model.addAttribute("membresia", membresiaService.actual(clienteId).orElseThrow());
        return "pago-confirmado";
    }

    private void agregarCampana(Model model) {
        model.addAttribute("cupon", MembresiaService.CUPON_FAMILIAR);
        model.addAttribute("descuento", MembresiaService.DESCUENTO_FAMILIAR);
        model.addAttribute("meses", MembresiaService.MESES_DESCUENTO);
    }
}
