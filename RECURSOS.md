# Recursos visuales de ZambaCare

## Identidad

Se consultó la [página oficial de Zamba Pisando Tierra](https://www.zambapisandotierra.com/) como referencia de color. La composición, el símbolo de ZambaCare, las pantallas y los componentes se diseñaron para este proyecto académico siguiendo la guía de diseño frontend proporcionada para el encargo.

| Variable CSS | Color | Uso |
| --- | --- | --- |
| `--primary` | `#73001F` | Borgoña de referencia, botones y navegación activa |
| `--secondary` | `#9F3946` | Complemento de marca |
| `--background` | `#F5F2EE` | Fondo cálido |
| `--surface` | `#FFFFFF` | Superficies claras |
| `--text-primary` | `#3F342F` | Texto marrón |
| `--text-secondary` | `#776E67` | Texto secundario |
| `--accent` | `#727B52` | Oliva de apoyo |

El beige `#F4E7D6` acompaña los colores de referencia. Los tonos oliva y marrón se añadieron para expresar cuidado, naturaleza y cercanía. Los valores efectivos se encuentran en `src/main/resources/static/css/styles.css`.

## Imágenes originales generadas

Se utilizó generación de imágenes integrada en Codex, mediante la herramienta `image_gen`, en modo **generación desde texto**. No se utilizaron fotografías de clientes reales. Las imágenes se guardaron localmente y no dependen de URLs externas.

Carpeta base: `src/main/resources/static/img/`.

| Archivo | Contenido y uso |
| --- | --- |
| `logo/zambacare.svg` | Símbolo vectorial original de rizo/hoja, construido con SVG |
| `clientes/valeria.png` | Retrato de una mujer ficticia; login, bienvenida y avatar de Valeria |
| `clientes/enero.png` | Díptico ilustrativo de cabello antes/después en enero |
| `clientes/marzo.png` | Díptico ilustrativo de marzo |
| `clientes/junio.png` | Díptico ilustrativo de junio |
| `clientes/septiembre.png` | Díptico ilustrativo de septiembre |
| `productos/shampoo.png` | Envase ficticio Raíz, Limpieza suave |
| `productos/acondicionador.png` | Envase ficticio Raíz, Suavidad |
| `productos/mascarilla.png` | Envase ficticio Raíz, Hidratación; también ilustra la variante intensiva |
| `productos/leave-in.png` | Envase ficticio Raíz, Leave-in |
| `productos/gel.png` | Envase ficticio Raíz, Definición |
| `productos/aceite.png` | Envase ficticio Raíz, Óleo nutritivo |
| `productos/shampoo-equilibrio.png` | Envase ficticio Raíz, Equilibrio |

### Descripciones utilizadas para la generación

Estos son los contenidos de los prompts, resumidos en español para documentar la dirección visual:

- **Retrato:** fotografía natural de una mujer latina ficticia de 27 años, piel cálida, rizos oscuros 3B hasta los hombros, blusa de lino marfil y sonrisa relajada. Salón beige con hojas de olivo desenfocadas, luz suave de ventana. Composición horizontal 3:2, persona hacia la derecha y espacio tranquilo a la izquierda, sin letras.
- **Productos:** fotografía de estudio de un único envase completo y centrado sobre fondo beige cálido, con una pequeña rama botánica y sombra suave. Composición vertical 2:3, marca ficticia “Raíz” y etiqueta mínima. Variantes: botella marfil con dispensador marrón para shampoo; botella crema con tapa oliva para acondicionador; tarro oliva con tapa crema para mascarilla; dispensador marfil y borgoña para leave-in; tubo oliva translúcido para gel; gotero ámbar con bulbo crema para aceite; botella borgoña con dispensador marfil para shampoo Equilibrio.
- **Evolución:** dípticos de dos paneles iguales, vista posterior de una mujer latina ficticia con rizos oscuros 3B a los hombros, blusa marfil, pared beige y luz natural. Sin letras ni rostros. Enero: textura seca y esponjada frente a rizos algo más agrupados. Marzo: frizz frente a mayor agrupación e hidratación visual. Junio: cabellos sueltos frente a definición más ordenada. Septiembre: deshidratación leve frente a rizos definidos e hidratados. Son ilustraciones independientes para demostrar el diseño de seguimiento, no registros fotográficos de un tratamiento real.

## Bibliotecas y fuentes

| Recurso | Fuente oficial | Licencia local |
| --- | --- | --- |
| Bootstrap 5.3.8 | https://github.com/twbs/bootstrap/tree/v5.3.8 | `static/vendor/bootstrap/LICENSE` (MIT) |
| Bootstrap Icons 1.13.1 | https://github.com/twbs/icons/tree/v1.13.1 | `static/vendor/bootstrap-icons/LICENSE` (MIT) |
| Lora | https://github.com/google/fonts/tree/main/ofl/lora | `static/fonts/LICENSE-Lora.txt` (SIL OFL) |
| Plus Jakarta Sans | https://github.com/google/fonts/tree/main/ofl/plusjakartasans | `static/fonts/LICENSE-Plus-Jakarta-Sans.txt` (SIL OFL) |
| Maven Wrapper 3.3.4 | https://github.com/apache/maven-wrapper/tree/maven-wrapper-3.3.4 | Apache License 2.0; avisos incluidos en scripts y JAR |

Las rutas de licencias de la tabla son relativas a `src/main/resources/`. `fonts.css` asigna Lora a los archivos `font-1.ttf` a `font-3.ttf` y Plus Jakarta Sans a `font-4.ttf` a `font-7.ttf`.

Los gráficos son elementos CSS/SVG de la interfaz con valores ficticios escritos en HTML. No se usa un servicio de gráficos ni una API de datos.
