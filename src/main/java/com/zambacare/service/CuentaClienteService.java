package com.zambacare.service;

import com.zambacare.entity.CuentaCliente;
import com.zambacare.repository.CuentaClienteRepository;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HexFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CuentaClienteService {
    private final CuentaClienteRepository cuentaRepository;

    public CuentaClienteService(CuentaClienteRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @Transactional
    public CuentaCliente crear(Long clienteId, String correo, String clave) {
        if (existePorCorreo(correo)) {
            throw new IllegalArgumentException("Usuario ya registrado");
        }
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        CuentaCliente cuenta = new CuentaCliente();
        cuenta.setClienteId(clienteId);
        cuenta.setCorreo(correo.toLowerCase());
        cuenta.setClaveSalt(HexFormat.of().formatHex(salt));
        cuenta.setClaveHash(hash(clave, salt));
        cuenta.setFechaCreacion(LocalDateTime.now());
        cuenta.setActiva(false);
        return cuentaRepository.save(cuenta);
    }

    public boolean existePorCorreo(String correo) {
        return correo != null && cuentaRepository.findByCorreoIgnoreCase(correo.trim()).isPresent();
    }

    @Transactional
    public void activar(Long clienteId) {
        cuentaRepository.findByClienteId(clienteId).ifPresent(cuenta -> {
            cuenta.setActiva(true);
            cuentaRepository.save(cuenta);
        });
    }

    private String hash(String clave, byte[] salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(salt);
            return HexFormat.of().formatHex(digest.digest(clave.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("No se pudo preparar la credencial", exception);
        }
    }
}
