// Warrior.java
package rpg.core;

import rpg.combat.MeleeAttack;
import rpg.events.GameEventManager; // <-- ¡Importa el manager!
import rpg.events.EventType;      // <-- ¡Importa los tipos de evento!

public class Warrior extends Character {

    // --- Constructor ---
    public Warrior(String name) {
        // Llama al constructor de Character (la clase padre)
        // super(name, level, baseAttack, baseMagic, maxHp, maxMana)
        super(name, 1, 10, 0, 100, 0); // Lvl 1, 10 ATK, 0 MAG, 100 HP, 0 MP
        
        // ¡Patrón Strategy! Le asignamos su estrategia de ataque
        this.setAttackStrategy(new MeleeAttack());
    }

    // --- Implementación de Métodos Abstractos (Obligatorios) ---

    @Override
    public void specialAbility(Character target) {
        // Habilidad especial: "Carga Brutal"
        // (Como no tenemos maná, simplemente hace más daño)
        
        int damage = (int) (this.baseAttack * 1.5); // Daño x1.5
        System.out.println(this.name + " usa [Carga Brutal] contra " + target.getName() + " por " + damage + " de daño!");
        
        target.receiveDamage(damage);
    }

    @Override
    public void levelUp() {
        // Define cómo sube de nivel un Guerrero
        this.level++;
        this.maxHp += 20; // Los guerreros ganan mucha vida
        this.baseAttack += 5; // Y mucho ataque
        this.baseMagic += 1; // Ganan casi nada de magia
        
        // Rellena la vida y el maná al subir de nivel
        this.currentHp = this.maxHp;
        this.currentMana = this.maxMana;
        GameEventManager.getInstance().notify(EventType.PLAYER_LEVELED_UP, this);
        
        System.out.println(this.name + " ha subido al nivel " + this.level + "!");
    }
}