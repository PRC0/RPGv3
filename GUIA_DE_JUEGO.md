# 🎮 Guía de Juego - RPGv3

## 📖 Tabla de Contenidos
1. [Inicio del Juego](#inicio-del-juego)
2. [Interfaz del Juego](#interfaz-del-juego)
3. [Clases de Personaje](#clases-de-personaje)
4. [Sistema de Combate](#sistema-de-combate)
5. [Inventario y Equipamiento](#inventario-y-equipamiento)
6. [Sistema de Experiencia](#sistema-de-experiencia)
7. [Tipos de Enemigos](#tipos-de-enemigos)
8. [Misiones](#misiones)
9. [Guardar y Cargar](#guardar-y-cargar)
10. [Consejos y Estrategias](#consejos-y-estrategias)

---

## 🎬 Inicio del Juego

Al iniciar el juego, aparecerá un diálogo de creación de personaje:

1. **Nombre**: Elige el nombre de tu héroe
2. **Clase**: Selecciona entre Warrior, Mage o Archer
3. Haz clic en **OK** para comenzar

### ⚔️ Clases Iniciales

| Clase | HP Inicial | MP Inicial | Ataque | Magia | Estilo |
|-------|------------|------------|---------|--------|---------|
| **Warrior** | 120 | 20 | 12 | 5 | Cuerpo a cuerpo |
| **Mage** | 80 | 50 | 6 | 15 | Magia |
| **Archer** | 100 | 30 | 10 | 8 | A distancia |

---

## 🖥️ Interfaz del Juego

### Distribución de la Pantalla

```
┌─────────────────────────────────────────┐
│  BARRA SUPERIOR                         │
│  HP: ████████░░ 80/100                  │
│  MP: ██████░░░░ 60/100                  │
│  Nivel: 3 | XP: 150/300 | Oro: 150     │
├──────┬──────────────────────┬───────────┤
│      │                      │           │
│ MENÚ │   PANEL CENTRAL      │  STATS    │
│      │   (Dinámico)         │           │
│      │   - Explorar         │  Ataque   │
│ Exp  │   - Batalla          │  Defensa  │
│ Inv  │   - Inventario       │  Magia    │
│ Mis  │   - Misiones         │           │
│ Stat │   - Estadísticas     │  Equip    │
│ ---  │                      │  Arma     │
│ Save │                      │  Armadura │
│ Load │                      │           │
├──────┴──────────────────────┴───────────┤
│  LOG DE EVENTOS                         │
│  [18:22:15] ¡Bienvenido, Hero!         │
│  [18:22:30] Encontraste un Slime!      │
└─────────────────────────────────────────┘
```

### 📌 Panel Superior (Barra de Estado)
- **HP**: Vida actual / Vida máxima
- **MP**: Maná actual / Maná máximo
- **Nivel**: Nivel actual del personaje
- **XP**: Experiencia actual / Experiencia para siguiente nivel
- **Oro**: Cantidad de oro (futuro)

### 📌 Menú Lateral Izquierdo
- **Explorar**: Buscar enemigos o descansar
- **Inventario**: Gestionar items y equipamiento
- **Misiones**: Ver y completar misiones
- **Estadísticas**: Ver stats completos del personaje
- **Guardar**: Guardar el progreso actual
- **Cargar**: Cargar una partida guardada
- **Salir**: Cerrar el juego

### 📌 Panel Derecho (Detalles del Personaje)
- Estadísticas actuales
- Equipamiento actual
- Bonificaciones activas

### 📌 Panel Inferior (Log de Eventos)
- Mensajes del sistema
- Notificaciones importantes
- Resultados de combate

---

## ⚔️ Clases de Personaje

### 🛡️ **WARRIOR (Guerrero)**

**Estilo de Juego**: Tanque cuerpo a cuerpo

**Estadísticas Base**:
- HP: 120 (más alto)
- MP: 20 (bajo)
- Ataque: 12
- Magia: 5
- Defensa: Alta

**Habilidad Especial**: **Carga Brutal** (20 MP)
- Daño: Ataque × 1.5
- Mejor para: Eliminar enemigos individuales fuertes

**Estrategia**:
- Aguanta mucho daño
- Excelente para peleas largas
- Usa defensa frecuentemente
- Gestiona MP cuidadosamente

---

### 🔮 **MAGE (Mago)**

**Estilo de Juego**: Daño mágico a distancia

**Estadísticas Base**:
- HP: 80 (bajo)
- MP: 50 (más alto)
- Ataque: 6
- Magia: 15 (más alto)
- Defensa: Baja

**Habilidad Especial**: **Bola de Fuego** (20 MP)
- Daño: Magia × 2
- Mejor para: Destruir múltiples enemigos

**Estrategia**:
- Ataca desde lejos
- Usa pociones de maná
- Evita el combate directo
- Elimina enemigos rápido

---

### 🏹 **ARCHER (Arquero)**

**Estilo de Juego**: Balanceado a distancia

**Estadísticas Base**:
- HP: 100 (medio)
- MP: 30 (medio)
- Ataque: 10
- Magia: 8
- Defensa: Media

**Habilidad Especial**: **Disparo Preciso** (20 MP)
- Daño: (Ataque + Magia) × 1.2
- Mejor para: Versatilidad

**Estrategia**:
- Balance entre daño y supervivencia
- Adapta a la situación
- Usa todas las tácticas
- Buen para principiantes

---

## ⚔️ Sistema de Combate

### 🎯 Cómo Funciona el Combate

1. **Iniciar Batalla**: Ve a "Explorar" → "Buscar Enemigos"
2. **Se abre el Panel de Batalla** mostrando:
   - Tu HP/MP
   - HP del enemigo
   - 4 botones de acción
   - Log de batalla

### 🔘 Acciones de Combate

#### ⚔️ **ATACAR** (Ataque Básico)
- **Costo**: 0 MP
- **Efecto**: Daño basado en tu stat de Ataque
- **Cuándo usar**: Siempre disponible, ahorra MP

**Ejemplo de mensaje**:
```
--- Turno del héroe ---
Hero ataca a Slime por 10 de daño!
```

#### ✦ **ESPECIAL** (Habilidad de Clase)
- **Costo**: 20 MP
- **Efecto**: Daño aumentado según tu clase
- **Cuándo usar**: Contra enemigos fuertes o grupos

**Ejemplo de mensaje**:
```
--- Turno del héroe ---
Hero usa [Carga Brutal] contra Orco por 18 de daño!
```

#### 🛡️ **DEFENDER**
- **Costo**: 0 MP
- **Efecto**: Reduce el daño recibido en 50% durante 1 turno
- **Cuándo usar**: Cuando anticipas un ataque fuerte

**Ejemplo de mensaje**:
```
--- Turno del héroe ---
>>> Te pones en guardia defensiva
Defensa aumentada temporalmente

--- Turno del enemigo ---
Hero se defiende y reduce el daño de 12 a 6!
```

#### 🏃 **HUIR**
- **Costo**: 0 MP
- **Probabilidad**: 50% de éxito
- **Efecto**: Escapa de la batalla (sin recompensas)
- **Cuándo usar**: Cuando estás en peligro

**Ejemplo de mensaje**:
```
--- Turno del héroe ---
>>> Intentas huir...
¡Escapaste con éxito!
```
o
```
>>> Intentas huir...
¡No pudiste escapar!
```

### 📊 Cálculo de Daño

#### Daño que TÚ haces:
```
Ataque Normal  = Ataque Base
Habilidad      = (Ataque o Magia) × Multiplicador de Clase
```

#### Daño que RECIBES:
```
Daño Inicial   = Ataque del Enemigo
Daño Final     = Daño Inicial - Tu Defensa (mínimo 1)

Si te defiendes:
Daño Final     = Daño Final × 0.5
```

**Ejemplo con armadura**:
```
--- Turno del enemigo ---
Orco Guerrero ataca a Hero por 12 de daño!
Hero recibe 9 de daño (bloqueó 3 con armadura)!
```

### 🎲 Tipos de Encuentros

El juego tiene 4 tipos de encuentros aleatorios:

1. **Individual** (40% probabilidad)
   - 1 enemigo aleatorio
   - Dificultad: Variable

2. **Patrulla** (20% probabilidad)
   - 2 enemigos aleatorios
   - Dificultad: Media

3. **Horda** (25% probabilidad)
   - 3 enemigos débiles (Slime, Goblin, Lobo)
   - Dificultad: Media-Alta

4. **Peligroso** (15% probabilidad)
   - Cachorro de Dragón + Mago Oscuro
   - Dificultad: Alta
   - Recompensa: 230 XP total

---

## 🎒 Inventario y Equipamiento

### 📦 Panel de Inventario

**Cómo acceder**: Menú lateral → "Inventario"

### 🗡️ Tipos de Items

#### **ARMAS**
- **Sword (Espada)**: +5 Ataque
- **Shield (Escudo)**: +5 Defensa

#### **ARMADURAS**
- **Chestplate (Pechera)**: +3 Defensa, +10 HP
- **Boots (Botas)**: +3 Defensa, +15 HP

#### **ACCESORIOS**
- **Ring (Anillo)**: +2 Ataque, +3 Magia, +1 Defensa

#### **CONSUMIBLES**
- **Health Potion (Poción de Vida)**: Restaura 50 HP
- **Mana Potion (Poción de Maná)**: Restaura 30 MP

### ⚙️ Cómo Equipar Items

1. Ve a **Inventario**
2. Selecciona un item equipable
3. Haz clic en **"Equipar"**
4. El item anterior se guarda en el inventario
5. Tus stats se actualizan automáticamente

**Mensaje de equipamiento**:
```
Has equipado: Espada de Hierro
Ataque +5
```

### 💊 Cómo Usar Consumibles

1. Ve a **Inventario**
2. Selecciona la poción
3. Haz clic en **"Usar"**
4. El item se consume y desaparece

**Mensaje al usar**:
```
Usaste: Health Potion
HP restaurado: +50
```

### 🎁 Items Decorados (Especiales)

El juego incluye items encantados usando el patrón Decorator:

- **Enchanted Weapon (Arma Encantada)**: Arma + Bonus Mágico adicional
- **Reinforced Armor (Armadura Reforzada)**: Armadura + Defensa extra

---

## ⭐ Sistema de Experiencia

### 📈 Cómo Funciona

- **Nivel Inicial**: 1
- **XP Base para subir**: 100
- **Crecimiento**: × 1.5 por nivel
- **Nivel Máximo**: Sin límite

### 🎯 XP por Enemigo

| Enemigo | XP Otorgado |
|---------|-------------|
| Slime | 15 XP |
| Goblin | 30 XP |
| Lobo Salvaje | 35 XP |
| Skeleton | 40 XP |
| Bandido | 45 XP |
| Orc Guerrero | 60 XP |
| Mago Oscuro | 80 XP |
| Cachorro de Dragón | 150 XP |

### 🆙 Beneficios al Subir de Nivel

Cada nivel aumenta:
- **HP Máximo**: +10
- **Ataque**: +2
- **Magia**: +2
- **HP Actual**: Restaurado al máximo
- **MP Actual**: Restaurado al máximo

**Mensaje de Level Up**:
```
=== ¡LEVEL UP! ===
¡Has alcanzado el nivel 2!
HP Máximo: 120 → 130
Ataque: 12 → 14
Magia: 5 → 7
```

### 📊 Tabla de XP por Nivel

| Nivel | XP Necesario | XP Acumulado |
|-------|--------------|--------------|
| 1→2 | 100 | 100 |
| 2→3 | 150 | 250 |
| 3→4 | 225 | 475 |
| 4→5 | 338 | 813 |
| 5→6 | 507 | 1,320 |

---

## 👹 Tipos de Enemigos

### 🟢 **SLIME** (Principiante)
- **HP**: 20
- **Ataque**: 3
- **Defensa**: 2
- **XP**: 15
- **Tipo**: Melee
- **Dificultad**: ⭐
- **Estrategia**: Cualquier clase puede derrotarlo fácilmente

---

### 🟢 **GOBLIN** (Fácil)
- **HP**: 35
- **Ataque**: 5
- **Defensa**: 5
- **XP**: 30
- **Tipo**: Melee
- **Dificultad**: ⭐⭐
- **Estrategia**: Usa ataques básicos, guarda MP

---

### 🟡 **LOBO SALVAJE** (Medio)
- **HP**: 45
- **Ataque**: 4
- **Defensa**: 9
- **XP**: 35
- **Tipo**: Melee rápido
- **Dificultad**: ⭐⭐
- **Estrategia**: Ataca rápido, puede hacer varios ataques

---

### 🟡 **SKELETON** (Medio)
- **HP**: 40
- **Ataque**: 7
- **Defensa**: 6
- **XP**: 40
- **Tipo**: Melee
- **Dificultad**: ⭐⭐⭐
- **Estrategia**: Golpea duro, usa defensa si es necesario

---

### 🟡 **BANDIDO** (Medio)
- **HP**: 55
- **Ataque**: 5
- **Defensa**: 8
- **XP**: 45
- **Tipo**: Ranged
- **Dificultad**: ⭐⭐⭐
- **Estrategia**: Ataca desde lejos, tiene HP moderado

---

### 🔴 **ORC GUERRERO** (Difícil)
- **HP**: 80
- **Ataque**: 6
- **Defensa**: 12
- **XP**: 60
- **Tipo**: Melee tanque
- **Dificultad**: ⭐⭐⭐⭐
- **Estrategia**: Mucha defensa, batalla larga, usa habilidades especiales

---

### 🔴 **MAGO OSCURO** (Difícil)
- **HP**: 60
- **Ataque Físico**: 8
- **Ataque Mágico**: 15
- **XP**: 80
- **Tipo**: Magia
- **Dificultad**: ⭐⭐⭐⭐
- **Estrategia**: Alto daño mágico, elimínalo rápido

---

### 🔴 **CACHORRO DE DRAGÓN** (Mini-Boss)
- **HP**: 120
- **Ataque Físico**: 12
- **Ataque Mágico**: 10
- **XP**: 150
- **Tipo**: Híbrido (Magia + Melee)
- **Dificultad**: ⭐⭐⭐⭐⭐
- **Estrategia**: El enemigo más fuerte, usa todas tus herramientas

---

## 📜 Misiones

### 🎯 Panel de Misiones

**Cómo acceder**: Menú lateral → "Misiones"

### 📋 Misiones Disponibles

#### 🔰 **Misión: "Derrota 5 Enemigos"**
- **Objetivo**: Derrota a 5 enemigos de cualquier tipo
- **Progreso**: Se actualiza automáticamente
- **Recompensa**: 100 XP
- **Estado**: Activa / Completada

**Cómo completar**:
1. Ve a "Explorar" → "Buscar Enemigos"
2. Derrota enemigos en combate
3. El contador se actualiza automáticamente
4. Al llegar a 5/5, recibes la recompensa

---

## 💾 Guardar y Cargar

### 💾 Guardar Partida

1. **Menú lateral** → "Guardar"
2. Se guarda automáticamente en `savegame.dat`
3. Aparece confirmación

**Se guarda**:
- Nivel y XP actuales
- HP y MP actuales
- Inventario completo
- Equipamiento actual
- Progreso de misiones

### 📂 Cargar Partida

1. **Menú lateral** → "Cargar"
2. Se carga automáticamente desde `savegame.dat`
3. Todo se restaura

**⚠️ IMPORTANTE**: Al cargar se sobrescribe el progreso actual sin preguntar.

---

## 💡 Consejos y Estrategias

### 🎯 Para Principiantes

1. **Empieza con Archer**: Es la clase más balanceada
2. **Guarda frecuentemente**: Especialmente después de level ups
3. **Usa pociones antes de morir**: No las acumules
4. **Descansa entre batallas**: Recupera HP gratis
5. **Completa misiones**: Dan XP extra

### ⚔️ Combate Efectivo

1. **Gestiona tu MP**: No desperdicies habilidades en enemigos débiles
2. **Defiéndete estratégicamente**: Contra Orc y Dragón es crucial
3. **Huye si es necesario**: Mejor vivir para pelear otro día
4. **Ataques normales son tus amigos**: 0 MP, siempre disponibles

### 📈 Subir de Nivel Rápido

1. **Busca Dragones**: 150 XP por victoria
2. **Completa misiones**: 100 XP por misión
3. **No huyas**: Pierdes la XP
4. **Grupos de enemigos**: Más XP total

### 🛡️ Equipamiento Óptimo

**Para Warrior**:
- Arma: Espada (+5 Ataque)
- Armadura: Chestplate (+3 Def, +10 HP)
- Extra: Shield para defensa máxima

**Para Mage**:
- Arma: Ring (+3 Magia)
- Armadura: Boots (+HP para sobrevivir)
- Consumibles: Muchas Mana Potions

**Para Archer**:
- Arma: Espada (+5 Ataque)
- Armadura: Chestplate (balance)
- Mix de pociones

### 🎲 Estrategias por Enemigo

**Contra Slimes/Goblins**:
- Ataque normal solo
- Ahorra MP

**Contra Lobos/Skeletons**:
- Usa habilidades si tienes MP
- Defiende si tu HP es bajo

**Contra Orcs**:
- Defiende cada 2 turnos
- Usa habilidades especiales
- Ten pociones listas

**Contra Magos Oscuros**:
- Elimina RÁPIDO
- Usa todas tus habilidades
- No dejes que ataque mucho

**Contra Dragones**:
- Prepárate antes (HP/MP full)
- Alterna ataque y defensa
- Usa pociones sin dudar
- Considera huir si HP < 30%

---

## 🆘 Solución de Problemas

### ❓ "No puedo usar mi habilidad especial"
- **Causa**: No tienes suficiente MP (necesitas 20)
- **Solución**: Usa Mana Potion o descansa

### ❓ "Mis ataques hacen poco daño"
- **Causa**: El enemigo tiene mucha defensa o tu equipo es débil
- **Solución**: Equipa mejor arma o sube de nivel

### ❓ "Muero muy rápido"
- **Causa**: Necesitas más HP o defensa
- **Solución**: 
  - Equipa armadura (Chestplate/Boots)
  - Sube de nivel
  - Usa defensa en combate
  - Ten pociones listas

### ❓ "No encuentro items"
- **Causa**: Los items se agregan al inventario automáticamente tras victoria
- **Solución**: Ve a Inventario para verlos

---

## 🎮 Controles y Navegación

### 🖱️ Controles del Ratón

- **Click Izquierdo**: Seleccionar botones/opciones
- **Hover**: Ver tooltips informativos
- **Scroll**: En el log de batalla y listas

### ⌨️ Atajos de Teclado

_Actualmente no disponibles - Usa el ratón_

---

## 📊 Estadísticas del Personaje

### Ver Stats Completas

**Menú lateral** → "Estadísticas"

Muestra:
- Nivel actual
- XP actual / XP necesario
- HP y MP actuales/máximos
- Ataque (base + bonificaciones)
- Magia (base + bonificaciones)
- Defensa (base + bonificaciones)
- Equipamiento actual
- Bonificaciones activas

---

## 🎯 Objetivos del Juego

### Corto Plazo
1. ✅ Completa la misión "Derrota 5 Enemigos"
2. ✅ Alcanza nivel 3
3. ✅ Consigue tu primer equipo completo
4. ✅ Derrota a un Orc Guerrero

### Mediano Plazo
1. ⏳ Alcanza nivel 5
2. ⏳ Derrota a un Cachorro de Dragón
3. ⏳ Completa 3 misiones
4. ⏳ Ten un set completo de equipo encantado

### Largo Plazo
1. ❌ Alcanza nivel 10
2. ❌ Derrota 100 enemigos totales
3. ❌ Colecciona todos los tipos de items
4. ❌ Domina las tres clases

---

## 🏆 Logros Sugeridos

| Logro | Descripción | Dificultad |
|-------|-------------|------------|
| 🥇 **Primera Sangre** | Derrota tu primer enemigo | ⭐ |
| 🥈 **Cazador Novato** | Derrota 10 enemigos | ⭐⭐ |
| 🥉 **Cazador Experto** | Derrota 50 enemigos | ⭐⭐⭐ |
| 👑 **Matadragones** | Derrota un Cachorro de Dragón | ⭐⭐⭐⭐ |
| 💎 **Coleccionista** | Posee uno de cada item | ⭐⭐⭐ |
| ⚔️ **Maestro del Combate** | Gana sin recibir daño | ⭐⭐⭐⭐⭐ |
| 🛡️ **Muralla Humana** | Bloquea 100 de daño con defensa | ⭐⭐⭐ |
| ✨ **Mago Supremo** | Usa 50 habilidades especiales | ⭐⭐⭐ |

---

## 📞 Créditos y Ayuda

**Proyecto**: RPGv3 - Sistema de Combate por Turnos  
**Versión**: 1.2.0  
**Java**: 17  
**Framework**: Swing GUI  

**Patrones de Diseño Implementados**:
- Singleton
- Factory
- Builder
- Strategy
- Observer
- Composite
- Facade
- Decorator

---

**¡Disfruta tu aventura, héroe! ⚔️🛡️✨**
