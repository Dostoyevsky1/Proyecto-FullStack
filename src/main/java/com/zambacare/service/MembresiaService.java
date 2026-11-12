package com.zambacare.service;

import com.zambacare.bean.PlanMembresiaBean;
import com.zambacare.entity.Membresia;
import com.zambacare.entity.Pago;
import com.zambacare.repository.MembresiaRepository;
import com.zambacare.repository.PagoRepository;
import com.zambacare.repository.PlanMembresiaRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MembresiaService {
    public static final String CUPON_FAMILIAR = "FAMILIAZAMBA";
    public static final int DESCUENTO_FAMILIAR = 20;
    public static final int MESES_DESCUENTO = 3;
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

    public boolean cuponValido(String cupon) {
        return cupon == null || cupon.isBlank() || CUPON_FAMILIAR.equalsIgnoreCase(cupon.trim());
    }

    @Transactional
    public Pago confirmarPago(Long clienteId, Long membresiaId, String metodo, String cupon) {
        Membresia membresia = membresiaRepository.findById(membresiaId).orElseThrow();
        if (!cuponValido(cupon)) {
            throw new IllegalArgumentException("Cupón no válido");
        }
        LocalDate inicio = LocalDate.now();
        membresia.setFechaInicio(inicio);
        membresia.setFechaVencimiento(inicio.plusMonths(1));
        membresia.setEstado("Activa");
        BigDecimal monto = membresia.getPrecioMensual();
        if (cupon != null && !cupon.isBlank()) {
            BigDecimal factor = BigDecimal.valueOf(100 - DESCUENTO_FAMILIAR).movePointLeft(2);
            monto = monto.multiply(factor).setScale(2, RoundingMode.HALF_UP);
            membresia.setCuponAplicado(CUPON_FAMILIAR);
            membresia.setDescuentoPorcentaje(DESCUENTO_FAMILIAR);
            membresia.setMesesDescuento(MESES_DESCUENTO);
            membresia.setPrecioPromocional(monto);
        }
        membresiaRepository.save(membresia);

        Pago pago = new Pago();
        pago.setClienteId(clienteId);
        pago.setMembresiaId(membresiaId);
        pago.setFecha(LocalDateTime.now());
        pago.setMonto(monto);
        pago.setMetodo(metodo);
        pago.setCodigoOperacion("ZC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        pago.setEstado("Pagado");
        cuentaService.activar(clienteId);
        return pagoRepository.save(pago);
    }
}
