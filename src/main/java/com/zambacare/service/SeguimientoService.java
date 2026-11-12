package com.zambacare.service;

import com.zambacare.entity.Diagnostico;
import com.zambacare.entity.EvolucionCapilar;
import com.zambacare.entity.PlanTratamiento;
import com.zambacare.entity.SesionTratamiento;
import com.zambacare.repository.DiagnosticoRepository;
import com.zambacare.repository.EvolucionCapilarRepository;
import com.zambacare.repository.PlanTratamientoRepository;
import com.zambacare.repository.SesionTratamientoRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeguimientoService {
    private final DiagnosticoRepository diagnosticoRepository;
    private final PlanTratamientoRepository planRepository;
    private final SesionTratamientoRepository sesionRepository;
    private final EvolucionCapilarRepository evolucionRepository;

    public SeguimientoService(DiagnosticoRepository diagnosticoRepository,
                               PlanTratamientoRepository planRepository,
                               SesionTratamientoRepository sesionRepository,
                               EvolucionCapilarRepository evolucionRepository) {
        this.diagnosticoRepository = diagnosticoRepository;
        this.planRepository = planRepository;
        this.sesionRepository = sesionRepository;
        this.evolucionRepository = evolucionRepository;
    }

    public List<Diagnostico> diagnosticos(Long clienteId) { return diagnosticoRepository.findByClienteIdOrderByFechaDesc(clienteId); }
    public Optional<Diagnostico> ultimoDiagnostico(Long clienteId) { return diagnosticoRepository.findFirstByClienteIdOrderByFechaDesc(clienteId); }
    public List<PlanTratamiento> planes(Long clienteId) { return planRepository.findByClienteIdOrderByFechaInicioDesc(clienteId); }
    public Optional<PlanTratamiento> planActual(Long clienteId) { return planRepository.findFirstByClienteIdOrderByFechaInicioDesc(clienteId); }
    public List<SesionTratamiento> sesiones(Long clienteId) { return sesionRepository.findByClienteIdOrderByFechaDesc(clienteId); }
    public List<EvolucionCapilar> evoluciones(Long clienteId) { return evolucionRepository.findByClienteIdOrderByFechaAsc(clienteId); }

    @Transactional
    public Diagnostico guardarDiagnostico(Diagnostico diagnostico) {
        if (diagnostico.getFecha() == null) diagnostico.setFecha(LocalDate.now());
        if (diagnostico.getRecomendacion() == null || diagnostico.getRecomendacion().isBlank()) {
            diagnostico.setRecomendacion("Priorizar hidratación, reducir el uso de calor y mantener una rutina constante.");
        }
        return diagnosticoRepository.save(diagnostico);
    }

    @Transactional
    public PlanTratamiento guardarPlan(PlanTratamiento plan) {
        if (plan.getFechaInicio() == null) plan.setFechaInicio(LocalDate.now());
        if (plan.getSesionesCompletadas() == null) plan.setSesionesCompletadas(0);
        if (plan.getEstado() == null || plan.getEstado().isBlank()) plan.setEstado("En curso");
        return planRepository.save(plan);
    }

    @Transactional
    public SesionTratamiento guardarSesion(SesionTratamiento sesion) {
        if (sesion.getFecha() == null) sesion.setFecha(LocalDate.now());
        if (sesion.getEspecialista() == null || sesion.getEspecialista().isBlank()) sesion.setEspecialista("Andrea Salazar");
        SesionTratamiento guardada = sesionRepository.save(sesion);
        if (sesion.getPlanTratamientoId() != null) {
            planRepository.findById(sesion.getPlanTratamientoId()).ifPresent(plan -> {
                int completadas = plan.getSesionesCompletadas() == null ? 0 : plan.getSesionesCompletadas();
                plan.setSesionesCompletadas(Math.min(plan.getTotalSesiones(), completadas + 1));
                if (plan.getSesionesCompletadas().equals(plan.getTotalSesiones())) plan.setEstado("Completado");
                planRepository.save(plan);
            });
        }
        return guardada;
    }
}
