package rpg.inventory;

import rpg.core.Character;

/**
 * Representa una poción de maná consumible.
 * Restaura puntos de maná al jugador.
 */
public class ManaPotion extends Item {
    
    private static final long serialVersionUID = 1L;
    
    private int manaRestore;
    
    public ManaPotion(String name, String description, int manaRestore) {
        super(name, description);
        this.manaRestore = manaRestore;
    }
    
    @Override
    public void use(Character target) {
        int currentMana = target.getCurrentMana();
        int maxMana = target.getMaxMana();
        int newMana = Math.min(currentMana + manaRestore, maxMana);
        
        target.setCurrentMana(newMana);
        
        System.out.println(target.getName() + " usó " + name + " y recuperó " + 
                          (newMana - currentMana) + " puntos de maná.");
    }
    
    public int getManaRestore() {
        return manaRestore;
    }
}
