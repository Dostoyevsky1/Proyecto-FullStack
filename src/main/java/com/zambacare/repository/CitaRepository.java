package com.zambacare.repository;

import com.zambacare.entity.Cita;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByFechaOrderByHoraInicioAsc(LocalDate fecha);
    List<Cita> findByFechaBetweenOrderByFechaAscHoraInicioAsc(LocalDate inicio, LocalDate fin);
}
