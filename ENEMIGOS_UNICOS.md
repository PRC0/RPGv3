# 🎮 Enemigos Únicos - RPGv3

## 📋 Resumen de Clases de Enemigos

Cada enemigo ahora tiene su **propia clase** con habilidades especiales únicas, comportamientos específicos y mecánicas especiales.

---

## 🐉 **CACHORRO DE DRAGÓN** - Mini-Boss Legendario

**Clase**: `DragonWhelp.java`

### 📊 Estadísticas
- **HP**: 120 (más alto)
- **Ataque**: 12
- **Magia**: 10
- **Maná**: 50
- **XP**: 150
- **IA**: MageAI (inteligente)

### ✨ Habilidades Especiales

**🔥 [ALIENTO DE FUEGO]**
- **Daño**: Magia × 2.5 = 25 de daño base
- **Animación**: 
  - "🔥 Cachorro de Dragón inhala profundamente..."
  - "¡¡¡ALIENTO DE FUEGO!!! 🔥🔥🔥"

**🩹 Regeneración Pasiva**
- Regenera **5 HP por turno** automáticamente
- Mensaje: "🩹 Cachorro de Dragón regenera 5 HP (escamas dracónicas)"
- Se activa ANTES de atacar cada turno

**⚡ Modo Furia** (HP < 30%)
- **Trigger**: Cuando la vida baja de 30%
- **Efecto**: +5 Ataque, +5 Magia permanente
- **Mensaje**: "⚡ ¡¡Cachorro de Dragón entra en MODO FURIA!! ⚡"
- Solo se activa UNA vez por batalla

### 🎯 Estrategia de Batalla
- Fase 1 (HP > 30%): Ataques mágicos constantes + regeneración
- Fase 2 (HP < 30%): MODO FURIA + defensa cuando crítico
- Peligro extremo cuando usa Aliento de Fuego

---

## 🧙 **MAGO OSCURO** - Hechicero de las Sombras

**Clase**: `DarkMage.java`

### 📊 Estadísticas
- **HP**: 60
- **Ataque**: 8
- **Magia**: 15 (segundo más alto)
- **Maná**: 40
- **XP**: 80
- **IA**: MageAI

### ✨ Habilidades Especiales

**⚫ [DRENAJE DE VIDA]**
- **Daño**: Magia × 1.8 = 27 de daño base
- **Curación**: 50% del daño infligido (13 HP)
- **Animación**:
  - "🌑 Mago Oscuro invoca magia prohibida..."
  - "⚫ ¡DRENAJE DE VIDA! ⚫"
  - "💜 Mago Oscuro absorbe X HP de energía vital!"

**🛡️ Barrera Mágica Mejorada**
- Cuando HP < 30%, usa defensa más frecuentemente
- Se autoprotege con barreras arcanas

### 🎯 Estrategia de Batalla
- Combina daño alto con autocuración
- Difícil de matar por el drenaje de vida
- Prioriza eliminarlo rápido antes que use múltiples drains

---

## 👹 **ORCO GUERRERO** - Tanque Imparable

**Clase**: `OrcWarrior.java`

### 📊 Estadísticas
- **HP**: 80 (segundo más alto)
- **Ataque**: 6
- **Defensa**: 12 (más alta)
- **XP**: 60
- **IA**: DefensiveAI

### ✨ Habilidades Especiales

**🗡️ [CARGA SALVAJE]**
- **Daño**: Ataque × 2.0 = 12 de daño base
- **Efecto**: Ignora 50% de la defensa del objetivo (futuro)
- **Animación**:
  - "💪 Orco Guerrero carga con toda su fuerza..."
  - "🗡️ ¡CARGA SALVAJE! 🗡️"
  - "💥 ¡Impacto devastador!"

**🛡️ Armadura Natural**
- **Defensa Base**: +12 (armadura gruesa de orco)
- Muy resistente a daño físico
- Alterna defensa inteligentemente

### 🎯 Estrategia de Batalla
- Batalla larga y de desgaste
- Usa defensa 1 de cada 2 turnos (HP > 50%)
- Usa defensa 2 de cada 3 turnos (HP < 50%)
- Requiere paciencia y habilidades especiales

---

## 🏹 **BANDIDO** - Francotirador Mortal

**Clase**: `Bandit.java`

### 📊 Estadísticas
- **HP**: 55
- **Ataque**: 5
- **XP**: 45
- **IA**: EvasiveAI (impredecible)

### ✨ Habilidades Especiales

**⚡ [DISPARO CRÍTICO]**
- **Daño Normal**: Ataque × 2.2 = 11 de daño base
- **Daño Crítico**: Ataque × 3.0 = 15 de daño base
- **Probabilidad Crítico**: 30%
- **Animación**:
  - "🎯 Bandido apunta cuidadosamente..."
  - "🏹 ¡DISPARO CRÍTICO! 🏹"
  - (Si crit) "⚡ ¡¡CRÍTICO!! ⚡"

**🤸 Agilidad Extrema**
- IA Evasiva: 50% ataque, 30% esquiva, 20% especial
- Comportamiento completamente impredecible
- Difícil de planear contra él

### 🎯 Estrategia de Batalla
- Puede hacer daño explosivo con críticos
- Sus esquivas prolongan la batalla
- Elimínalo rápido antes de que tenga suerte

---

## 🐺 **LOBO SALVAJE** - Depredador Bestial

**Clase**: `Wolf.java`

### 📊 Estadísticas
- **HP**: 45
- **Ataque**: 4
- **XP**: 35
- **IA**: AggressiveAI

### ✨ Habilidades Especiales

**🦷 [MORDIDA SALVAJE]**
- **Daño Base**: Ataque × 1.8 = 7 de daño base
- **Daño vs Heridos**: Ataque × 1.8 × 1.5 = 10 de daño (si objetivo HP < 50%)
- **Animación**:
  - "🐺 Lobo Salvaje gruñe amenazadoramente..."
  - (Si objetivo herido) "🩸 ¡Instinto de cazador activado!"
  - "🦷 ¡MORDIDA SALVAJE! 🦷"

**🩸 Instinto de Cazador**
- +50% daño contra enemigos con HP < 50%
- Más peligroso cuando estás herido
- "Huele la sangre" y ataca más fuerte

### 🎯 Estrategia de Batalla
- Mantén tu HP alto (> 50%) para reducir su daño
- Matalo rápido antes de que te debilites
- Su IA agresiva lo hace predecible pero letal

---

## 💀 **SKELETON** - No-Muerto Inquebrantable

**Clase**: `Skeleton.java`

### 📊 Estadísticas
- **HP**: 40
- **Ataque**: 7
- **XP**: 40
- **IA**: AggressiveAI

### ✨ Habilidades Especiales

**☠️ [FLECHA ENVENENADA]**
- **Daño**: Ataque × 1.7 = 11 de daño base
- **Efecto**: Veneno (futuro: 3 daño × 2 turnos)
- **Animación**:
  - "💀 Skeleton prepara una flecha oscura..."
  - "☠️ ¡FLECHA ENVENENADA! ☠️"
  - "🦠 El veneno se infiltra en las venas..."

**🦴 Estructura Ósea**
- **Esquiva Pasiva**: 20% probabilidad de evitar CUALQUIER ataque
- **Mensaje**: "🦴 ¡La flecha atraviesa entre los huesos sin hacer daño!"
- Se aplica a TODOS los ataques recibidos, no solo flechas

**💀 Ya Está Muerto**
- No siente dolor
- Resistencia innata al daño

### 🎯 Estrategia de Batalla
- Ten paciencia con sus esquivas
- 1 de cada 5 ataques fallará por su estructura
- Su IA agresiva lo hace atacar cada turno

---

## 👺 **GOBLIN** - Tramposo Astuto

**Clase**: `Goblin.java`

### 📊 Estadísticas
- **HP**: 35
- **Ataque**: 5
- **XP**: 30
- **IA**: EvasiveAI

### ✨ Habilidades Especiales

**🗡️ [ATAQUE SUCIO]**
- **Daño**: Ataque × 1.5 = 7 de daño base
- **Truco Sucio**: 40% probabilidad de efecto debilitante
- **Animación**:
  - "👹 Goblin se prepara para hacer trampa..."
  - "🗡️ ¡ATAQUE SUCIO! 🗡️"
  - (40% chance) "💨 Goblin lanza arena a los ojos! ¡Tu visión está borrosa!"

**🤹 Comportamiento Impredecible**
- IA Evasiva: Nunca sabes qué hará
- Puede atacar, esquivar o usar especial aleatoriamente
- Frustrante pero débil

### 🎯 Estrategia de Batalla
- Enemigo de nivel bajo
- Su imprevisibilidad es su única ventaja
- Fácil de derrotar con paciencia

---

## 🟢 **SLIME** - Gelatinoso Adorable

**Clase**: `Slime.java`

### 📊 Estadísticas
- **HP**: 20 (más bajo)
- **Ataque**: 3 (más bajo)
- **Defensa**: 2 (cuerpo gelatinoso)
- **XP**: 15
- **IA**: AggressiveAI

### ✨ Habilidades Especiales

**💧 [SALPICADURA ÁCIDA]**
- **Daño**: Ataque × 1.3 = 3 de daño base
- **Tipo**: Ácido (ignora defensa física - futuro)
- **Animación**:
  - "🟢 Slime rebota amenazadoramente..."
  - "💧 ¡SALPICADURA ÁCIDA! 💧"
  - "🧪 ¡El ácido quema!"

**🟢 Cuerpo Gelatinoso**
- **Defensa +2**: Absorbe impactos
- **Mensaje Especial**: "🟢 El slime tiembla pero se mantiene cohesionado..."
- Resistente a ataques contundentes

**😊 Adorablemente Débil**
- Enemigo principiante perfecto
- Fácil de derrotar
- Poca amenaza

### 🎯 Estrategia de Batalla
- Tutorial viviente
- Úsalo para practicar mecánicas
- Perfecto para farmear XP temprano

---

## 📊 Comparativa de Enemigos

| Enemigo | HP | Ataque | Magia | Defensa | XP | Dificultad | Habilidad Única |
|---------|----|----|-------|---------|-----|------------|----------------|
| 🟢 Slime | 20 | 3 | 0 | 2 | 15 | ⭐ | Cuerpo Gelatinoso |
| 👺 Goblin | 35 | 5 | 0 | 0 | 30 | ⭐⭐ | Ataque Sucio |
| 🐺 Lobo | 45 | 4 | 0 | 0 | 35 | ⭐⭐ | Instinto Cazador |
| 💀 Skeleton | 40 | 7 | 0 | 0 | 40 | ⭐⭐⭐ | Esquiva 20% |
| 🏹 Bandido | 55 | 5 | 0 | 0 | 45 | ⭐⭐⭐ | Crítico 30% |
| 👹 Orco | 80 | 6 | 0 | 12 | 60 | ⭐⭐⭐⭐ | Tanque Defensivo |
| 🧙 Mago | 60 | 8 | 15 | 0 | 80 | ⭐⭐⭐⭐ | Drenaje Vida |
| 🐉 Dragón | 120 | 12 | 10 | 0 | 150 | ⭐⭐⭐⭐⭐ | Regeneración + Furia |

---

## 🎯 Orden Recomendado de Combate

### Para Principiantes (Nivel 1-2)
1. 🟢 Slime - Tutorial
2. 👺 Goblin - Aprende evasión
3. 🐺 Lobo - Gestión de HP

### Nivel Intermedio (Nivel 3-5)
4. 💀 Skeleton - Lidiar con esquivas
5. 🏹 Bandido - Aprender a defenderse

### Nivel Avanzado (Nivel 6+)
6. 👹 Orco Guerrero - Batalla de desgaste
7. 🧙 Mago Oscuro - Daño explosivo

### BOSS (Nivel 8+)
8. 🐉 Cachorro de Dragón - ¡Desafío final!

---

## 🔥 Mejoras Implementadas

### ✅ Clases Únicas Creadas
- 8 archivos .java nuevos en `rpg/core/`
- Cada enemigo hereda de `Enemy.java`
- Comportamientos completamente únicos

### ✅ Habilidades Especiales Personalizadas
- Cada enemigo sobreescribe `specialAbility()`
- Mensajes con emojis para efectos visuales
- Eventos disparados a la GUI

### ✅ Mecánicas Pasivas
- **Dragón**: Regeneración automática
- **Skeleton**: Esquiva probabilística
- **Lobo**: Daño condicional
- **Mago**: Defensa inteligente

### ✅ Balanceo Mejorado
- Stats ajustados para dificultad progresiva
- IAs específicas por tipo de enemigo
- Curva de XP equilibrada

### ✅ Mensajes Narrativos
- Cada habilidad tiene descripción única
- Emojis para identificación visual rápida
- Feedback claro de efectos especiales

---

## 🎮 Cómo Probar

1. **Inicia el juego**
2. **Ve a "Explorar"**
3. **Buscar Enemigos**
4. **Observa los diferentes comportamientos**:
   - Orco se defiende constantemente
   - Dragón regenera HP cada turno
   - Skeleton esquiva ataques
   - Mago usa magia hasta quedarse sin maná
   - Bandido hace críticos sorpresa
   - Lobo golpea más fuerte cuando estás herido

---

## 📝 Código de Ejemplo

```java
// Ejemplo: Habilidad del Dragón
@Override
public void specialAbility(Character target) {
    String message = "🔥 " + this.name + " inhala profundamente...";
    GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, message);
    
    int damage = (int) (this.baseMagic * 2.5); // 25 de daño!
    
    String fireMessage = "¡¡¡ALIENTO DE FUEGO!!! 🔥🔥🔥";
    GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, fireMessage);
    
    target.receiveDamage(damage);
}
```

---

**¡Cada batalla ahora es única y emocionante! 🎉**
