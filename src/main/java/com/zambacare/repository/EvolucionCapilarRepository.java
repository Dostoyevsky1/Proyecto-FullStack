package com.zambacare.repository;

import com.zambacare.entity.EvolucionCapilar;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvolucionCapilarRepository extends JpaRepository<EvolucionCapilar, Long> {
    List<EvolucionCapilar> findByClienteIdOrderByFechaAsc(Long clienteId);
}
