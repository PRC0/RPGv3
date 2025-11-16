package rpg.decorator;

import rpg.core.Character;
import rpg.inventory.Equippable;

/**
 * Decorador que refuerza una armadura.
 * Aumenta la defensa y añade HP máximo.
 */
public class ReinforcedArmor extends EquipmentDecorator {
    
    private int bonusDefense;
    private int bonusHp;
    private String reinforcementType;
    
    public ReinforcedArmor(Equippable armor, String reinforcementType, int bonusDefense, int bonusHp) {
        super(armor);
        this.reinforcementType = reinforcementType;
        this.bonusDefense = bonusDefense;
        this.bonusHp = bonusHp;
    }
    
    @Override
    public void applyStats(Character character) {
        // Primero aplica los stats del item base
        super.applyStats(character);
        
        // Luego añade los bonus del refuerzo
        character.addDefenseBonus(bonusDefense);
        character.addMaxHp(bonusHp);
        
        System.out.println("🛡️ Refuerzo [" + reinforcementType + "] aplicado! +" 
            + bonusDefense + " DEF, +" + bonusHp + " HP");
    }
    
    @Override
    public void removeStats(Character character) {
        // Quita los bonus del refuerzo
        character.addDefenseBonus(-bonusDefense);
        character.addMaxHp(-bonusHp);
        
        // Luego quita los stats del item base
        super.removeStats(character);
        
        System.out.println("🛡️ Refuerzo [" + reinforcementType + "] removido.");
    }
    
    public String getReinforcementType() {
        return reinforcementType;
    }
    
    @Override
    public String getName() {
        return decoratedItem.getName() + " [" + reinforcementType + "]";
    }
}
