# ✅ Lista de Verificación - RPGv3 v1.1

## 🎯 Checklist de Pruebas

### 🔹 **1. Nuevos Enemigos (5 tipos)**

#### Prueba de Encuentros Aleatorios
- [ ] Iniciar nuevo juego
- [ ] Ir a "Explorar" → "Buscar Enemigos"
- [ ] Verificar que aparecen enemigos variados:
  - [ ] Slime (viejo)
  - [ ] Goblin (viejo)
  - [ ] Skeleton (viejo)
  - [ ] **Orc** (nuevo) - HP alto, defensa fuerte
  - [ ] **Mago Oscuro** (nuevo) - Ataque mágico
  - [ ] **Lobo Salvaje** (nuevo) - Melee rápido
  - [ ] **Cachorro de Dragón** (nuevo) - Mini-boss
  - [ ] **Bandido** (nuevo) - Ranged

#### Prueba de Tipos de Encuentro
- [ ] Encuentro Individual (1 enemigo) - Debería ser ~40%
- [ ] Patrulla (2 enemigos) - Debería ser ~20%
- [ ] Horda (3 enemigos débiles) - Debería ser ~25%
- [ ] Peligroso (Dragon Whelp + Dark Mage) - Debería ser ~15%

**Buscar 10-15 veces para ver variedad**

---

### 🔹 **2. Nuevos Items**

#### Shield (Escudo)
- [ ] Obtener escudo (agregar manualmente si es necesario)
- [ ] Equipar en slot WEAPON
- [ ] Verificar que aumenta Defensa
- [ ] Verificar en panel de Stats

#### Boots (Botas)
- [ ] Obtener botas
- [ ] Equipar en slot CHEST
- [ ] Verificar que aumenta Defensa + HP
- [ ] Verificar estadísticas actualizadas

#### Ring (Anillo)
- [ ] Obtener anillo
- [ ] Equipar en slot WEAPON
- [ ] Verificar bonus de Ataque, Magia y Defensa
- [ ] Verificar stats múltiples

#### ManaPotion (Poción de Maná)
- [ ] Obtener poción de maná
- [ ] Usar habilidad especial hasta gastar MP
- [ ] Usar ManaPotion
- [ ] Verificar que restaura 30 MP
- [ ] Verificar que no excede MP máximo
- [ ] Verificar que se consume del inventario

---

### 🔹 **3. GameConstants**

#### Verificar Constantes en Combate
- [ ] Defender en batalla
- [ ] Verificar que reduce daño en 50% (DEFENSE_DAMAGE_MULTIPLIER)
- [ ] Intentar huir
- [ ] Verificar tasa de éxito ~50% (FLEE_SUCCESS_RATE)

#### Verificar Constantes de Curación
- [ ] Descansar en Explorar
- [ ] Verificar que cura 30 HP (REST_HP_RESTORE)
- [ ] Usar HealthPotion
- [ ] Verificar que cura 50 HP (HEALTH_POTION_RESTORE)

#### Verificar XP de Enemigos
- [ ] Derrotar Slime → Debería dar 15 XP
- [ ] Derrotar Goblin → 30 XP
- [ ] Derrotar Lobo → 35 XP
- [ ] Derrotar Skeleton → 40 XP
- [ ] Derrotar Bandido → 45 XP
- [ ] Derrotar Orc → 60 XP
- [ ] Derrotar Mago Oscuro → 80 XP
- [ ] Derrotar Cachorro de Dragón → 150 XP

---

### 🔹 **4. Tooltips**

#### Menú de Navegación (LeftMenuPanel)
- [ ] Pasar mouse sobre "Explorar" → Tooltip visible
- [ ] Pasar mouse sobre "Combate" → Tooltip visible
- [ ] Pasar mouse sobre "Inventario" → Tooltip visible
- [ ] Pasar mouse sobre "Misiones" → Tooltip visible
- [ ] Pasar mouse sobre "Estadísticas" → Tooltip visible
- [ ] Pasar mouse sobre "Guardar" → Tooltip visible
- [ ] Pasar mouse sobre "Cargar" → Tooltip visible

#### Batalla (BattlePanel)
- [ ] Tooltip en botón "Atacar"
- [ ] Tooltip en botón "Habilidad Especial"
- [ ] Tooltip en botón "Defender"
- [ ] Tooltip en botón "Huir"
- [ ] Tooltip en área de log de batalla

**Verificar que los tooltips tienen texto descriptivo en español**

---

### 🔹 **5. Sistema de Encuentros Mejorado**

#### Verificar Variedad
- [ ] Buscar enemigos 20 veces
- [ ] Contar cuántos encuentros de cada tipo:
  - Individual: _____ (esperado ~8)
  - Patrulla 2: _____ (esperado ~4)
  - Horda 3: _____ (esperado ~5)
  - Peligroso: _____ (esperado ~3)

#### Verificar Composición
- [ ] Grupo de 3 contiene: Slime + Goblin + Lobo
- [ ] Encuentro peligroso: Dragon Whelp + Dark Mage
- [ ] Enemigos individuales son aleatorios

---

### 🔹 **6. Método setCurrentMana**

#### Pruebas de Validación
- [ ] Reducir MP a 0 con habilidades
- [ ] Usar ManaPotion
- [ ] Verificar que MP no excede el máximo
- [ ] Verificar que UI se actualiza correctamente (barra de MP)
- [ ] Verificar que no puede ser negativo

---

### 🔹 **7. Regresión - Funcionalidades Existentes**

#### Combate
- [ ] Ataque básico funciona
- [ ] Habilidad especial funciona (consume MP)
- [ ] Defender reduce daño
- [ ] Huir termina batalla (50% éxito)
- [ ] Victoria da XP y recompensas

#### Inventario
- [ ] Equipar/Desequipar items antiguos (Sword, Chestplate)
- [ ] Usar HealthPotion
- [ ] Ver descripción de items
- [ ] Items aumentan stats correctamente

#### Sistema de Guardado
- [ ] Guardar partida
- [ ] Cargar partida
- [ ] Stats se mantienen
- [ ] Inventario se mantiene

#### Misiones
- [ ] Ver misiones disponibles
- [ ] Completar "Derrota 5 enemigos"
- [ ] Recibir recompensas

#### Level Up
- [ ] Subir de nivel (ganar 100 XP)
- [ ] Verificar que aumentan stats
- [ ] Verificar notificación visual

---

## 📊 **Resultados de Pruebas**

### Fecha: ___________
### Probador: ___________

| Categoría | Pruebas Pasadas | Pruebas Falladas | Estado |
|-----------|-----------------|------------------|---------|
| Nuevos Enemigos | __/8 | __/8 | ⏳ |
| Nuevos Items | __/4 | __/4 | ⏳ |
| GameConstants | __/8 | __/8 | ⏳ |
| Tooltips | __/12 | __/12 | ⏳ |
| Encuentros | __/6 | __/6 | ⏳ |
| setCurrentMana | __/5 | __/5 | ⏳ |
| Regresión | __/15 | __/15 | ⏳ |
| **TOTAL** | **__/58** | **__/58** | ⏳ |

---

## 🐛 **Bugs Encontrados**

### Bug #1
- **Descripción:** 
- **Pasos para reproducir:** 
- **Severidad:** [ ] Crítico [ ] Alto [ ] Medio [ ] Bajo
- **Estado:** [ ] Abierto [ ] En progreso [ ] Cerrado

### Bug #2
- **Descripción:** 
- **Pasos para reproducir:** 
- **Severidad:** [ ] Crítico [ ] Alto [ ] Medio [ ] Bajo
- **Estado:** [ ] Abierto [ ] En progreso [ ] Cerrado

---

## 💡 **Notas Adicionales**

- 
- 
- 

---

## ✅ **Aprobación Final**

- [ ] Todas las pruebas críticas pasaron
- [ ] No hay bugs bloqueantes
- [ ] Listo para generar UML
- [ ] Listo para empaquetar JAR

**Firma:** ___________  
**Fecha:** ___________

---

*Checklist v1.1 - RPGv3*
