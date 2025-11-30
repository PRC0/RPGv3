# Changelog

Todos los cambios notables de este proyecto serán documentados en este archivo.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
y este proyecto adhiere a [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.2.0] - 2025-01-XX

### ✨ Agregado

#### Sistema de Quests Completo
- **QuestObjective.java** - Objetivos rastreables con 6 tipos
- **7 Quests completas**:
  - Plaga de Slimes (kill 5 slimes)
  - Suministros para el Templo (collect 3 potions)
  - Lobos Salvajes (kill 3 wolves)
  - Campamento Goblin (kill 7 goblins)
  - Camino del Guerrero (reach level 5)
  - Amenaza No-Muerta (kill 4 skeletons)
  - Maestro de Combate (win 10 battles)
- Progreso en tiempo real por objetivo
- Recompensas múltiples (XP + Oro + Items)
- Notificaciones automáticas de progreso

#### Sistema de Drops
- **ItemDrop.java** - Items con probabilidad de drop
- **LootTable.java** - Tablas de loot por enemigo
- 8 enemigos con tablas únicas:
  - Slime: 5-15 oro, 15% potion
  - Wolf: 15-35 oro, 30% potion, 10% boots
  - Goblin: 20-50 oro, 25% potion, 8% dagger
  - Skeleton: 10-25 oro, 20% potion, 12% shield, 5% ring
  - Bandit: 30-70 oro, 35% potion, 20% mana, 15% sword
  - Orc: 35-80 oro, 40% potion, 20% armor, 15% axe
  - Dark Mage: 50-110 oro, 50% mana, 18% ring, 10% grimoire
  - Dragon: 100-250 oro, 60% mana, 25% sword, 20% armor
- Generación automática al vencer enemigos
- Items añadidos realmente al inventario del jugador

#### Arte Visual
- **Pixel Art Assets** para personajes: Hero, Mage, Archer, Priest
- **Pixel Art Assets** para enemigos: Slime, Goblin, Orc, Skeleton, Dragon Whelp, Bandit, Wolf, Dark Mage
- Integración en BattlePanel para visualización de combate


### 🔧 Mejorado

#### UI - Panel de Quests
- Removida columna ID (confusa)
- Añadida columna Progreso con porcentaje
- Fuente aumentada 13pt → 14pt
- Área de detalles ampliada 150px → 200px
- Formato mejorado con símbolos (📜📊🎯🎁)
- Lookup de quests cambiado de ID a Título
- Objetivos muestran progreso: [2/5] Eliminar 5 Slimes

#### UI - Botones
- BasicButtonUI aplicado para colores correctos en Windows
- Tamaño aumentado:
  - Quest buttons: 160x40 → 180x45
  - Inventory buttons: 120x40 → 140x45
- Fuente aumentada 14pt → 15pt
- 4 estados de hover (normal, hover, pressed, released)
- Colores visibles y consistentes

#### BattleManager
- ✅ **FIX CRÍTICO**: Items ahora realmente se añaden al inventario
- Antes: código comentado `// player.getInventory().addItem(item)`
- Ahora: `player.getInventory().add(item)` funcional
- Log mejorado con "=== ITEMS DROPEADOS ===="
- Notificaciones a QuestManager tras victoria

#### Quest System
- Métodos de notificación automáticos:
  - `notifyEnemyKilled(enemyName)`
  - `notifyItemCollected(itemName)`
  - `notifyLevelReached(level)`
  - `notifyBattleWon()`
- Actualización automática de progreso
- Detección de quests completadas

### 🐛 Corregido
- Items dropeados ahora realmente aparecen en inventario
- Botones ahora visibles en Windows (BasicButtonUI)
- Quest lookup funciona con títulos en vez de IDs
- Progreso de quests visible correctamente
- **InventoryPanel**: Corregido problema de contraste en detalles de items (texto blanco sobre fondo oscuro)

### 📚 Documentación
- Actualizado README.md con features completas
- Actualizado SISTEMA_QUESTS_DROPS.md con detalles de implementación
- Añadido INSTRUCCIONES_GITHUB.md para subida al repositorio
- Documentadas mejoras de UI

---

## [1.1.0] - 2024-11-14

### ✨ Agregado

#### Nuevos Enemigos (5)
- Orc Guerrero (fuerte, alta defensa)
- Mago Oscuro (ataque mágico alto)
- Lobo Salvaje (rápido, melee)
- Cachorro de Dragón (mini-boss)
- Bandido (ataque a distancia)

#### Nuevos Items (4)
- Shield - Escudo equipable con bonus de defensa
- Boots - Botas con bonus de defensa y HP
- Ring - Anillo mágico con bonus múltiples
- ManaPotion - Poción consumible que restaura MP

#### Sistema de Constantes
- GameConstants.java con 100+ constantes
- Categorías: Combate, XP, Stats, Curación, UI, Colores
- Elimina todos los magic numbers del código
- Facilita balanceo del juego

#### Mejoras de UX
- Tooltips en todos los botones del menú
- Tooltips en botones de batalla
- Descripciones contextuales de acciones
- Tooltip en log de batalla

### 🔧 Mejorado

#### Sistema de Encuentros
- Encuentros aleatorios variados (4 tipos)
- 40% enemigo individual
- 20% patrulla (2 enemigos)
- 25% horda (3 enemigos débiles)
- 15% encuentro peligroso (mini-boss + secuaz)

#### Balanceo
- Curva de XP mejorada (15-150 XP por enemigo)
- Stats de enemigos balanceados
- Constantes centralizadas para fácil ajuste

#### Código
- Uso de GameConstants en lugar de números hardcodeados
- Método setCurrentMana() agregado a Character
- Validación de rango en pociones
- Código más legible y mantenible

### 🐛 Corregido

- Método setCurrentMana faltante en Character
- Validación de mana máximo en pociones
- Uso correcto de addDefenseBonus, addAttackBonus, addMagicBonus

### 📚 Documentación

- IMPROVEMENTS.md con todas las mejoras detalladas
- Documentación de nuevas clases
- Changelog actualizado con versión 1.1

## [1.0.0] - 2024-11-14

### ✨ Agregado

#### Backend Completo
- Sistema de combate por turnos con BattleManager
- 4 clases jugables: Warrior, Mage, Archer, Priest
- Sistema de inventario con equipamiento dinámico
- Sistema de experiencia y level-up automático
- Persistencia con serialización (guardar/cargar partidas)
- Sistema de misiones (QuestManager)

#### Patrones de Diseño (8 implementados)
- **Singleton**: GameFacade, GameEventManager, SaveManager
- **Factory Method**: CharacterFactory, EnemyFactory
- **Builder**: CharacterBuilder para construcción flexible
- **Strategy**: AttackStrategy (Melee, Magic, Ranged) + EnemyAI (Aggressive, Defensive, Evasive)
- **Observer**: GameEventManager con suscripciones a eventos
- **Composite**: EnemyGroup para batallas con múltiples enemigos
- **Facade**: GameFacade coordinando todos los subsistemas
- **Decorator**: EquipmentDecorator (EnchantedWeapon, ReinforcedArmor)

#### GUI Completa (Swing)
- MainGameWindow con arquitectura de ventana única
- 10 paneles funcionales:
  - TopPanel: HP/MP bars, nivel, oro
  - LeftMenuPanel: Navegación
  - RightStatsPanel: Estadísticas del personaje
  - BottomLogPanel: Log de eventos con timestamps
  - ExplorePanel: Exploración y descanso
  - BattlePanel: Combate por turnos
  - InventoryPanel: Gestión de items
  - QuestPanel: Gestión de misiones
  - StatsPanel: Estadísticas completas
- Tema visual profesional sin emojis
- Paleta de colores oscura (#2C3E50, #34495E)
- Fuentes Segoe UI y Consolas

#### Documentación
- README completo con instrucciones
- JavaDoc en 40+ clases
- Comentarios explicativos en código crítico
- Diagramas de arquitectura en README

### 🔧 Mejorado

- Optimización de rendimiento en sistema de combate
- Validación de inputs en diálogos de creación de personaje
- Manejo robusto de errores en SaveManager
- Responsividad de la interfaz gráfica

### 🐛 Corregido

- Error de versión de Java compilado (Java 18 → 17)
- Problemas de inicialización de enemigos
- Bugs en sistema de equipamiento
- Errores de concurrencia en Observer pattern

### 🔒 Seguridad

- Validación de archivos de guardado antes de deserializar
- Manejo seguro de excepciones en persistencia

## [Unreleased]

### 🎯 Planificado para v1.1.0

- [ ] Tests unitarios con JUnit
- [ ] Diagrama UML completo
- [ ] JAR ejecutable empaquetado
- [ ] Sistema de achievements
- [ ] Más tipos de enemigos
- [ ] Nuevos items y equipamiento
- [ ] Sistema de crafting básico
- [ ] Mejoras de balanceo

### 💡 Ideas Futuras

- Sistema de mazmorras procedurales
- Multiplayer local (hot-seat)
- Editor de personajes avanzado
- Sistema de clases híbridas
- Internacionalización (i18n)
- Soporte para mods

---

## Tipos de Cambios

- **✨ Agregado**: Nuevas funcionalidades
- **🔧 Mejorado**: Mejoras en funcionalidades existentes
- **🐛 Corregido**: Bugs corregidos
- **🔒 Seguridad**: Mejoras de seguridad
- **⚠️ Deprecado**: Funcionalidades que serán removidas
- **🗑️ Removido**: Funcionalidades removidas
- **🏗️ Refactorizado**: Cambios de código sin afectar funcionalidad

[1.0.0]: https://github.com/tu-usuario/RPGv3/releases/tag/v1.0.0
