package rpg.inventory;

import rpg.core.Character;

/**
 * Representa un anillo mágico equipable.
 * Proporciona bonus a múltiples stats.
 */
public class Ring extends Item implements Equippable {
    
    private static final long serialVersionUID = 1L;
    
    private int attackBonus;
    private int magicBonus;
    private int defenseBonus;
    
    public Ring(String name, String description, int attackBonus, int magicBonus, int defenseBonus) {
        super(name, description);
        this.attackBonus = attackBonus;
        this.magicBonus = magicBonus;
        this.defenseBonus = defenseBonus;
    }
    
    @Override
    public void use(Character target) {
        // Los anillos no se "usan", solo se equipan
        System.out.println("El anillo " + name + " debe ser equipado, no usado.");
    }
    
    @Override
    public void applyStats(Character character) {
        character.addAttackBonus(attackBonus);
        character.addMagicBonus(magicBonus);
        character.addDefenseBonus(defenseBonus);
    }
    
    @Override
    public void removeStats(Character character) {
        character.addAttackBonus(-attackBonus);
        character.addMagicBonus(-magicBonus);
        character.addDefenseBonus(-defenseBonus);
    }
    
    @Override
    public Slot getSlot() {
        return Slot.WEAPON; // Reutilizamos slot existente
    }
}
