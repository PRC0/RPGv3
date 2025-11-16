package rpg.builder;

import rpg.core.*;
import rpg.factory.CharacterType;
import rpg.combat.AttackStrategy;
import rpg.combat.MeleeAttack;

/**
 * Implementa el Patrón Builder (GoF) para construcción flexible de personajes.
 * 
 * PROPÓSITO:
 * - Permite crear personajes con configuración personalizada paso a paso
 * - Separa la construcción compleja de la representación del objeto
 * - Provee métodos factory estáticos para configuraciones predefinidas
 * 
 * USO:
 * Character hero = CharacterBuilder.warrior("Arthas")
 *                      .withMaxHp(150)
 *                      .withBaseAttack(15)
 *                      .build();
 * 
 * @author RPGv3 Team
 * @version 1.0
 */
public class CharacterBuilder {
    
    private String name;
    private int level = 1;
    private int baseAttack = 5;
    private int baseMagic = 0;
    private int maxHp = 100;
    private int maxMana = 0;
    private AttackStrategy attackStrategy = new MeleeAttack();
    private CharacterType type = CharacterType.WARRIOR;
    
    public CharacterBuilder() {
        // Constructor vacío - usa valores por defecto
    }
    
    // Métodos de construcción fluidos (retornan 'this')
    
    public CharacterBuilder withName(String name) {
        this.name = name;
        return this;
    }
    
    public CharacterBuilder withLevel(int level) {
        this.level = level;
        return this;
    }
    
    public CharacterBuilder withBaseAttack(int baseAttack) {
        this.baseAttack = baseAttack;
        return this;
    }
    
    public CharacterBuilder withBaseMagic(int baseMagic) {
        this.baseMagic = baseMagic;
        return this;
    }
    
    public CharacterBuilder withMaxHp(int maxHp) {
        this.maxHp = maxHp;
        return this;
    }
    
    public CharacterBuilder withMaxMana(int maxMana) {
        this.maxMana = maxMana;
        return this;
    }
    
    public CharacterBuilder withAttackStrategy(AttackStrategy strategy) {
        this.attackStrategy = strategy;
        return this;
    }
    
    public CharacterBuilder ofType(CharacterType type) {
        this.type = type;
        return this;
    }
    
    /**
     * Construye un personaje personalizado basado en los parámetros configurados.
     * @return Un CustomCharacter con los stats especificados
     */
    public rpg.core.Character build() {
        if (name == null || name.isEmpty()) {
            throw new IllegalStateException("El personaje debe tener un nombre");
        }
        
        CustomCharacter character = new CustomCharacter(
            name, level, baseAttack, baseMagic, maxHp, maxMana, type
        );
        character.setAttackStrategy(attackStrategy);
        return character;
    }
    
    /**
     * Crea un builder preconfigurado para un Warrior
     */
    public static CharacterBuilder warrior(String name) {
        return new CharacterBuilder()
            .withName(name)
            .ofType(CharacterType.WARRIOR)
            .withBaseAttack(10)
            .withBaseMagic(0)
            .withMaxHp(100)
            .withMaxMana(0)
            .withAttackStrategy(new rpg.combat.MeleeAttack());
    }
    
    /**
     * Crea un builder preconfigurado para un Mage
     */
    public static CharacterBuilder mage(String name) {
        return new CharacterBuilder()
            .withName(name)
            .ofType(CharacterType.MAGE)
            .withBaseAttack(2)
            .withBaseMagic(12)
            .withMaxHp(70)
            .withMaxMana(50)
            .withAttackStrategy(new rpg.combat.MagicAttack());
    }
    
    /**
     * Crea un builder preconfigurado para un Archer
     */
    public static CharacterBuilder archer(String name) {
        return new CharacterBuilder()
            .withName(name)
            .ofType(CharacterType.ARCHER)
            .withBaseAttack(8)
            .withBaseMagic(2)
            .withMaxHp(85)
            .withMaxMana(15)
            .withAttackStrategy(new rpg.combat.RangedAttack());
    }
}
