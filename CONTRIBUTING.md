# 🤝 Guía de Contribución

¡Gracias por tu interés en contribuir a RPGv3! Este documento te guiará en el proceso.

## 📋 Tabla de Contenidos

- [Código de Conducta](#código-de-conducta)
- [Cómo Contribuir](#cómo-contribuir)
- [Estándares de Código](#estándares-de-código)
- [Proceso de Pull Request](#proceso-de-pull-request)

## 🌟 Código de Conducta

Este proyecto adhiere a principios de respeto y colaboración. Se espera que todos los contribuidores:

- Sean respetuosos con otros colaboradores
- Acepten críticas constructivas de manera profesional
- Se enfoquen en lo que es mejor para la comunidad
- Muestren empatía hacia otros miembros

## 🚀 Cómo Contribuir

### Reportar Bugs

Si encuentras un bug:

1. Verifica que no haya sido reportado previamente en [Issues](../../issues)
2. Crea un nuevo issue con:
   - Descripción clara del problema
   - Pasos para reproducir
   - Comportamiento esperado vs. actual
   - Screenshots (si aplica)
   - Versión de Java utilizada

### Sugerir Mejoras

Para nuevas funcionalidades:

1. Abre un issue describiendo:
   - La funcionalidad propuesta
   - Casos de uso
   - Beneficios para el proyecto
   - Posible implementación (opcional)

### Contribuir Código

1. **Fork** el repositorio
2. **Crea una rama** para tu feature:
   ```bash
   git checkout -b feature/nueva-funcionalidad
   ```
3. **Realiza tus cambios** siguiendo los estándares
4. **Haz commit** con mensajes descriptivos:
   ```bash
   git commit -m "feat: agregar sistema de crafting"
   ```
5. **Push** a tu fork:
   ```bash
   git push origin feature/nueva-funcionalidad
   ```
6. **Abre un Pull Request**

## 📝 Estándares de Código

### Estilo Java

- **Indentación**: 4 espacios (no tabs)
- **Nomenclatura**:
  - `PascalCase` para clases: `CharacterBuilder`
  - `camelCase` para métodos y variables: `getMaxHp()`
  - `UPPER_SNAKE_CASE` para constantes: `MAX_INVENTORY_SIZE`
- **Llaves**: Estilo K&R (apertura en misma línea)

```java
public class Example {
    public void method() {
        if (condition) {
            // código
        }
    }
}
```

### Documentación

- **JavaDoc** obligatorio para:
  - Clases públicas
  - Métodos públicos
  - Constructores
  
```java
/**
 * Descripción breve de la clase.
 * 
 * Descripción detallada si es necesaria.
 * 
 * @author Tu Nombre
 * @version 1.0
 */
public class MyClass {
    /**
     * Descripción del método.
     * 
     * @param param1 descripción del parámetro
     * @return descripción del valor de retorno
     */
    public int myMethod(int param1) {
        // implementación
    }
}
```

### Patrones de Diseño

Al agregar código que implemente patrones GoF:

- Documenta claramente qué patrón implementas
- Explica el propósito en los comentarios
- Mantén consistencia con patrones existentes

## 🔄 Proceso de Pull Request

### Antes de Enviar

- [ ] El código compila sin errores
- [ ] Has agregado JavaDoc apropiado
- [ ] El código sigue los estándares de estilo
- [ ] Has probado tu código manualmente
- [ ] Has actualizado el README si es necesario

### Template de PR

```markdown
## Descripción
Breve descripción de los cambios

## Tipo de Cambio
- [ ] Bug fix
- [ ] Nueva funcionalidad
- [ ] Mejora de código
- [ ] Documentación

## Checklist
- [ ] Mi código sigue los estándares del proyecto
- [ ] He agregado documentación apropiada
- [ ] He probado mi código
- [ ] Los cambios no rompen funcionalidad existente

## Screenshots (si aplica)
```

### Revisión

Todos los PRs serán revisados por mantenedores del proyecto. Puede que se soliciten cambios antes de mergear.

## 🎯 Áreas de Contribución

### Alta Prioridad

- Tests unitarios (JUnit)
- Nuevos patrones de diseño
- Mejoras de rendimiento
- Documentación adicional

### Media Prioridad

- Nuevos tipos de enemigos
- Items adicionales
- Mejoras de GUI
- Internacionalización (i18n)

### Ideas de Features

- Sistema de crafting
- Multiplayer local
- Mazmorras procedurales
- Sistema de achievements
- Editor de niveles

## 📧 Contacto

Para preguntas sobre contribuciones, abre un issue con la etiqueta `question`.

---

¡Gracias por contribuir a RPGv3! 🎮⚔️
