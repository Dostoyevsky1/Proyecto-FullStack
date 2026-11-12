package com.zambacare.controller;

import com.zambacare.bean.CitaCalendarioBean;
import com.zambacare.entity.Cliente;
import com.zambacare.service.CitaService;
import com.zambacare.service.ClienteService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.IntStream;
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
        LocalDate inicioSemana = seleccionada.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate finSemana = inicioSemana.plusDays(6);
        var citas = citaService.listarSemana(inicioSemana, finSemana);
        Map<Long, String> clientesPorId = clienteService.listar().stream()
            .collect(Collectors.toMap(Cliente::getId, Cliente::getNombreCompleto));
        List<LocalDate> dias = IntStream.range(0, 7).mapToObj(inicioSemana::plusDays).toList();
        List<CitaCalendarioBean> eventos = citas.stream().map(cita -> {
            int dia = (int) (cita.getFecha().toEpochDay() - inicioSemana.toEpochDay());
            int posicion = (cita.getHoraInicio().getHour() - 8) * 60 + cita.getHoraInicio().getMinute();
            int duracion = (int) Duration.between(cita.getHoraInicio(), cita.getHoraFin()).toMinutes();
            boolean tieneCruce = citas.stream()
                .filter(otra -> !otra.getId().equals(cita.getId()))
                .filter(otra -> otra.getFecha().equals(cita.getFecha()))
                .anyMatch(otra -> cita.getHoraInicio().isBefore(otra.getHoraFin()) && cita.getHoraFin().isAfter(otra.getHoraInicio()));
            int izquierda = tieneCruce && "Rosa Medina".equals(cita.getEspecialista()) ? 50 : 0;
            int ancho = tieneCruce ? 50 : 100;
            return new CitaCalendarioBean(cita, clientesPorId.getOrDefault(cita.getClienteId(), "Cliente"), dia, posicion, Math.max(44, duracion), izquierda, ancho);
        }).toList();
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter fechaCorta = DateTimeFormatter.ofPattern("d MMM", new Locale("es", "PE"));
        DateTimeFormatter fechaCompleta = DateTimeFormatter.ofPattern("d MMM yyyy", new Locale("es", "PE"));
        model.addAttribute("fecha", seleccionada);
        model.addAttribute("hoy", hoy);
        model.addAttribute("inicioSemana", inicioSemana);
        model.addAttribute("finSemana", finSemana);
        model.addAttribute("semanaAnterior", inicioSemana.minusWeeks(1));
        model.addAttribute("semanaSiguiente", inicioSemana.plusWeeks(1));
        model.addAttribute("dias", dias);
        model.addAttribute("nombresDias", List.of("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"));
        model.addAttribute("semanaLabel", inicioSemana.format(fechaCorta) + " – " + finSemana.format(fechaCompleta));
        model.addAttribute("eventos", eventos);
        model.addAttribute("totalSemana", citas.size());
        model.addAttribute("totalHoy", citaService.listar(hoy).size());
        model.addAttribute("confirmadas", citas.stream().filter(cita -> "Confirmada".equals(cita.getEstado())).count());
        model.addAttribute("enAtencion", citas.stream().filter(cita -> "En atención".equals(cita.getEstado())).count());
        model.addAttribute("disponibles", citaService.disponibles(hoy));
        model.addAttribute("capacidad", CitaService.CAPACIDAD_DIARIA);
        model.addAttribute("ocupacion", citaService.ocupacion(hoy));
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
