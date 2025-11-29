# Buscaminas MVC – Java (Consola)

Este proyecto es una implementación completa del clásico **Buscaminas**, desarrollado siguiendo el patrón **Modelo–Vista–Controlador (MVC)** y organizado según la siguiente estructura de paquetes usada en Eclipse:

```
src/
 └─ ups.edu.controlador/
       └─ ControladorBuscaminas.java
 └─ ups.edu.modelo/
       ├─ Casilla.java
       ├─ CasillaDescubierta.java
       ├─ JuegoException.java
       └─ Tablero.java
 └─ ups.edu.vista/
       ├─ VistaJuego.java
       └─ MainBuscaminas.java
```

---

## Características principales

-  Arquitectura **MVC real** con paquetes separados.  
-  Tablero **10x10** con **10 minas aleatorias**.  
-  Descubrimiento en cascada (flood-fill).  
-  Sistema de **marcación de banderas**.  
-  **Serialización** completa del tablero (`buscaminas.dat`).  
-  Interfaz por consola clara y fácil de usar.  

---

##  Paquetes y responsabilidades

###  ups.edu.modelo  
Contiene toda la lógica del juego:
- `Casilla`  
- `CasillaDescubierta` (excepción personalizada)  
- `JuegoException`  
- `Tablero` (mina, recursividad, contar vecinos, validar coordenadas)

###  ups.edu.vista  
Maneja todo lo que se muestra en consola:
- Menú principal  
- Tablero  
- Mensajes  
- Entradas y salidas de texto

###  ups.edu.controlador  
Coordina la interacción usuario–juego–vista:
- Interpreta comandos  
- Llama al modelo  
- Actualiza la vista  
- Guarda/carga partidas  

---

## Cómo ejecutar

1. Abrir Eclipse.  
2. Crear un proyecto Java vacío.  
3. Crear los paquetes:
   - `ups.edu.controlador`
   - `ups.edu.modelo`
   - `ups.edu.vista`
4. Crear las clases correspondientes dentro de cada paquete.  
5. Ejecutar desde:

```
ups.edu.vista.MainBuscaminas
```

---

##  Controles del juego

| Acción | Comando | Ejemplo |
|--------|---------|----------|
| Descubrir casilla | A# | `A5` |
| Marcar / Quitar bandera | M A# | `M A5` |
| Guardar partida | G | `G` |
| Cargar partida | Desde menú | Opción 2 |
| Ver ayuda | Desde menú | Opción 3 |
| Salir | Desde menú | Opción 4 |

---

##  Guardado y carga de partidas

El juego usa **serialización Java**, lo que permite guardar el tablero completo:

- Casillas descubiertas  
- Banderas  
- Posiciones reales de las minas  
- Progreso actual  

El archivo generado es:

```
buscaminas.dat
```

---

##  Conceptos clave que demuestra este proyecto

###  Patrón MVC  
Separación clara entre:
- **Modelo** → Lógica  
- **Vista** → Interfaz  
- **Controlador** → Flujo  

###  Excepciones personalizadas  
Ideal para controlar errores del usuario.

###  Recursividad  
Para descubrir automáticamente zonas vacías.

###  Serialización  
Guardar objetos completos en un archivo binario.

---

##  Conclusión

Este proyecto es perfecto como práctica para:
- Exámenes de POO  
- Comprender MVC  
- Entender manejo de excepciones  
- Serialización de objetos  
- Organización profesional de paquetes en Java  

##  Autores:

- Oscar Tacuri
- Wilson Siguencia
- Adriel Ochoa
- Freddy Potes
