package com.zambacare.repository;

import com.zambacare.bean.PlanMembresiaBean;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class PlanMembresiaRepository {
    private final List<PlanMembresiaBean> planes = List.of(
        new PlanMembresiaBean("ESENCIAL", "Seguimiento mensual", new BigDecimal("39.90"), "Historial, evolución y plan de tratamiento"),
        new PlanMembresiaBean("INTEGRAL", "Cuidado integral", new BigDecimal("59.90"), "Seguimiento mensual y recomendaciones personalizadas")
    );

    public List<PlanMembresiaBean> findAll() {
        return planes;
    }

    public Optional<PlanMembresiaBean> findByCodigo(String codigo) {
        return planes.stream().filter(plan -> plan.getCodigo().equalsIgnoreCase(codigo)).findFirst();
    }
}
