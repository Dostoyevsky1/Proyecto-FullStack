package com.zambacare.service;

import com.zambacare.bean.PlanMembresiaBean;
import com.zambacare.entity.Membresia;
import com.zambacare.entity.Pago;
import com.zambacare.repository.MembresiaRepository;
import com.zambacare.repository.PagoRepository;
import com.zambacare.repository.PlanMembresiaRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MembresiaService {
    private final PlanMembresiaRepository catalogo;
    private final MembresiaRepository membresiaRepository;
    private final PagoRepository pagoRepository;
    private final CuentaClienteService cuentaService;

    public MembresiaService(PlanMembresiaRepository catalogo,
                             MembresiaRepository membresiaRepository,
                             PagoRepository pagoRepository,
                             CuentaClienteService cuentaService) {
        this.catalogo = catalogo;
        this.membresiaRepository = membresiaRepository;
        this.pagoRepository = pagoRepository;
        this.cuentaService = cuentaService;
    }

    public List<PlanMembresiaBean> planes() { return catalogo.findAll(); }
    public Optional<Membresia> actual(Long clienteId) { return membresiaRepository.findFirstByClienteIdOrderByFechaInicioDesc(clienteId); }
    public List<Pago> pagos(Long clienteId) { return pagoRepository.findByClienteIdOrderByFechaDesc(clienteId); }

    @Transactional
    public Membresia elegirPlan(Long clienteId, String codigo) {
        PlanMembresiaBean plan = catalogo.findByCodigo(codigo).orElseThrow();
        Membresia membresia = new Membresia();
        membresia.setClienteId(clienteId);
        membresia.setPlanCodigo(plan.getCodigo());
        membresia.setPlanNombre(plan.getNombre());
        membresia.setPrecioMensual(plan.getPrecio());
        membresia.setEstado("Pendiente");
        return membresiaRepository.save(membresia);
    }

    @Transactional
    public Pago confirmarPago(Long clienteId, Long membresiaId, String metodo) {
        Membresia membresia = membresiaRepository.findById(membresiaId).orElseThrow();
        LocalDate inicio = LocalDate.now();
        membresia.setFechaInicio(inicio);
        membresia.setFechaVencimiento(inicio.plusMonths(1));
        membresia.setEstado("Activa");
        membresiaRepository.save(membresia);

        Pago pago = new Pago();
        pago.setClienteId(clienteId);
        pago.setMembresiaId(membresiaId);
        pago.setFecha(LocalDateTime.now());
        pago.setMonto(membresia.getPrecioMensual());
        pago.setMetodo(metodo);
        pago.setCodigoOperacion("ZC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        pago.setEstado("Pagado");
        cuentaService.activar(clienteId);
        return pagoRepository.save(pago);
    }
}
