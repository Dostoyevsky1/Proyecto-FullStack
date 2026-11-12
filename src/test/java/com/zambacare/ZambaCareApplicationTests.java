package com.zambacare;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.zambacare.repository.CitaRepository;
import com.zambacare.repository.ClienteRepository;
import com.zambacare.repository.CuentaClienteRepository;
import com.zambacare.repository.SesionTratamientoRepository;
import com.zambacare.service.MembresiaService;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ZambaCareApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CuentaClienteRepository cuentaRepository;

    @Autowired
    private SesionTratamientoRepository sesionRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private MembresiaService membresiaService;

    @Test
    void cargaLasRutasPrincipales() throws Exception {
        String[] rutas = {
            "/",
            "/dashboard",
            "/citas",
            "/citas/nueva",
            "/clientes",
            "/clientes/nuevo",
            "/clientes/perfil/1",
            "/diagnosticos/nuevo?clienteId=1",
            "/diagnosticos/resultado?clienteId=1",
            "/historial?clienteId=1",
            "/tratamientos/nuevo?clienteId=1",
            "/seguimiento/evolucion?clienteId=1",
            "/planes/nuevo?clienteId=1",
            "/registro",
            "/membresia/planes?clienteId=1",
            "/pago?clienteId=1&membresiaId=1",
            "/portal/cliente?clienteId=1",
            "/portal/cliente/plan?clienteId=1",
            "/portal/cliente/evolucion?clienteId=1",
            "/portal/cliente/historial?clienteId=1",
            "/portal/cliente/membresia?clienteId=1",
            "/portal/cliente/rutina?clienteId=1",
            "/portal/cliente/productos?clienteId=1",
            "/seguimiento/rutina",
            "/productos"
        };

        for (String ruta : rutas) {
            mockMvc.perform(get(ruta)).andExpect(status().isOk());
        }
    }

    @Test
    void registraUnaCuentaDeCliente() throws Exception {
        long clientesAntes = clienteRepository.count();
        long cuentasAntes = cuentaRepository.count();

        mockMvc.perform(post("/registro")
                .param("nombres", "Rosa")
                .param("apellidos", "Campos")
                .param("correo", "rosa.campos@correo.pe")
                .param("telefono", "999111222")
                .param("clave", "Rosa2026"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrlPattern("/membresia/planes?clienteId=*"));

        assertEquals(clientesAntes + 1, clienteRepository.count());
        assertEquals(cuentasAntes + 1, cuentaRepository.count());
    }

    @Test
    void muestraMensajeCuandoElUsuarioYaExiste() throws Exception {
        long clientesAntes = clienteRepository.count();
        long cuentasAntes = cuentaRepository.count();

        String respuesta = mockMvc.perform(post("/registro")
                .param("nombres", "Valeria")
                .param("apellidos", "Mendoza")
                .param("correo", "valeria.mendoza@zambacare.pe")
                .param("telefono", "900000001")
                .param("clave", "Valeria2026"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        assertTrue(respuesta.contains("Usuario ya registrado"));
        assertEquals(clientesAntes, clienteRepository.count());
        assertEquals(cuentasAntes, cuentaRepository.count());
    }

    @Test
    void registraUnaSesionDeTratamiento() throws Exception {
        long sesionesAntes = sesionRepository.count();

        mockMvc.perform(post("/tratamientos")
                .param("clienteId", "1")
                .param("planTratamientoId", "1")
                .param("fecha", "2026-11-11")
                .param("servicio", "Hidratación profunda")
                .param("especialista", "Andrea Salazar")
                .param("duracionMinutos", "90")
                .param("resultado", "Mayor hidratación"))
            .andExpect(status().is3xxRedirection());

        assertEquals(sesionesAntes + 1, sesionRepository.count());
    }

    @Test
    void aplicaElCuponFamiliarDuranteElPago() {
        var membresia = membresiaService.elegirPlan(2L, "ESENCIAL");
        var pago = membresiaService.confirmarPago(2L, membresia.getId(), "Yape", "FAMILIAZAMBA");

        assertEquals(new BigDecimal("31.92"), pago.getMonto());
    }

    @Test
    void registraUnaCitaEnUnHorarioDisponible() throws Exception {
        LocalDate fecha = LocalDate.now().plusDays(1);
        long citasAntes = citaRepository.count();

        mockMvc.perform(post("/citas")
                .param("clienteId", "1")
                .param("fecha", fecha.toString())
                .param("horaInicio", "09:00")
                .param("duracionMinutos", "60")
                .param("servicio", "Control de evolución")
                .param("especialista", "Andrea Salazar")
                .param("observaciones", "Control mensual"))
            .andExpect(status().is3xxRedirection());

        assertEquals(citasAntes + 1, citaRepository.count());
    }

    @Test
    void evitaCrucesDeHorarioParaLaMismaEspecialista() throws Exception {
        String respuesta = mockMvc.perform(post("/citas")
                .param("clienteId", "2")
                .param("fecha", LocalDate.now().toString())
                .param("horaInicio", "10:45")
                .param("duracionMinutos", "60")
                .param("servicio", "Definición")
                .param("especialista", "Andrea Salazar"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        assertTrue(respuesta.contains("La especialista ya tiene una cita en ese horario"));
    }
}
