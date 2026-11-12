# Zambacare

Zambacare es un sistema web de gestión y seguimiento capilar. El segundo avance conecta la interfaz con un backend en Spring Boot y una base de datos MySQL para administrar clientes, diagnósticos, planes de tratamiento, sesiones, evolución fotográfica y membresías mensuales.

El sistema tiene dos recorridos. El especialista gestiona la información capilar y registra cada atención. El cliente crea su cuenta, realiza un pago mensual simulado y consulta su plan, los tiempos estimados, su historial y las comparaciones de antes y después.

## Funciones implementadas

- Registro y consulta de clientes desde MySQL.
- Diagnósticos capilares asociados a un cliente.
- Planes con objetivo, sesiones y duración estimada.
- Registro de tratamientos e historial de servicios.
- Seguimiento con fotografías guardadas dentro del proyecto.
- Portal del cliente con plan, evolución, historial y membresía.
- Creación de cuenta y pago mensual simulado.
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

## Accesos para la exposición

Especialista:

```text
Correo: andrea.salazar@zambacare.pe
Contraseña: Zambacare2026
```

El botón **Entrar como cliente** abre el portal precargado de Valeria Mendoza. El botón **Crear cuenta** permite demostrar el flujo de registro, elección de membresía y pago mensual.

## Datos iniciales

Cuando la base está vacía, `DataInitializer` registra ocho clientes y el seguimiento completo de Valeria Mendoza. Al reiniciar la aplicación, los registros no se duplican.

## Pruebas

```powershell
.\mvnw.cmd test
```

Las pruebas cargan el contexto de Spring y verifican las rutas principales con una base aislada de prueba. La aplicación normal utiliza MySQL.

## Alcance pendiente

El acceso todavía no protege las rutas. Spring Security, roles reales, sesiones, recuperación de contraseña y autorización según el estado de la membresía se implementarán en el avance final.

La guía para preparar la exposición y el apartado de desarrollo del informe está en [GUIA_EXPOSICION_E_INFORME.md](GUIA_EXPOSICION_E_INFORME.md).
