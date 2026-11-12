# Guía del segundo avance

## Diapositivas para la exposición

La presentación puede tener entre 10 y 12 diapositivas. Conviene usar capturas grandes y poco texto.

### 1. Portada

- Zambacare.
- Sistema de gestión y seguimiento capilar.
- Integrantes, curso, docente, ciclo y año.

### 2. Problema y objetivo

Explica que la información del cliente, sus tratamientos y su evolución necesitan mantenerse en un solo lugar. El objetivo de este avance es conectar el diseño del primer entregable con un backend y una base de datos.

### 3. Alcance del 60 %

Presenta una tabla con tres estados:

| Implementado | En progreso | Avance final |
|---|---|---|
| Clientes, diagnósticos, planes, tratamientos, evolución, membresías y pagos simulados | Edición completa y carga de nuevas fotografías | Spring Security, roles y autorización de rutas |

### 4. Arquitectura

Incluye este flujo:

```text
Navegador
   ↓
Controller Spring MVC
   ↓
Service
   ↓
Repository Spring Data
   ↓
MySQL
```

Explica que Thymeleaf recibe los datos desde los controladores y construye el HTML en el servidor.

### 5. Modelo de datos

Muestra un diagrama sencillo:

```text
Cliente
 ├── Diagnóstico
 ├── Plan de tratamiento
 │    └── Sesión de tratamiento
 ├── Evolución capilar
 ├── Cuenta de cliente
 └── Membresía
      └── Pago
```

### 6. Gestión del especialista

Capturas recomendadas:

- Directorio dinámico de clientes.
- Perfil de Valeria con su plan y progreso.
- Formulario de diagnóstico o tratamiento.

Describe que los formularios ahora envían información al servidor y que Spring Data la guarda en MySQL.

### 7. Plan y tiempos estimados

Usa una captura del plan de tratamiento y señala:

- Objetivo.
- Sesiones completadas y totales.
- Duración estimada por sesión.
- Próxima fecha.
- Porcentaje de progreso.

### 8. Evolución capilar

Muestra una captura de antes y después. Explica que la base guarda la ruta de cada imagen y los indicadores de hidratación, definición, frizz y elasticidad.

### 9. Portal del cliente

Incluye una captura del inicio del cliente. Destaca que el cliente puede ver su próxima sesión, tiempo estimado, progreso, historial y estado de membresía.

### 10. Registro y pago mensual

Muestra en una misma diapositiva tres capturas pequeñas:

1. Crear cuenta.
2. Elegir membresía.
3. Confirmar pago.

Aclara verbalmente que el pago es simulado y se registra en MySQL con un código de operación.

### 11. Data sintética e integración

Incluye dos fragmentos de código cortos:

- `PlanMembresiaRepository`, donde se utiliza `List<PlanMembresiaBean>`.
- `DataInitializer`, donde se insertan clientes y seguimientos cuando la base está vacía.

No muestres clases completas. Entre 8 y 15 líneas por fragmento es suficiente.

### 12. Pruebas y siguiente avance

- Indica que se verificaron 23 rutas.
- Menciona el inicio correcto del contexto de Spring.
- Explica que Spring Security, roles, sesiones y control de membresía se presentarán en el avance final.

## Orden recomendado para la demostración

1. Inicia como especialista.
2. Abre el directorio de clientes.
3. Entra al perfil de Valeria.
4. Muestra el plan con sesiones y duración.
5. Registra un tratamiento.
6. Revisa el historial actualizado.
7. Abre la evolución capilar.
8. Cierra sesión.
9. Crea una cuenta de cliente.
10. Elige una membresía y confirma el pago.
11. Recorre el portal del cliente.

## Apartado de desarrollo del informe

Sí debes incluir capturas y código, pero cada elemento debe demostrar algo concreto. Una estructura clara sería la siguiente.

### 1. Tecnologías y modelo seleccionado

Indica que se eligió Bootstrap, Thymeleaf y Spring MVC. Justifica la elección porque permite reutilizar las vistas del primer avance y renderizar información desde el servidor sin crear una API REST separada.

### 2. Arquitectura del sistema

Describe las capas:

- **Entity:** representa las tablas.
- **Repository:** realiza operaciones con Spring Data.
- **Service:** contiene las operaciones del sistema.
- **Controller:** recibe las solicitudes y prepara las vistas.
- **Template:** presenta los datos mediante Thymeleaf.

Incluye el diagrama de arquitectura de la diapositiva 4.

### 3. Integración con MySQL

Incluye:

- Una captura del esquema o de las tablas en MySQL Workbench.
- Un fragmento breve de `application.properties` sin mostrar tu contraseña.
- Una explicación de `spring.jpa.hibernate.ddl-auto=update`.

### 4. Modelo de datos

Agrega un diagrama entidad relación. Explica las entidades principales y qué información guarda cada una. No es necesario documentar cada getter y setter.

### 5. Data sintética

Explica las dos estrategias utilizadas:

- Repositorio con `List<PlanMembresiaBean>` para los planes disponibles.
- `DataInitializer` para insertar clientes, diagnósticos, sesiones, evolución y una membresía cuando MySQL está vacío.

Incluye un fragmento corto de cada implementación.

### 6. Gestión de clientes y seguimiento

Describe el flujo Controller, Service y Repository. Puedes usar el registro de cliente como ejemplo e incluir:

- Fragmento del método `registrarCliente`.
- Fragmento de `ClienteService.guardar`.
- Declaración de `ClienteRepository`.
- Captura del formulario y del perfil resultante.

### 7. Planes, sesiones y evolución

Explica cómo el plan calcula el porcentaje de avance y cómo una sesión puede aumentar las sesiones completadas. Incluye una captura del plan y otra de antes y después.

### 8. Cuenta, membresía y pago

Describe el recorrido registro, plan, pago y portal. Aclara que:

- El pago es una simulación académica.
- No se guardan números de tarjeta.
- Se genera un código de operación.
- La membresía se activa durante un mes.
- Las rutas aún no están protegidas porque Spring Security pertenece al avance final.

### 9. Thymeleaf y navegación

Incluye un ejemplo corto de `th:each`, `th:text` y `th:href`. Acompáñalo con una captura donde se vea el resultado generado.

### 10. Pruebas realizadas

Agrega una captura del resultado `BUILD SUCCESS` y explica que la prueba carga las rutas del especialista, el registro, el pago y el portal del cliente.

### 11. Resultado y trabajo pendiente

Resume qué partes alcanzaron el 60 % y enumera lo que se implementará después: Spring Security, roles, sesiones, autorización, renovación automática y carga de fotografías.

## Capturas recomendadas para el informe

Entre 8 y 10 capturas son suficientes:

1. Login con los dos tipos de acceso.
2. Dashboard del especialista.
3. Clientes cargados desde MySQL.
4. Perfil con plan de tratamiento.
5. Registro de tratamiento.
6. Evolución antes y después.
7. Registro del cliente.
8. Selección de membresía y pago.
9. Portal del cliente.
10. Tablas en MySQL Workbench o resultado de las pruebas.

Cada figura debe llevar número, título y una explicación de dos o tres líneas. Evita colocar capturas sin describir qué requisito demuestran.

## Fragmentos de código recomendados

Usa fragmentos breves de:

- Una entidad con `@Entity` e `@Id`.
- Un repositorio que extienda `JpaRepository`.
- Un método de servicio con `@Transactional`.
- Un controlador que agregue información al `Model`.
- El repositorio sintético con `List<PlanMembresiaBean>`.
- El inicializador de datos.
- Un `th:each` en una tabla.

No pegues archivos completos ni bloques de getters y setters. El código debe apoyar la explicación, no reemplazarla.
