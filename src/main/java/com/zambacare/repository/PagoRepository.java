package com.zambacare.repository;

import com.zambacare.entity.Pago;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByClienteIdOrderByFechaDesc(Long clienteId);
}
