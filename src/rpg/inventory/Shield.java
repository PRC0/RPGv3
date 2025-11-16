package rpg.inventory;

import rpg.core.Character;

/**
 * Representa un escudo equipable.
 * Proporciona principalmente defensa.
 */
public class Shield extends Item implements Equippable {
    
    private static final long serialVersionUID = 1L;
    
    private int defenseBonus;
    
    public Shield(String name, String description, int defenseBonus) {
        super(name, description);
        this.defenseBonus = defenseBonus;
    }
    
    @Override
    public void use(Character target) {
        // Los escudos no se "usan", solo se equipan
        System.out.println("El escudo " + name + " debe ser equipado, no usado.");
    }
    
    @Override
    public void applyStats(Character character) {
        character.addDefenseBonus(defenseBonus);
    }
    
    @Override
    public void removeStats(Character character) {
        character.addDefenseBonus(-defenseBonus);
    }
    
    @Override
    public Slot getSlot() {
        return Slot.WEAPON; // Usa el slot de arma (off-hand)
    }
}
