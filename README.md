# ZambaCare · Primer entregable

**Sistema de Gestión y Seguimiento Capilar para Zamba Pisando Tierra**

Proyecto académico de Desarrollo FullStack, Semana 08. Contiene 12 pantallas terminadas, responsive y navegables. Spring Boot se utiliza para iniciar el servidor, resolver rutas y renderizar HTML con Thymeleaf.

Toda la información es ficticia y está escrita en las vistas. Los formularios validan en el navegador y simulan acciones mediante navegación, modales o toasts. No guardan cambios.

## Ejecutar

Para obtener el proyecto en otra computadora:

```shell
git clone https://github.com/Dostoyevsky1/Proyecto-FullStack.git
cd Proyecto-FullStack
```

Requisitos: JDK 17 o superior y Maven 3.6.3 o superior. Se verificó con JDK 21 y Maven 3.9.11. El código se compila para Java 17.

Desde la carpeta que contiene `pom.xml`:

```shell
mvn spring-boot:run
```

Abrir **http://localhost:8080/**. Para detener la aplicación iniciada en una terminal, usar `Ctrl+C`.

### Windows sin Maven instalado

El proyecto incluye Maven Wrapper. En PowerShell, configurar `JAVA_HOME` con la ubicación real del JDK y ejecutar:

```powershell
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-21'
.\mvnw.cmd spring-boot:run
```

En macOS o Linux, con `JAVA_HOME` configurado:

```shell
sh mvnw spring-boot:run
```

La primera ejecución requiere Internet para descargar Maven y las dependencias Java. Bootstrap, Icons, tipografías, imágenes, CSS y JavaScript están incluidos en el proyecto: el navegador no necesita servicios externos para mostrar las pantallas.

El puerto 8080 debe estar libre. Si ya se está ejecutando ZambaCare, abrir esa instancia o detenerla antes de iniciar otra. Como alternativa temporal:

```powershell
.\mvnw.cmd spring-boot:run '-Dspring-boot.run.arguments=--server.port=8081'
```

### Acceso de demostración

Usar cualquier correo con formato válido y una contraseña de prueba de al menos cuatro caracteres. Por ejemplo:

- Correo: `andrea@ejemplo.com`
- Contraseña: `demo2026`

No son credenciales reales: cualquier combinación que cumpla la validación visual permite navegar al Dashboard. “Recordarme”, recuperación de contraseña y cierre de sesión también son demostraciones visuales.

### Generar un archivo ejecutable

```powershell
.\mvnw.cmd package
java -jar target/zambacare-0.0.1-SNAPSHOT.jar
```

## Estructura

```text
Proyecto-FullStack/
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .gitignore
├── .gitattributes
├── .mvn/wrapper/
│   ├── maven-wrapper.jar
│   └── maven-wrapper.properties
├── README.md
├── RECURSOS.md
├── VERIFICACION.md
└── src/main/
    ├── java/com/zambacare/
    │   ├── ZambaCareApplication.java
    │   └── controller/
    │       └── PageController.java
    └── resources/
        ├── application.properties
        ├── templates/
        │   ├── login.html
        │   ├── dashboard.html
        │   ├── clientes.html
        │   ├── nuevo-cliente.html
        │   ├── perfil-cliente.html
        │   ├── nuevo-diagnostico.html
        │   ├── resultado-diagnostico.html
        │   ├── historial-servicios.html
        │   ├── nuevo-tratamiento.html
        │   ├── evolucion-capilar.html
        │   ├── rutina-personalizada.html
        │   ├── productos-recomendados.html
        │   └── fragments/
        │       ├── head.html
        │       ├── sidebar.html
        │       ├── navbar.html
        │       └── ui.html
        └── static/
            ├── css/
            │   ├── styles.css
            │   └── fonts.css
            ├── js/app.js
            ├── fonts/                 # Tipografías locales y licencias
            ├── vendor/
            │   ├── bootstrap/         # CSS, JS y licencia
            │   └── bootstrap-icons/   # CSS, fuentes y licencia
            └── img/
                ├── logo/zambacare.svg
                ├── clientes/          # Retrato y cuatro comparaciones
                └── productos/         # Fotografías del catálogo ficticio
```

`target/` se genera al compilar. El repositorio incluye el código, los recursos locales y esta documentación. Los compilados, cachés, ZIP de entrega, guías de trabajo y el resumen personal de la conversación se conservan como archivos locales excluidos de Git.

Solo existen dos archivos Java. `PageController` contiene 12 métodos GET que devuelven nombres de vistas; no recibe formularios ni entrega datos mediante `Model`.

## Rutas

| Pantalla | Ruta GET | Vista |
| --- | --- | --- |
| Login | `/` | `login.html` |
| Dashboard | `/dashboard` | `dashboard.html` |
| Clientes | `/clientes` | `clientes.html` |
| Nuevo cliente | `/clientes/nuevo` | `nuevo-cliente.html` |
| Perfil de Valeria | `/clientes/perfil` | `perfil-cliente.html` |
| Nuevo diagnóstico | `/diagnosticos/nuevo` | `nuevo-diagnostico.html` |
| Resultado del diagnóstico | `/diagnosticos/resultado` | `resultado-diagnostico.html` |
| Historial de servicios | `/historial` | `historial-servicios.html` |
| Registrar tratamiento | `/tratamientos/nuevo` | `nuevo-tratamiento.html` |
| Evolución capilar | `/seguimiento/evolucion` | `evolucion-capilar.html` |
| Mi Rutina Zamba | `/seguimiento/rutina` | `rutina-personalizada.html` |
| Productos recomendados | `/productos` | `productos-recomendados.html` |

## Recorrido para la exposición

1. Ingresar desde el login y explicar los indicadores, agenda, clientes recientes y gráfico del Dashboard.
2. Abrir **Clientes**, buscar “Valeria” y probar los filtros por tipo de cabello. El directorio tiene ocho clientes ficticios.
3. Abrir **Nuevo cliente**, demostrar las validaciones, completar el formulario y mostrar el modal de confirmación. Volver al directorio para comprobar que conserva los mismos ocho registros.
4. Entrar en el perfil de **Valeria Mendoza**: 27 años, cabello 3B, porosidad alta, densidad media, grosor fino y deshidratación moderada. Recorrer sus pestañas.
5. Abrir **Nuevo diagnóstico**, seleccionar opciones y generar el resultado. El resultado es un ejemplo fijo; no depende de cálculos ni de las selecciones del formulario.
6. Abrir **Productos recomendados**, probar las categorías y consultar el modal con descripción, modo de uso, beneficio y recomendación.
7. Desde el perfil o **Tratamientos**, abrir el historial y registrar un tratamiento de prueba. Mostrar el modal de éxito y regresar al historial estático.
8. Abrir **Seguimiento**: comparar las fotografías ilustrativas de enero, marzo, junio y septiembre; revisar las barras y el gráfico.
9. Pulsar **Ver rutina actual** y mostrar los siete días, consejos y productos incluidos.
10. Reducir el ancho del navegador para mostrar el menú Offcanvas, las tarjetas reorganizadas y las tablas con desplazamiento interno.
11. Probar **Mi perfil**, notificaciones, toasts y **Cerrar sesión** con confirmación.

El perfil navegable del prototipo es el de Valeria. Los accesos “Ver perfil” del directorio y de clientes recientes llevan a esta ficha de demostración.

## Tecnologías y responsabilidad de cada una

| Herramienta | Uso en este avance |
| --- | --- |
| Java 17 / Spring Boot 3.5.6 | Iniciar la aplicación y el servidor integrado |
| Maven / Maven Wrapper | Resolver las dos dependencias declaradas y ejecutar/compilar |
| Spring MVC | Resolver las 12 rutas con un único controller |
| Thymeleaf | Cargar vistas, URLs y fragmentos compartidos |
| HTML5 / CSS3 | Estructura semántica, formularios e identidad visual |
| Bootstrap 5.3.8 | Grilla responsive, modales, Offcanvas, tabs, dropdowns y toasts |
| Bootstrap Icons 1.13.1 | Iconografía local |
| JavaScript Vanilla | Filtros, búsqueda visual, validación e interacciones |
| CSS y SVG | Gráficos estáticos del Dashboard y evolución |

No se necesita Chart.js, jQuery ni un entorno Node.js para ejecutar el proyecto.

## Alcance cumplido

- 12 pantallas completas con navegación mediante botones, enlaces y menús.
- Sidebar fijo en escritorio y Bootstrap Offcanvas por debajo de 992 px.
- Formularios con grilla responsive, etiquetas y validaciones HTML5.
- Tablas dentro de `.table-responsive`; cards adaptables a escritorio, tablet y celular.
- Modales obligatorios: cliente registrado, tratamiento registrado, detalle de producto y confirmación de cierre.
- Modales adicionales de edición visual, perfil de la especialista y recuperación de contraseña.
- Toasts, búsqueda local, filtros, tabs, estados vacíos y control de visibilidad de contraseña.
- Fotografías ilustrativas, avatares, badges, timeline, planificación semanal y gráficos.
- Fragmentos compartidos para navbar, sidebar, encabezado HTML y elementos visuales comunes.
- Colores basados en la página oficial y una composición propia para ZambaCare.
- Recursos del navegador incluidos localmente.

No se incorporaron Service, Repository, Entity, DTO, Spring Data, JPA, Hibernate, bases de datos, API REST, Spring Security, autenticación, sesiones, roles reales, persistencia ni lógica de negocio. Tampoco se utiliza `fetch`, AJAX, `localStorage` o `sessionStorage`. Los datos no se transportan entre pantallas: cada vista contiene su ejemplo estático coherente.

Las fotografías de evolución, recomendaciones, precios, datos personales y métricas son ficticios. El perfil mantiene el diagnóstico de ejemplo del 18/09/2026; las barras de evolución ilustran un seguimiento posterior a esa atención.

Consultar `VERIFICACION.md` para el resultado de las pruebas y `RECURSOS.md` para la paleta, fotografías, tipografías y licencias.
