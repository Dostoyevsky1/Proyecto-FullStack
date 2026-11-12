package com.zambacare.data;

import com.zambacare.entity.Cliente;
import com.zambacare.entity.Cita;
import com.zambacare.entity.Diagnostico;
import com.zambacare.entity.EvolucionCapilar;
import com.zambacare.entity.PlanTratamiento;
import com.zambacare.entity.SesionTratamiento;
import com.zambacare.repository.ClienteRepository;
import com.zambacare.repository.CitaRepository;
import com.zambacare.repository.DiagnosticoRepository;
import com.zambacare.repository.EvolucionCapilarRepository;
import com.zambacare.repository.PlanTratamientoRepository;
import com.zambacare.repository.SesionTratamientoRepository;
import com.zambacare.service.CuentaClienteService;
import com.zambacare.service.MembresiaService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements ApplicationRunner {
    private final ClienteRepository clienteRepository;
    private final CitaRepository citaRepository;
    private final DiagnosticoRepository diagnosticoRepository;
    private final PlanTratamientoRepository planRepository;
    private final SesionTratamientoRepository sesionRepository;
    private final EvolucionCapilarRepository evolucionRepository;
    private final CuentaClienteService cuentaService;
    private final MembresiaService membresiaService;

    public DataInitializer(ClienteRepository clienteRepository,
                           CitaRepository citaRepository,
                           DiagnosticoRepository diagnosticoRepository,
                           PlanTratamientoRepository planRepository,
                           SesionTratamientoRepository sesionRepository,
                           EvolucionCapilarRepository evolucionRepository,
                           CuentaClienteService cuentaService,
                           MembresiaService membresiaService) {
        this.clienteRepository = clienteRepository;
        this.citaRepository = citaRepository;
        this.diagnosticoRepository = diagnosticoRepository;
        this.planRepository = planRepository;
        this.sesionRepository = sesionRepository;
        this.evolucionRepository = evolucionRepository;
        this.cuentaService = cuentaService;
        this.membresiaService = membresiaService;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (clienteRepository.count() > 0) return;

        Cliente valeria = cliente("Valeria", "Mendoza", "900 000 001", "valeria.mendoza@zambacare.pe", "3B", "Rizado", "Alta", "Media", "Fino", "Hidratación en progreso");
        Cliente camila = cliente("Camila", "Torres", "900 000 002", "camila.torres@zambacare.pe", "2C", "Ondulado", "Media", "Alta", "Medio", "Control periódico");
        Cliente daniela = cliente("Daniela", "Ramos", "900 000 003", "daniela.ramos@zambacare.pe", "4A", "Afro", "Alta", "Alta", "Grueso", "Nutrición en progreso");
        Cliente lucia = cliente("Lucía", "Fernández", "900 000 004", "lucia.fernandez@zambacare.pe", "3A", "Rizado", "Media", "Media", "Medio", "Seguimiento");
        Cliente maria = cliente("María Fernanda", "López", "900 000 005", "maria.lopez@zambacare.pe", "2B", "Ondulado", "Baja", "Media", "Fino", "Diagnóstico inicial");
        Cliente ximena = cliente("Ximena", "Castro", "900 000 006", "ximena.castro@zambacare.pe", "1C", "Liso", "Media", "Alta", "Grueso", "Mantenimiento");
        Cliente alejandra = cliente("Alejandra", "Paredes", "900 000 007", "alejandra.paredes@zambacare.pe", "3C", "Rizado", "Alta", "Alta", "Fino", "Reconstrucción");
        Cliente paola = cliente("Paola", "Vega", "900 000 008", "paola.vega@zambacare.pe", "4B", "Afro", "Media", "Alta", "Grueso", "Seguimiento");
        clienteRepository.saveAll(List.of(valeria, camila, daniela, lucia, maria, ximena, alejandra, paola));

        LocalDate hoy = LocalDate.now();
        citaRepository.saveAll(List.of(
            cita(maria.getId(), hoy, "09:00", 60, "Diagnóstico capilar", "Andrea Salazar", "Completada"),
            cita(valeria.getId(), hoy, "10:30", 90, "Hidratación profunda", "Andrea Salazar", "En atención"),
            cita(camila.getId(), hoy, "10:00", 60, "Control de evolución", "Rosa Medina", "Confirmada"),
            cita(daniela.getId(), hoy, "11:30", 90, "Nutrición", "Rosa Medina", "Confirmada"),
            cita(lucia.getId(), hoy, "13:00", 60, "Definición", "Andrea Salazar", "Confirmada"),
            cita(ximena.getId(), hoy, "14:30", 45, "Corte de mantenimiento", "Andrea Salazar", "Confirmada"),
            cita(alejandra.getId(), hoy, "15:00", 90, "Reconstrucción", "Rosa Medina", "Confirmada"),
            cita(paola.getId(), hoy, "16:30", 60, "Seguimiento", "Andrea Salazar", "Por confirmar")
        ));

        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setClienteId(valeria.getId());
        diagnostico.setFecha(LocalDate.of(2026, 5, 15));
        diagnostico.setPatronCapilar("3B");
        diagnostico.setPorosidad("Alta");
        diagnostico.setDensidad("Media");
        diagnostico.setGrosor("Fino");
        diagnostico.setProblemasIdentificados("Deshidratación, Frizz, Puntas secas");
        diagnostico.setHabitos("Lavado tres veces por semana y uso ocasional de calor");
        diagnostico.setObservaciones("El cabello responde bien a tratamientos hidratantes.");
        diagnostico.setRecomendacion("Priorizar hidratación profunda, reducir el uso de calor y favorecer la retención de humedad.");
        diagnosticoRepository.save(diagnostico);

        PlanTratamiento plan = new PlanTratamiento();
        plan.setClienteId(valeria.getId());
        plan.setNombre("Hidratación y definición");
        plan.setObjetivo("Mejorar la retención de humedad, reducir el frizz y mantener una definición flexible.");
        plan.setDuracionEstimadaMinutos(90);
        plan.setTotalSesiones(6);
        plan.setSesionesCompletadas(3);
        plan.setFechaInicio(LocalDate.of(2026, 5, 15));
        plan.setProximaSesion(LocalDate.of(2026, 11, 18));
        plan.setEstado("En curso");
        planRepository.save(plan);

        sesionRepository.saveAll(List.of(
            sesion(valeria.getId(), plan.getId(), LocalDate.of(2026, 9, 18), "Hidratación profunda y definición", 90, "Se observó mayor suavidad y definición."),
            sesion(valeria.getId(), plan.getId(), LocalDate.of(2026, 8, 2), "Definición", 60, "Mejor respuesta al producto de fijación."),
            sesion(valeria.getId(), plan.getId(), LocalDate.of(2026, 6, 20), "Corte de mantenimiento", 45, "Se retiraron puntas secas."),
            sesion(valeria.getId(), plan.getId(), LocalDate.of(2026, 5, 15), "Diagnóstico inicial", 50, "Se definió el plan de cuidado.")
        ));

        evolucionRepository.saveAll(List.of(
            evolucion(valeria.getId(), LocalDate.of(2026, 1, 20), "/img/clientes/enero.png", "/img/clientes/marzo.png", 38, 42, 70, 45, "Inicio del seguimiento."),
            evolucion(valeria.getId(), LocalDate.of(2026, 3, 22), "/img/clientes/marzo.png", "/img/clientes/junio.png", 55, 60, 52, 58, "Mejoró la respuesta a la hidratación."),
            evolucion(valeria.getId(), LocalDate.of(2026, 6, 20), "/img/clientes/junio.png", "/img/clientes/septiembre.png", 70, 75, 38, 72, "Mayor definición y menor frizz."),
            evolucion(valeria.getId(), LocalDate.of(2026, 9, 18), "/img/clientes/septiembre.png", "/img/clientes/valeria.png", 82, 86, 24, 80, "La rutina mantiene resultados estables.")
        ));

        cuentaService.crear(valeria.getId(), valeria.getCorreo(), "Valeria2026");
        var membresia = membresiaService.elegirPlan(valeria.getId(), "INTEGRAL");
        membresiaService.confirmarPago(valeria.getId(), membresia.getId(), "Yape", "");
    }

    private Cliente cliente(String nombres, String apellidos, String telefono, String correo, String tipo, String textura, String porosidad, String densidad, String grosor, String estado) {
        Cliente cliente = new Cliente();
        cliente.setNombres(nombres);
        cliente.setApellidos(apellidos);
        cliente.setTelefono(telefono);
        cliente.setCorreo(correo);
        cliente.setTipoCabello(tipo);
        cliente.setTextura(textura);
        cliente.setPorosidad(porosidad);
        cliente.setDensidad(densidad);
        cliente.setGrosor(grosor);
        cliente.setEstadoCapilar(estado);
        cliente.setFechaNacimiento(LocalDate.of(1999, 4, 12));
        cliente.setFechaRegistro(LocalDate.of(2025, 3, 10));
        return cliente;
    }

    private SesionTratamiento sesion(Long clienteId, Long planId, LocalDate fecha, String servicio, int duracion, String resultado) {
        SesionTratamiento sesion = new SesionTratamiento();
        sesion.setClienteId(clienteId);
        sesion.setPlanTratamientoId(planId);
        sesion.setFecha(fecha);
        sesion.setServicio(servicio);
        sesion.setEspecialista("Andrea Salazar");
        sesion.setDuracionMinutos(duracion);
        sesion.setProductosUtilizados("Línea hidratante y definición suave");
        sesion.setResultado(resultado);
        sesion.setObservaciones(resultado);
        return sesion;
    }

    private EvolucionCapilar evolucion(Long clienteId, LocalDate fecha, String antes, String despues, int hidratacion, int definicion, int frizz, int elasticidad, String observaciones) {
        EvolucionCapilar evolucion = new EvolucionCapilar();
        evolucion.setClienteId(clienteId);
        evolucion.setFecha(fecha);
        evolucion.setImagenAntes(antes);
        evolucion.setImagenDespues(despues);
        evolucion.setHidratacion(hidratacion);
        evolucion.setDefinicion(definicion);
        evolucion.setFrizz(frizz);
        evolucion.setElasticidad(elasticidad);
        evolucion.setObservaciones(observaciones);
        return evolucion;
    }

    private Cita cita(Long clienteId, LocalDate fecha, String hora, int duracion, String servicio, String especialista, String estado) {
        Cita cita = new Cita();
        cita.setClienteId(clienteId);
        cita.setFecha(fecha);
        cita.setHoraInicio(LocalTime.parse(hora));
        cita.setHoraFin(cita.getHoraInicio().plusMinutes(duracion));
        cita.setServicio(servicio);
        cita.setEspecialista(especialista);
        cita.setEstado(estado);
        return cita;
    }
}
