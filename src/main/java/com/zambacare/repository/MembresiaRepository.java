package com.zambacare.repository;

import com.zambacare.entity.Membresia;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembresiaRepository extends JpaRepository<Membresia, Long> {
    Optional<Membresia> findFirstByClienteIdOrderByFechaInicioDesc(Long clienteId);
}
