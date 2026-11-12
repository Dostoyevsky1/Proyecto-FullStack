package com.zambacare.repository;

import com.zambacare.entity.PlanTratamiento;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanTratamientoRepository extends JpaRepository<PlanTratamiento, Long> {
    List<PlanTratamiento> findByClienteIdOrderByFechaInicioDesc(Long clienteId);
    Optional<PlanTratamiento> findFirstByClienteIdOrderByFechaInicioDesc(Long clienteId);
}
