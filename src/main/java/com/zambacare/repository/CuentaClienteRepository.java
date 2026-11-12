package com.zambacare.repository;

import com.zambacare.entity.CuentaCliente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuentaClienteRepository extends JpaRepository<CuentaCliente, Long> {
    Optional<CuentaCliente> findByCorreoIgnoreCase(String correo);
    Optional<CuentaCliente> findByClienteId(Long clienteId);
}
