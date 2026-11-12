package com.zambacare.repository;

import com.zambacare.entity.SesionTratamiento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SesionTratamientoRepository extends JpaRepository<SesionTratamiento, Long> {
    List<SesionTratamiento> findByClienteIdOrderByFechaDesc(Long clienteId);
    long countByClienteId(Long clienteId);
}
