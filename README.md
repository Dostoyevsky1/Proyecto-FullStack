# Zambacare

Zambacare es una propuesta de sistema web para **Zamba Pisando Tierra**, pensada para acompañar la atención y el seguimiento capilar de cada cliente de una manera clara, cercana y ordenada.

La aplicación permite recorrer visualmente el proceso completo de atención: desde el registro del cliente y su diagnóstico capilar hasta los tratamientos realizados, la evolución mediante fotografías, la rutina personalizada y los productos recomendados. El contenido está especialmente orientado al cuidado del cabello ondulado, rizado, afro y liso.

## ¿Cómo funciona?

Al ingresar se muestra una pantalla de acceso con credenciales de demostración. Después del inicio de sesión, el usuario llega al panel principal, donde puede revisar la agenda del día y consultar información general.

Desde el menú lateral se puede navegar por las principales secciones:

- Clientes y perfiles capilares.
- Diagnósticos y resultados.
- Historial de servicios y registro de tratamientos.
- Seguimiento de la evolución capilar.
- Rutinas personalizadas.
- Productos recomendados.

Los formularios, filtros, modales y mensajes permiten simular el uso de un sistema real. Este primer avance está centrado únicamente en la experiencia visual, por lo que utiliza datos ficticios y todavía no almacena información en una base de datos.

## Acceso de demostración

```text
Correo: andrea.salazar@zambacare.pe
Contraseña: Zambacare2026
```

## Tecnologías utilizadas

El proyecto utiliza Java 17, Spring Boot 3, Spring MVC, Thymeleaf, HTML5, CSS3, Bootstrap 5 y JavaScript. Spring Boot se encarga de iniciar la aplicación, servir las vistas y manejar las rutas de navegación.

## Ejecutar el proyecto

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

En Linux o macOS:

```bash
./mvnw spring-boot:run
```

Luego abre [http://localhost:8080](http://localhost:8080) en el navegador.

El repositorio también incluye un `Dockerfile` para desplegar la aplicación como un servicio web en Render.
