// Archer.java
package rpg.core;

import rpg.combat.RangedAttack;

public class Archer extends Character {

    // --- Constructor ---
    public Archer(String name) {
        // Llama al constructor de Character (la clase padre)
        // super(name, level, baseAttack, baseMagic, maxHp, maxMana)
        super(name, 1, 8, 2, 85, 15); // Lvl 1, 8 ATK, 2 MAG, 85 HP, 15 MP
        
        // ¡Patrón Strategy! Le asignamos su estrategia de ataque
        this.setAttackStrategy(new RangedAttack());
    }

    // --- Implementación de Métodos Abstractos (Obligatorios) ---

    @Override
    public void specialAbility(Character target) {
        // Habilidad especial: "Disparo Perforante"
        // Gasta 10 de maná para hacer 2x de daño
        
        int manaCost = 10;
        
        if (this.consumeMana(manaCost)) {
            int damage = (int) (this.baseAttack * 2.0); // Daño x2.0
            System.out.println(this.name + " usa [Disparo Perforante] contra " + target.getName() + " por " + damage + " de daño!");
            target.receiveDamage(damage);
        } else {
            System.out.println(this.name + " intenta usar [Disparo Perforante], ¡pero no tiene maná!");
        }
    }

    @Override
    public void levelUp() {
        // Define cómo sube de nivel un Arquero
        this.level++;
        this.maxHp += 10; // Ganancia de vida balanceada
        this.maxMana += 3; // Gana un poco de maná
        this.baseAttack += 4; // Gana buen ataque
        this.baseMagic += 1; // Gana casi nada de magia
        
        // Rellena la vida y el maná al subir de nivel
        this.currentHp = this.maxHp;
        this.currentMana = this.maxMana;
        
        System.out.println(this.name + " ha subido al nivel " + this.level + "!");
    }
}