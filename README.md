# Gestor de Catálogo de Anime (Anime Catalog Manager)

**Autor:** Miguel Indriago  
**Materia:** Paradigma Orientado a Objetos

## Descripción
Una aplicación de escritorio en Java Swing diseñada para gestionar un catálogo personal de animes. El proyecto demuestra la aplicación de conceptos de POO, patrones GRASP y principios SOLID en un nivel intermedio-alto.

## Pre-requisitos
* **Java Development Kit (JDK):** Versión 8 o superior.
* **IDE:** IntelliJ IDEA, Eclipse, o NetBeans.
* **Librerías:** No se requieren librerías externas (utiliza Java Swing estándar y serialización nativa).

## Estructura del Proyecto
* `src/exception`: Manejo de errores personalizados.
* `src/model`: Entidades del dominio y Enums.
* `src/repository`: Capa de persistencia (Archivos).
* `src/service`: Lógica de negocio y Estrategias.
* `src/ui`: Interfaz Gráfica (Swing).

## Cómo Ejecutar
1.  Compile el proyecto desde su IDE o terminal.
2.  Ejecute la clase principal: `ui.Main`.
3.  La aplicación generará automáticamente un archivo `anime_catalog.ser` para guardar sus datos.

##️ Notas Importantes
Los datos se guardan en `anime_catalog.ser` en la raíz del proyecto. Si borra este archivo, perderá los datos guardados.