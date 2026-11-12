# Zambacare

Zambacare es un sistema web de gestión y seguimiento capilar. El segundo avance conecta la interfaz con un backend en Spring Boot y Spring Data JPA para administrar clientes, diagnósticos, planes de tratamiento, sesiones, evolución fotográfica y membresías mensuales.

El sistema tiene dos recorridos. El especialista gestiona la información capilar y registra cada atención. El cliente crea su cuenta, realiza un pago mensual simulado y consulta su plan, los tiempos estimados, su historial y las comparaciones de antes y después.

## Funciones implementadas

- Registro y consulta de clientes mediante Spring Data JPA.
- Diagnósticos capilares asociados a un cliente.
- Planes con objetivo, sesiones y duración estimada.
- Registro de tratamientos e historial de servicios.
- Seguimiento con fotografías guardadas dentro del proyecto.
- Portal del cliente con plan, evolución, historial y membresía.
- Creación de cuenta y pago mensual simulado.
- Validación de correos registrados con un mensaje comprensible para el usuario.
- Campaña familiar con cupón de descuento durante los primeros tres meses.
- Agenda diaria con capacidad máxima, horarios por especialista y estados de atención.
- Datos iniciales cargados automáticamente desde Spring Boot.
- Catálogo sintético de membresías mediante `List<PlanMembresiaBean>`.
- Navegación responsive con Thymeleaf y Bootstrap.

## Tecnologías

- Java 17 o superior
- Spring Boot 3
- Spring MVC
- Spring Data JPA
- Thymeleaf
- MySQL
- H2 para demostraciones sin un servidor de base de datos externo
- Bootstrap 5.3
- JavaScript Vanilla
- Maven

## Preparar MySQL

Crea una base de datos llamada `zambacare`:

```sql
CREATE DATABASE zambacare CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

La aplicación utiliza estas variables:

```text
DB_URL=jdbc:mysql://localhost:3306/zambacare?serverTimezone=America/Lima
DB_USERNAME=root
DB_PASSWORD=tu_contraseña
```

En PowerShell puedes configurarlas antes de iniciar:

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/zambacare?serverTimezone=America/Lima"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="tu_contraseña"
.\mvnw.cmd spring-boot:run
```

Luego abre [http://localhost:8080](http://localhost:8080).

Si no se configuran estas variables, la aplicación utiliza automáticamente una base H2 temporal. Esto permite ejecutarla y desplegarla en Render con los datos sintéticos sin depender de un servidor MySQL externo. Para conservar los registros entre reinicios se deben configurar las variables anteriores con una base MySQL disponible en la red.

## Accesos para la exposición

Especialista:

```text
Correo: andrea.salazar@zambacare.pe
Contraseña: Zambacare2026
```

El botón **Entrar como cliente** abre el portal precargado de Valeria Mendoza. El botón **Crear cuenta** permite demostrar el flujo de registro, elección de membresía y pago mensual.

## Campaña familiar

El cupón de demostración `FAMILIAZAMBA` aplica un 20 % de descuento durante los primeros tres meses. Está pensado para que los especialistas lo compartan con familiares y se valida durante el pago mensual.

## Gestión de citas

La ruta `/citas` muestra la agenda diaria del local, la ocupación, los cupos disponibles y el estado de cada atención. La agenda admite hasta 12 citas activas por día y evita que una especialista tenga dos atenciones superpuestas. Las cuentas de clientes y las reservas se administran por separado, por lo que un aumento de usuarios registrados no llena automáticamente la agenda.

## Datos iniciales

Cuando la base está vacía, `DataInitializer` registra ocho clientes y el seguimiento completo de Valeria Mendoza. Al reiniciar la aplicación, los registros no se duplican.

## Pruebas

```powershell
.\mvnw.cmd test
```

Las pruebas cargan el contexto de Spring y verifican las rutas principales con una base H2 aislada. La aplicación utiliza MySQL cuando se proporcionan `DB_URL`, `DB_USERNAME` y `DB_PASSWORD`; de lo contrario, usa H2 temporal.

## Alcance pendiente

El acceso todavía no protege las rutas. Spring Security, roles reales, sesiones, recuperación de contraseña y autorización según el estado de la membresía se implementarán en el avance final.

La guía para preparar la exposición y el apartado de desarrollo del informe está en [GUIA_EXPOSICION_E_INFORME.md](GUIA_EXPOSICION_E_INFORME.md).
