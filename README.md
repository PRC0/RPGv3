# ⚔️ RPGv3 - Sistema de Combate por Turnos

<div align="center">

![Java](https://img.shields.io/badge/Java-18+-orange?style=for-the-badge&logo=java)
![Swing](https://img.shields.io/badge/GUI-Swing-blue?style=for-the-badge)
![Patterns](https://img.shields.io/badge/Design_Patterns-8-green?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

**Un RPG táctico por turnos construido con patrones de diseño GoF y arquitectura orientada a objetos**

[Características](#-características-principales) • [Instalación](#-instalación) • [Uso](#-guía-de-uso) • [Arquitectura](#-arquitectura-y-patrones) • [Contribuir](#-contribución)

</div>

---

## 📖 Descripción

**RPGv3** es un juego de rol simplificado donde el jugador controla a un héroe (Warrior, Mage, Archer o Priest) que explora escenarios, completa misiones, equipa items y combate enemigos en batallas tácticas por turnos. 

El proyecto fue diseñado como caso de estudio para demostrar la aplicación práctica de **8 patrones de diseño GoF** en un sistema de juego completo, con arquitectura en capas, persistencia de datos y una interfaz gráfica intuitiva.

### 🎯 Objetivos del Proyecto

- ✅ Consolidar conceptos de POO (herencia, polimorfismo, abstracción, encapsulación)
- ✅ Implementar patrones de diseño en un contexto realista
- ✅ Diseñar arquitectura modular y escalable
- ✅ Crear interfaz gráfica responsive con Swing
- ✅ Documentar decisiones de diseño y generar UML

---

## ✨ Características Principales

### 🎮 Sistema de Combate
- **Combate por turnos** estratégico contra enemigos individuales o grupos
- **4 Clases jugables** con habilidades únicas:
  - 🗡️ **Warrior** - Alto HP y daño físico, tanque resistente
  - 🔮 **Mage** - Poder mágico devastador, maestro de hechizos
  - 🏹 **Archer** - Ataques a distancia precisos y balanceados
  - ⛪ **Priest** - NPC curador que asiste al jugador
- **IA enemiga inteligente** con 3 comportamientos distintos
- **Sistema de experiencia** con level-up automático

### 🎒 Inventario y Equipamiento
- **Sistema de items** con armas, armaduras y consumibles
- **Equipamiento dinámico** que modifica stats en tiempo real
- **Items encantados** con bonus mágicos adicionales
- **Pociones** de curación consumibles

### 📜 Sistema de Misiones
- **Quests** con seguimiento de progreso
- Estados: Disponible → Activa → Completada
- Recompensas de experiencia y oro

### 💾 Persistencia
- **Guardar/Cargar** partida completa
- Serialización del personaje, inventario y equipo
- Sistema robusto de manejo de errores

### 🎨 Interfaz Gráfica (GUI)
- **Ventana única** con navegación fluida entre secciones
- **Actualización en tiempo real** mediante patrón Observer
- Paneles: Exploración, Inventario, Misiones, Stats, Combate
- **Log de eventos** para seguimiento de acciones

---

## 🏗️ Arquitectura y Patrones

### Patrones de Diseño Implementados (8/6 requeridos)

| Patrón | Tipo | Implementación | Propósito |
|--------|------|----------------|-----------|
| **Singleton** | Creacional | `GameFacade`, `GameEventManager` | Instancia única del motor del juego |
| **Factory Method** | Creacional | `CharacterFactory`, `EnemyFactory` | Creación polimórfica de personajes |
| **Builder** | Creacional | `CharacterBuilder` | Construcción flexible de personajes personalizados |
| **Strategy** | Comportamental | `AttackStrategy`, `EnemyAI` | Algoritmos intercambiables de ataque e IA |
| **Observer** | Comportamental | `GameEventManager` + `GameEventListener` | Notificaciones desacopladas GUI ↔ Backend |
| **Composite** | Estructural | `EnemyGroup` | Grupos de enemigos tratados como entidad única |
| **Facade** | Estructural | `GameFacade` | Interfaz simplificada para subsistemas complejos |
| **Decorator** | Estructural | `EnchantedWeapon`, `ReinforcedArmor` | Mejoras dinámicas a items sin modificar su estructura |

### Arquitectura en Capas

```
┌─────────────────────────────────────────────────┐
│            GUI Layer (Swing)                    │
│  MainGameWindow + Panels (View)                │
└─────────────────┬───────────────────────────────┘
                  │
┌─────────────────▼───────────────────────────────┐
│         Application Layer                       │
│  GameFacade (Controller/Mediator)              │
└─────────────────┬───────────────────────────────┘
                  │
┌─────────────────▼───────────────────────────────┐
│          Business Logic Layer                   │
│  ┌──────────────┬──────────────┬─────────────┐ │
│  │ Combat       │ Inventory    │ Quest       │ │
│  │ System       │ System       │ System      │ │
│  └──────────────┴──────────────┴─────────────┘ │
└─────────────────┬───────────────────────────────┘
                  │
┌─────────────────▼───────────────────────────────┐
│           Domain Model Layer                    │
│  Character, Enemy, Item, Quest (Entities)      │
└─────────────────┬───────────────────────────────┘
                  │
┌─────────────────▼───────────────────────────────┐
│        Persistence Layer                        │
│  SaveManager (Serialization)                   │
└─────────────────────────────────────────────────┘
```

### Estructura de Paquetes

```
rpg/
├── game/          # Punto de entrada (GameEngine)
├── core/          # Modelos principales (Character, Enemy, GameFacade)
├── combat/        # Sistema de combate (BattleManager, AttackStrategy)
├── factory/       # Fábricas de personajes y enemigos
├── builder/       # Constructor de personajes personalizados
├── decorator/     # Decoradores de items
├── ai/            # Inteligencia artificial enemiga
├── inventory/     # Sistema de items y equipamiento
├── quest/         # Sistema de misiones
├── events/        # Gestor de eventos (Observer)
├── persistence/   # Guardar/cargar partidas
└── ui/            # Interfaz gráfica (Swing)
```

---

## 🚀 Instalación

### Requisitos Previos

- **Java JDK 18+** (recomendado: Java 21 LTS)
- **IDE** (recomendado: Eclipse, IntelliJ IDEA, VS Code)
- **Sistema Operativo**: Windows, macOS, Linux

### Instalación Rápida

#### Opción 1: JAR Ejecutable (Recomendado)

```bash
# Descargar el JAR desde releases
java -jar RPGv3.jar
```

#### Opción 2: Compilar desde Código Fuente

```bash
# 1. Clonar el repositorio
git clone https://github.com/tu-usuario/RPGv3.git
cd RPGv3

# 2. Compilar el proyecto
cd src
javac -d ../bin -encoding UTF-8 rpg/**/*.java

# 3. Ejecutar el juego
cd ..
java -cp bin rpg.game.GameEngine
```

#### Opción 3: Importar en Eclipse

1. `File` → `Import` → `Existing Projects into Workspace`
2. Seleccionar la carpeta `RPGv3`
3. Click derecho en `GameEngine.java` → `Run As` → `Java Application`

---

## 📘 Guía de Uso

### Inicio Rápido

1. **Crear Personaje**: Selecciona tu clase (Warrior, Mage, Archer)
2. **Explorar**: Navega por el menú lateral para acceder a diferentes secciones
3. **Combatir**: Inicia batallas y usa estrategia para vencer enemigos
4. **Equipar Items**: Mejora tu personaje con armas y armaduras
5. **Completar Misiones**: Acepta y completa quests para ganar recompensas
6. **Guardar Progreso**: Usa el menú para guardar tu partida

### Controles (GUI)

| Sección | Descripción |
|---------|-------------|
| **Explorar** | Inicia encuentros aleatorios con enemigos |
| **Inventario** | Gestiona items, equipa armas/armaduras |
| **Misiones** | Ve misiones disponibles, activas y completadas |
| **Stats** | Revisa estadísticas detalladas de tu personaje |
| **Guardar** | Guarda tu progreso actual |
| **Cargar** | Carga una partida guardada |

### Ejemplo de Código (Uso Programático)

```java
// Crear personaje usando Factory
CharacterFactory factory = new CharacterFactory();
Character hero = factory.createCharacter(CharacterType.WARRIOR, "Arthas");

// O usar Builder para personalización avanzada
Character customHero = CharacterBuilder.warrior("Uther")
    .withMaxHp(150)
    .withBaseAttack(15)
    .withLevel(5)
    .build();

// Iniciar combate
GameFacade facade = GameFacade.getInstance();
facade.startNewGame(CharacterType.MAGE, "Jaina");
facade.startBattle();

// Guardar partida
facade.saveGame("save1.dat");
```

---

## 🧪 Testing

### Ejecutar Prueba de Consola

```bash
cd bin
java rpg.game.GameEngine
```

Esto ejecutará una batalla de prueba automatizada mostrando el sistema de combate en acción.

### Probar Patrones Específicos

```java
// Probar Decorator
Equippable sword = new Sword("Espada de Acero", "Espada común", 15);
sword = new EnchantedWeapon(sword, "Fuego", 8, 5);
// Ahora la espada otorga: 15 + 8 = 23 ATK y +5 MAG

// Probar Builder
Character paladin = CharacterBuilder.warrior("Uther")
    .withBaseMagic(10)  // Guerrero híbrido con magia
    .withMaxMana(30)
    .build();

// Probar Observer
GameEventManager.getInstance().subscribe(
    EventType.PLAYER_HP_CHANGED, 
    (type, data) -> System.out.println("HP cambió!")
);
```

---

## 📊 Diagrama de Clases (UML)

### Diagrama Simplificado - Patrones Principales

```
┌─────────────────┐         ┌──────────────────┐
│  GameFacade     │◄────────│  Singleton       │
│  (Singleton)    │         └──────────────────┘
└────────┬────────┘
         │ uses
         ▼
┌─────────────────┐         ┌──────────────────┐
│CharacterFactory │◄────────│  Factory Method  │
└────────┬────────┘         └──────────────────┘
         │ creates
         ▼
┌─────────────────────────────────────┐
│         Character (abstract)        │
│  + attack()                         │
│  + defend()                         │◄─── Strategy
│  + specialAbility()  [abstract]     │     (AttackStrategy)
└──────────┬──────────────────────────┘
           │
    ┌──────┴────────┬──────────┐
    ▼               ▼          ▼
┌─────────┐   ┌─────────┐  ┌──────────┐
│ Warrior │   │  Mage   │  │  Archer  │
└─────────┘   └─────────┘  └──────────┘
```

> 📄 **Nota**: Diagrama UML completo disponible en `enproceso`

---

## 🎓 Decisiones de Diseño

### ¿Por qué estos Patrones?

| Patrón | Problema Resuelto | Beneficio |
|--------|-------------------|-----------|
| **Singleton** | Múltiples instancias del motor causaban inconsistencias | Estado global controlado y acceso centralizado |
| **Factory** | Creación de personajes esparcida en el código | Creación centralizada, fácil añadir nuevas clases |
| **Builder** | Constructor con demasiados parámetros | Construcción fluida y legible |
| **Strategy** | Comportamiento de ataque hardcodeado en clases | Cambio de estrategia en runtime, reutilización |
| **Observer** | GUI acoplada al modelo | Actualización automática sin dependencias directas |
| **Composite** | Código duplicado para grupos vs enemigos individuales | Tratamiento uniforme de objetos simples y compuestos |
| **Facade** | GUI necesitaba conocer todos los subsistemas | Interfaz simple que oculta complejidad |
| **Decorator** | Modificar items requería crear muchas subclases | Composición flexible de mejoras |

### Trade-offs y Limitaciones

**Ventajas:**
- ✅ Código altamente modular y mantenible
- ✅ Fácil de extender con nuevas clases o enemigos
- ✅ Bajo acoplamiento entre capas
- ✅ Testing simplificado por separación de responsabilidades

**Limitaciones Actuales:**
- ⚠️ SaveManager solo guarda el personaje, no el estado completo del mundo
- ⚠️ Sistema de IA podría mejorarse con aprendizaje adaptativo
- ⚠️ GUI single-window limita visualización simultánea de múltiples datos

**Mejoras Futuras:**
- 🔮 Multiplayer usando patrón Proxy para comunicación red
- 🔮 Sistema de crafting con patrón Abstract Factory
- 🔮 Replay de batallas con patrón Memento
- 🔮 Comandos deshacer/rehacer con patrón Command

---

## 🤝 Contribución

¡Las contribuciones son bienvenidas! Sigue estos pasos:

### Proceso de Contribución

1. **Fork** el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add: nueva clase Necromancer'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un **Pull Request**

### Guías de Estilo

- **JavaDoc** completo en todas las clases públicas
- Nomenclatura: `PascalCase` para clases, `camelCase` para métodos
- Máximo 200 líneas por clase (principio Single Responsibility)
- Incluir comentarios explicando patrones aplicados

### Reportar Bugs

Usa la plantilla de issues con:
- Descripción del bug
- Pasos para reproducir
- Comportamiento esperado vs actual
- Screenshots si aplica

---

## 📜 Licencia

Este proyecto está bajo la Licencia MIT - ver archivo [LICENSE](LICENSE) para detalles.

```
MIT License

Copyright (c) 2025 RPGv3 Team

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction...
```

---

## 👥 Autores y Reconocimientos

### Desarrolladores

- **[Ricardo]** - *Arquitectura y Backend* - [@RicardoMaas7](https://github.com/RicardoMaas7)

### Agradecimientos

- Inspiración de patrones: [Design Patterns - GoF](https://refactoring.guru/design-patterns)
- Arquitectura: [Clean Code - Robert C. Martin](https://www.amazon.com/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882)
- GUI Design: [Oracle Swing Tutorial](https://docs.oracle.com/javase/tutorial/uiswing/)

---

## 📞 Contacto y Soporte

- 📧 Email: ri3s@protonmail.com
- 🐛 Issues: [GitHub Issues](https://github.com/RicardoMaas7/RPGv3/issues)

---

## 📈 Roadmap

### Versión 1.0 (Actual) ✅
- [x] Sistema de combate por turnos
- [x] 4 clases jugables (Warrior, Mage, Archer, Priest)
- [x] Inventario y equipamiento completo
- [x] Sistema de misiones con objetivos rastreables
- [x] Sistema de drops con probabilidades por enemigo
- [x] 8 enemigos únicos con tablas de loot
- [x] 7 quests completas con recompensas
- [x] Persistencia de datos
- [x] 8 patrones de diseño GoF
- [x] GUI con Swing (mejorada con colores y progreso)

### Versión 1.1 (Próxima) 🚧
- [ ] Sistema de crafting
- [ ] 10+ enemigos diferentes
- [ ] Múltiples mapas/zonas
- [ ] Música y efectos de sonido
- [ ] Logros y estadísticas

### Versión 2.0 (Futuro) 🔮
- [ ] Modo multijugador cooperativo
- [ ] Editor de mapas
- [ ] Mod support
- [ ] Mobile version (Android/iOS)

---

<div align="center">

### ⭐ Si te gustó el proyecto, ¡deja una estrella!

**Hecho con ❤️ usando Java y mucha ☕**

[⬆ Volver arriba](#️-rpgv3---sistema-de-combate-por-turnos)

</div>
