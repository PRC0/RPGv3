package rpg.core;

import rpg.combat.PassiveStrategy; 

/**
 * Un NPC que puede curar al jugador.
 * Hereda de Character para poder existir en el mundo del juego.
 */
public class Priest extends Character {

    // --- Constructor ---
    public Priest(String name) {
        // Los stats de un Priest no importan mucho si no pelea
        // super(name, level, baseAttack, baseMagic, maxHp, maxMana)
        super(name, 10, 0, 10, 100, 200); // Es nivel 10, no ataca, mucha vida y maná
        
        // Un Priest no ataca, pero 'attackStrategy' no puede ser null.
        // Le damos una que no haga nada o una mágica.
        // O creamos una "PassiveStrategy"
        this.setAttackStrategy(new PassiveStrategy());
    }

    // --- Métodos Abstractos (Obligatorios) ---

    @Override
    public void specialAbility(Character target) {
        // La "habilidad especial" de un Priest es curar a otros
        this.healTarget(target, 50); // Cura 50 HP
    }

 // --- ARREGLO ---
    // Rellena este método para que specialAbility funcione
    private void healTarget(Character target, int amount) {
        // ¡Solo necesita llamar al método 'heal' del objetivo!
        // (Y tal vez consumir maná del propio Priest)
        
        int manaCost = 10;
        if (this.consumeMana(manaCost)) {
            System.out.println(this.name + " lanza [Curación Menor] sobre " + target.getName() + "!");
            target.heal(amount);
        } else {
            System.out.println(this.name + " intenta curar pero no tiene maná.");
        }
	}

	@Override
    public void levelUp() {
        // Los NPCs no suben de nivel
    }

    // --- Lógica de Interacción (El método clave) ---

    /**
     * El método principal de este NPC.
     * Revisa al objetivo (jugador) y lo cura si es necesario.
     * ¡Usa la interfaz Healable para no depender de la clase 'Character'!
     */
    public void offerHeal(Healable target) {
        System.out.println(this.name + ": 'Permíteme sanar tus heridas.'");

        if (!target.isAlive()) {
            System.out.println(this.name + ": 'Es demasiado tarde para este...'");
            return;
        }

        if (target.getCurrentHp() < target.getMaxHp()) {
            int amountToHeal = target.getMaxHp() - target.getCurrentHp();
            target.heal(amountToHeal); // ¡Cura completa!
            System.out.println(this.name + ": '¡Que la luz te restaure! (Curado por " + amountToHeal + " HP)'");
        } else {
            System.out.println(this.name + ": 'Pareces estar en perfecto estado de salud.'");
        }
    }
}