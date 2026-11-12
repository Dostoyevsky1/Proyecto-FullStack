package com.zambacare.service;

import com.zambacare.entity.Cita;
import com.zambacare.repository.CitaRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitaService {
    public static final int CAPACIDAD_DIARIA = 12;
    private final CitaRepository citaRepository;

    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public List<Cita> listar(LocalDate fecha) {
        return citaRepository.findByFechaOrderByHoraInicioAsc(fecha);
    }

    public List<Cita> listarSemana(LocalDate inicio, LocalDate fin) {
        return citaRepository.findByFechaBetweenOrderByFechaAscHoraInicioAsc(inicio, fin);
    }

    public long activas(LocalDate fecha) {
        return listar(fecha).stream().filter(cita -> !"Cancelada".equalsIgnoreCase(cita.getEstado())).count();
    }

    public int disponibles(LocalDate fecha) {
        return Math.max(0, CAPACIDAD_DIARIA - (int) activas(fecha));
    }

    public int ocupacion(LocalDate fecha) {
        return (int) Math.round(activas(fecha) * 100.0 / CAPACIDAD_DIARIA);
    }

    @Transactional
    public Cita registrar(Long clienteId,
                          LocalDate fecha,
                          LocalTime horaInicio,
                          int duracionMinutos,
                          String servicio,
                          String especialista,
                          String observaciones) {
        if (activas(fecha) >= CAPACIDAD_DIARIA) {
            throw new IllegalArgumentException("La agenda del día está completa");
        }
        LocalTime horaFin = horaInicio.plusMinutes(duracionMinutos);
        boolean cruce = listar(fecha).stream()
            .filter(cita -> !"Cancelada".equalsIgnoreCase(cita.getEstado()))
            .filter(cita -> especialista.equalsIgnoreCase(cita.getEspecialista()))
            .anyMatch(cita -> horaInicio.isBefore(cita.getHoraFin()) && horaFin.isAfter(cita.getHoraInicio()));
        if (cruce) {
            throw new IllegalArgumentException("La especialista ya tiene una cita en ese horario");
        }
        Cita cita = new Cita();
        cita.setClienteId(clienteId);
        cita.setFecha(fecha);
        cita.setHoraInicio(horaInicio);
        cita.setHoraFin(horaFin);
        cita.setServicio(servicio);
        cita.setEspecialista(especialista);
        cita.setEstado("Confirmada");
        cita.setObservaciones(observaciones);
        return citaRepository.save(cita);
    }

    @Transactional
    public void cambiarEstado(Long id, String estado) {
        if (!List.of("Confirmada", "En atención", "Completada", "Cancelada").contains(estado)) {
            throw new IllegalArgumentException("Estado de cita no válido");
        }
        Cita cita = citaRepository.findById(id).orElseThrow();
        cita.setEstado(estado);
        citaRepository.save(cita);
    }
}
