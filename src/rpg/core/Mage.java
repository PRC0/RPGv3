// Mage.java
package rpg.core;

import rpg.combat.MagicAttack;
import rpg.events.GameEventManager; // <-- ¡Importa el manager!
import rpg.events.EventType;      // <-- ¡Importa los tipos de evento!

public class Mage extends Character {

    // --- Constructor ---
    public Mage(String name) {
        // Llama al constructor de Character (la clase padre)
        // super(name, level, baseAttack, baseMagic, maxHp, maxMana)
        super(name, 1, 2, 12, 70, 50); // Lvl 1, 2 ATK, 12 MAG, 70 HP, 50 MP
        
        // ¡Patrón Strategy! Le asignamos su estrategia de ataque mágica
        this.setAttackStrategy(new MagicAttack());
    }

    // --- Implementación de Métodos Abstractos (Obligatorios) ---

    @Override
    public void specialAbility(Character target) {
        // Habilidad especial: "Bola de Fuego"
        int manaCost = 15;
        
        if (this.consumeMana(manaCost)) {
            int damage = (int) (this.baseMagic * 2.5); // Daño x2.5
            System.out.println(this.name + " lanza una [Bola de Fuego] contra " + target.getName() + " por " + damage + " de daño!");
            target.receiveDamage(damage);
        } else {
            System.out.println(this.name + " intenta lanzar [Bola de Fuego], ¡pero no tiene maná!");
        }
    }

    @Override
    public void levelUp() {
        // Define cómo sube de nivel un Mago
        this.level++;
        this.maxHp += 5; // Los magos ganan poca vida
        this.maxMana += 15; // Pero mucho maná
        this.baseMagic += 6; // Y mucho poder mágico
        this.baseAttack += 1;
        
        // Rellena la vida y el maná al subir de nivel
        this.currentHp = this.maxHp;
        this.currentMana = this.maxMana;
        GameEventManager.getInstance().notify(EventType.PLAYER_LEVELED_UP, this);
        
        System.out.println(this.name + " ha subido al nivel " + this.level + "!");
    }
}