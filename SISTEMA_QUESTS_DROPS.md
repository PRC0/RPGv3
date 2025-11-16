# Sistema de Quests y Drops - RPGv3

## 🎯 RESUMEN DE MEJORAS

Se ha implementado un sistema completo de **Quests con Objetivos** y **Sistema de Drops de Items** para enemigos.

---

## 📋 SISTEMA DE QUESTS MEJORADO

### Características Principales

#### 1. **Objetivos Rastreables** (`QuestObjective`)
Cada quest ahora puede tener múltiples objetivos con progreso individual:

**Tipos de Objetivos:**
- `KILL_ENEMY` - Matar X enemigos de cierto tipo
- `COLLECT_ITEM` - Recoger X items
- `REACH_LEVEL` - Alcanzar nivel X
- `TALK_TO_NPC` - Hablar con un NPC
- `EXPLORE_AREA` - Explorar una zona
- `WIN_BATTLES` - Ganar X batallas

**Ejemplo de Uso:**
```java
Quest wolfQuest = new Quest("HUNT_WOLVES", "Lobos Salvajes", "Elimina a 3 lobos", 200, 75);
wolfQuest.addObjective(new QuestObjective(
    QuestObjective.ObjectiveType.KILL_ENEMY,
    "Lobo",
    3,
    "Eliminar 3 Lobos"
));
```

#### 2. **Progreso Automático**
Las quests se actualizan automáticamente cuando:
- Matas un enemigo → `QuestManager.notifyEnemyKilled("Lobo")`
- Recoges un item → `QuestManager.notifyItemCollected("Poción de Salud")`
- Subes de nivel → `QuestManager.notifyLevelReached(5)`
- Ganas una batalla → `QuestManager.notifyBattleWon()`

#### 3. **Recompensas Mejoradas**
Cada quest puede otorgar:
- ✨ **Experiencia (XP)**
- 💰 **Oro**
- 🎁 **Items** (espadas, armaduras, pociones, etc.)

**Ejemplo:**
```java
quest.addItemReward(new Sword("Espada de Hierro", "Espada forjada", 8));
quest.addItemReward(new HealthPotion());
```

---

## 🎁 SISTEMA DE DROPS

### Características

#### 1. **Tabla de Loot** (`LootTable`)
Cada enemigo tiene una tabla personalizada con:
- **Oro garantizado** + **Oro aleatorio**
- **Items** con probabilidades individuales

**Ejemplo:**
```java
LootTable loot = new LootTable();
loot.setGoldReward(15, 20);  // 15-35 oro
loot.addDrop(new HealthPotion(), 0.30);  // 30% chance
loot.addDrop(new Sword("...", "...", 8), 0.10);  // 10% chance
```

#### 2. **Drops por Enemigo**

| Enemigo | Oro | Items Principales | Rareza |
|---------|-----|-------------------|--------|
| **Slime** | 5-15 | Poción de Salud (15%) | ⭐ Común |
| **Lobo** | 15-35 | Poción (30%), Botas Cuero (10%) | ⭐⭐ Normal |
| **Goblin** | 20-50 | Poción (25%), Daga (8%) | ⭐⭐ Normal |
| **Skeleton** | 10-25 | Poción (20%), Escudo Huesos (12%), Anillo (5%) | ⭐⭐⭐ Poco Común |
| **Bandido** | 30-70 | Pociones (35%/20%), Espada (15%), Botas (12%) | ⭐⭐⭐ Poco Común |
| **Orco** | 35-80 | Poción (40%), Armadura (20%), Hacha (15%) | ⭐⭐⭐ Poco Común |
| **Mago Oscuro** | 50-110 | Poción Maná (50%), Anillo (18%), Grimorio (10%) | ⭐⭐⭐⭐ Raro |
| **Dragón** | 100-250 | Pociones (60%/50%), Garra (25%), Escamas (20%), Anillo Fuego (15%) | ⭐⭐⭐⭐⭐ Épico |

#### 3. **Generación Automática**
Al ganar una batalla, el `BattleManager` automáticamente:
1. Calcula XP total
2. Genera oro de cada enemigo
3. Genera items basados en probabilidades
4. Muestra todo en el log de batalla

---

## 🗺️ QUESTS DISPONIBLES

### 1. **Plaga de Slimes** 
- **Objetivo:** Eliminar 5 Slimes
- **Recompensas:** 150 XP, 50 Oro, Poción de Salud
- **ID:** `SLAY_SLIMES`

### 2. **Suministros para el Templo**
- **Objetivo:** Recoger 3 Pociones de Salud
- **Recompensas:** 100 XP, 30 Oro
- **ID:** `COLLECT_POTIONS`

### 3. **Lobos Salvajes**
- **Objetivo:** Eliminar 3 Lobos
- **Recompensas:** 200 XP, 75 Oro, Espada de Hierro
- **ID:** `HUNT_WOLVES`

### 4. **Campamento Goblin**
- **Objetivo:** Eliminar 7 Goblins
- **Recompensas:** 300 XP, 100 Oro, Peto de Acero, Poción
- **ID:** `GOBLIN_CAMP`

### 5. **Camino del Guerrero**
- **Objetivo:** Alcanzar nivel 5
- **Recompensas:** 500 XP, 200 Oro, Espada del Veterano, Poción Maná Grande
- **ID:** `REACH_LEVEL_5`

### 6. **Amenaza No-Muerta**
- **Objetivo:** Eliminar 4 Esqueletos
- **Recompensas:** 250 XP, 80 Oro, Escudo de Roble
- **ID:** `UNDEAD_THREAT`

### 7. **Maestro de Combate**
- **Objetivo:** Ganar 10 batallas
- **Recompensas:** 400 XP, 150 Oro, Armadura de Campeón, Pociones
- **ID:** `WIN_BATTLES`

---

## 🔧 INTEGRACIÓN CON EL JUEGO

### En BattleManager
```java
// Cuando ganas una batalla:
1. Calcula XP y lo otorga al jugador
2. Genera oro de todos los enemigos derrotados
3. Genera items aleatorios basados en LootTable
4. **AÑADE ITEMS AL INVENTARIO DEL JUGADOR** mediante player.getInventory().add(item)
5. Notifica al QuestManager de la victoria
6. Muestra todo en el log de mensajes con "=== ITEMS DROPEADOS ==="
```

### Ejemplo Real de Drop:
```java
// En BattleManager.processBattleVictory():
for (Enemy enemy : enemies) {
    List<Item> loot = enemy.generateLoot();
    for (Item item : loot) {
        player.getInventory().add(item);  // ✅ REALMENTE AÑADE AL INVENTARIO
        battleLog.append("  ✓ " + item.getName() + "\n");
    }
}
```

### En QuestManager
```java
// Métodos de notificación disponibles:
questManager.notifyEnemyKilled("Lobo");         // Llamado en BattleManager
questManager.notifyItemCollected("Poción de Salud");  // Llamado en Inventory
questManager.notifyLevelReached(5);             // Llamado en Character.gainXp()
questManager.notifyBattleWon();                 // Llamado en BattleManager
```

### Flujo de Quest
```
1. Jugador acepta quest → startQuest("HUNT_WOLVES")
2. Jugador mata lobo → notifyEnemyKilled("Lobo")
3. Quest actualiza progreso (1/3)
4. Cuando completa objetivo → Quest se completa automáticamente
5. Jugador recibe recompensas (XP, Oro, Items)
```

---

## 🎨 MEJORAS DE UI

### Panel de Quests
- **Tabla simplificada**: Removido el ID confuso, añadida columna de Progreso (%)
- **Detalles ampliados**: Fuente más grande (14pt), área de 200px
- **Formato mejorado**: Símbolos visuales (📜 título, 📊 estado, 🎯 objetivos, 🎁 recompensas)
- **Progreso visible**: Cada objetivo muestra [2/5] Eliminar 5 Slimes
- **Botones mejorados**: Tamaño 180x45, BasicButtonUI para colores correctos en Windows

### Panel de Inventario
- **Botones visibles**: BasicButtonUI soluciona problema de colores en Windows
- **Tamaño aumentado**: 140x45 píxeles
- **Hover effects**: 4 estados (normal, hover, pressed, released)

---

## 📊 CLASES NUEVAS

### `ItemDrop.java`
Representa un item que puede ser dropeado con su probabilidad.

### `LootTable.java`
Tabla completa de drops de un enemigo (oro + items).

### `QuestObjective.java`
Representa un objetivo individual dentro de una quest.

---

## 🎮 CÓMO USAR

### Para Añadir una Nueva Quest:

```java
Quest newQuest = new Quest(
    "MY_QUEST_ID",
    "Título de la Quest",
    "Descripción detallada",
    300,  // XP reward
    150   // Gold reward
);

newQuest.addObjective(new QuestObjective(
    QuestObjective.ObjectiveType.KILL_ENEMY,
    "Nombre Enemigo",
    5,
    "Eliminar 5 Nombre Enemigo"
));

newQuest.addItemReward(new Sword("...", "...", 10));
questDatabase.put(newQuest.getId(), newQuest);
```

### Para Configurar Drops de un Enemigo:

```java
LootTable loot = new LootTable();
loot.setGoldReward(20, 30);  // 20-50 oro
loot.addDrop(new HealthPotion(), 0.25);
loot.addDrop(new Sword("...", "...", 8), 0.10);
enemy.setLootTable(loot);
```

---

## ✨ PRÓXIMAS MEJORAS SUGERIDAS

1. **UI de Quests**: Panel visual mostrando progreso de objetivos
2. **Sistema de Oro**: Añadir gold al inventario del jugador
3. **Auto-añadir Items**: Los drops se añaden automáticamente al inventario
4. **Quests Dinámicas**: Proceduralmente generadas
5. **Quests Encadenadas**: Una quest desbloquea otra
6. **Diálogos de NPCs**: Para iniciar/completar quests

---

## 🎯 BENEFICIOS

✅ **Progreso Claro**: El jugador ve exactamente qué falta para completar cada quest
✅ **Recompensas Variadas**: No solo XP, también items y oro
✅ **Drops Emocionantes**: Cada enemigo puede dropear items útiles
✅ **Rejugabilidad**: Sistema de drops aleatorio hace cada batalla única
✅ **Escalado**: Fácil añadir nuevas quests y configurar loot tables
✅ **Extensible**: Arquitectura lista para NPCs, diálogos, y más

---

**¡El sistema está completamente funcional y listo para usar!** 🚀
