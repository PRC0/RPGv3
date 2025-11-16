package rpg.inventory;

import rpg.core.Character;

/**
 * Representa unas botas equipables.
 * Pueden proporcionar defensa y otros bonus.
 */
public class Boots extends Item implements Equippable {
    
    private static final long serialVersionUID = 1L;
    
    private int defenseBonus;
    private int hpBonus;
    
    public Boots(String name, String description, int defenseBonus, int hpBonus) {
        super(name, description);
        this.defenseBonus = defenseBonus;
        this.hpBonus = hpBonus;
    }
    
    @Override
    public void use(Character target) {
        // Las botas no se "usan", solo se equipan
        System.out.println("Las botas " + name + " deben ser equipadas, no usadas.");
    }
    
    @Override
    public void applyStats(Character character) {
        character.addDefenseBonus(defenseBonus);
        character.addMaxHp(hpBonus);
    }
    
    @Override
    public void removeStats(Character character) {
        character.addDefenseBonus(-defenseBonus);
        character.addMaxHp(-hpBonus);
    }
    
    @Override
    public Slot getSlot() {
        return Slot.CHEST; // Reutilizamos slot de pecho para simplificar
    }
}
