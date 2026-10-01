# Verificación del primer entregable

Fecha de revisión: 28 de septiembre de 2026.

## Compilación y arranque

- Compilación Maven completada con `BUILD SUCCESS`, usando JDK 21 y objetivo Java 17.
- Inicio comprobado mediante el plugin `spring-boot:run` y mediante el JAR ejecutable.
- Arranque de Spring Boot 3.5.6 y Tomcat en el puerto 8080 sin errores.
- Recompilación y arranque final después de ajustar la transición entre Offcanvas y modales.
- Dos clases Java: aplicación principal y un controller con 12 mappings GET.
- Dependencias declaradas: `spring-boot-starter-web` y `spring-boot-starter-thymeleaf`.

## Rutas y recursos

Las 12 rutas listadas en el README respondieron con HTTP **200**. Se recorrieron los destinos de enlaces y formularios de las vistas renderizadas: todos corresponden a esas rutas y no se encontraron enlaces internos con error 404.

Se verificaron 19 recursos únicos referenciados por las páginas —imágenes, hojas de estilo, scripts y favicon—, además de las fuentes referenciadas por CSS. Todos cargan desde la misma aplicación. Se comprobó que las imágenes se muestran y que la fuente de Bootstrap Icons se aplica.

Las vistas renderizadas tienen un `<main>` y un `<h1>`, sin IDs duplicados, sin expresiones Thymeleaf pendientes y con destinos existentes para tabs, modales, Offcanvas y enlaces internos a secciones. Las respuestas verificadas no establecen cookies de sesión.

## Interacciones comprobadas en el navegador

- Login: campos obligatorios, correo válido, alternar visibilidad de contraseña y navegación al Dashboard sin enviar los campos en la URL.
- Recuperación: formulario de prueba y confirmación visual sin envío de correo.
- Clientes: búsqueda, categorías, contador y estado vacío.
- Buscador de la navbar: búsqueda de Valeria y navegación a su perfil.
- Notificaciones: despliegue de recordatorios y toast al simular su lectura.
- Edición visual: apertura con datos de la fila y toast de simulación.
- Nuevo cliente: validación y modal de confirmación. Al regresar siguen los ocho registros estáticos.
- Perfil: cambio de pestañas y acceso a diagnóstico, historial, evolución y rutina.
- Diagnóstico: selección visual y navegación al resultado fijo.
- Historial: filtros por categoría y navegación al formulario de tratamiento.
- Tratamiento: formulario y modal de éxito. Al volver se conservan los cuatro eventos originales.
- Evolución: acceso a la rutina y sus siete días; toast de acción visual.
- Catálogo: filtro Shampoo con dos resultados y modal con descripción, modo de uso, beneficio y recomendación.
- Perfil de Andrea: apertura del modal desde el menú.
- Cierre: confirmación, cancelación y regreso al login.
- Móvil: apertura del menú, navegación a productos y menú cerrado al cargar la nueva pantalla.
- Móvil: cierre del Offcanvas antes de abrir el modal de cuenta; solo queda un diálogo activo.
- Móvil: detalle de producto desplazable con botón de regreso accesible.

## Responsive

Se revisaron las 12 rutas a anchos de **320, 390, 768, 1024 y 1440 px**: 60 combinaciones de pantalla y ancho. No se detectó desbordamiento horizontal del documento.

Se inspeccionaron visualmente el login, Dashboard, perfil, evolución y catálogo, además del menú y modales en móvil. Los cuadros de datos permanecen dentro de `.table-responsive`. El sidebar se presenta fijo desde 992 px y se convierte en Offcanvas por debajo de ese ancho. Se restauró el tamaño normal del navegador después de las pruebas.

La revisión final de consola de las interacciones móviles no devolvió errores ni advertencias JavaScript de la aplicación.

## Límite del avance

La verificación corresponde a una interfaz con datos estáticos. Las acciones no crean registros, no calculan diagnósticos ni autentican usuarios. No hay pruebas de base de datos o de API porque esos componentes no existen en este entregable.

No hay capas `model`, `entity`, `service`, `repository`, `dto` ni `config`; tampoco código de almacenamiento, peticiones AJAX o llamadas `fetch`. El JavaScript opera únicamente sobre la interfaz y las rutas de navegación.
