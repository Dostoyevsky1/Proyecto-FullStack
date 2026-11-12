package com.zambacare.repository;

import com.zambacare.entity.Diagnostico;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Long> {
    List<Diagnostico> findByClienteIdOrderByFechaDesc(Long clienteId);
    Optional<Diagnostico> findFirstByClienteIdOrderByFechaDesc(Long clienteId);
}
