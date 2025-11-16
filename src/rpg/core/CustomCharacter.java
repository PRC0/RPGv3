package rpg.core;

import rpg.factory.CharacterType;
import rpg.events.GameEventManager;
import rpg.events.EventType;

/**
 * Personaje totalmente personalizable creado con CharacterBuilder.
 * 
 * PROPÓSITO:
 * - Permite crear personajes con stats únicos no disponibles en clases predefinidas
 * - Construido usando el patrón Builder para máxima flexibilidad
 * - Útil para NPCs especiales, personajes de prueba, o héroes modificados
 * 
 * DIFERENCIA CON CLASES ESTÁNDAR:
 * - Warrior/Mage/Archer: Stats predefinidos, balanceados para juego normal
 * - CustomCharacter: Stats completamente personalizables vía Builder
 * 
 * COMPORTAMIENTO:
 * - Habilidades especiales basadas en el CharacterType
 * - Level up adaptado al tipo (Warrior: +HP/ATK, Mage: +Maná/MAG)
 * 
 * EJEMPLO:
 * Character hibrido = CharacterBuilder.warrior("Paladin")
 *     .withBaseMagic(10)  // Guerrero con magia!
 *     .withMaxMana(30)
 *     .build();
 */
public class CustomCharacter extends Character {
    
    private static final long serialVersionUID = 1L;
    private CharacterType characterType;

    public CustomCharacter(String name, int level, int baseAttack, int baseMagic, 
                          int maxHp, int maxMana, CharacterType type) {
        super(name, level, baseAttack, baseMagic, maxHp, maxMana);
        this.characterType = type;
    }

    @Override
    public void specialAbility(Character target) {
        // Habilidad genérica basada en el tipo
        switch (characterType) {
            case WARRIOR:
                warriorAbility(target);
                break;
            case MAGE:
                mageAbility(target);
                break;
            case ARCHER:
                archerAbility(target);
                break;
            default:
                // Ataque básico potenciado
                int damage = (int) (this.baseAttack * 1.5);
                System.out.println(this.name + " usa [Golpe Especial] por " + damage + " de daño!");
                target.receiveDamage(damage);
        }
    }
    
    private void warriorAbility(Character target) {
        int damage = (int) (this.baseAttack * 1.5);
        System.out.println(this.name + " usa [Carga Brutal] por " + damage + " de daño!");
        target.receiveDamage(damage);
    }
    
    private void mageAbility(Character target) {
        int manaCost = 15;
        if (this.consumeMana(manaCost)) {
            int damage = (int) (this.baseMagic * 2.5);
            System.out.println(this.name + " lanza [Bola de Fuego] por " + damage + " de daño!");
            target.receiveDamage(damage);
        } else {
            System.out.println(this.name + " no tiene maná suficiente!");
        }
    }
    
    private void archerAbility(Character target) {
        int manaCost = 10;
        if (this.consumeMana(manaCost)) {
            int damage = (int) (this.baseAttack * 2.0);
            System.out.println(this.name + " usa [Disparo Perforante] por " + damage + " de daño!");
            target.receiveDamage(damage);
        } else {
            System.out.println(this.name + " no tiene maná suficiente!");
        }
    }

    @Override
    public void levelUp() {
        this.level++;
        
        // Stats aumentan según el tipo
        switch (characterType) {
            case WARRIOR:
                this.maxHp += 20;
                this.baseAttack += 5;
                this.baseMagic += 1;
                break;
            case MAGE:
                this.maxHp += 5;
                this.maxMana += 15;
                this.baseMagic += 6;
                this.baseAttack += 1;
                break;
            case ARCHER:
                this.maxHp += 10;
                this.maxMana += 3;
                this.baseAttack += 4;
                this.baseMagic += 1;
                break;
            default:
                this.maxHp += 10;
                this.baseAttack += 3;
                this.baseMagic += 2;
        }
        
        this.currentHp = this.maxHp;
        this.currentMana = this.maxMana;
        
        GameEventManager.getInstance().notify(EventType.PLAYER_LEVELED_UP, this);
        System.out.println(this.name + " ha subido al nivel " + this.level + "!");
    }
    
    public CharacterType getCharacterType() {
        return characterType;
    }
}
