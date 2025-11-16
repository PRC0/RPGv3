package rpg.decorator;

import rpg.core.Character;
import rpg.inventory.Equippable;

/**
 * Decorador que añade encantamiento mágico a un arma.
 * 
 * FUNCIONALIDAD:
 * - Aumenta el daño de ataque físico
 * - Añade daño mágico adicional
 * - Mantiene todas las propiedades del arma base
 * 
 * EJEMPLO DE USO:
 * Equippable espada = new Sword("Espada de Acero", "Espada común", 15);
 * espada = new EnchantedWeapon(espada, "Llamas Ardientes", 8, 5);
 * // Ahora otorga: 15 (base) + 8 (encant.) = 23 ATK y +5 MAG
 * 
 * COMBINABLE:
 * Puedes decorar múltiples veces:
 * espada = new EnchantedWeapon(espada, "Hielo", 3, 7); // ¡Doble encantamiento!
 */
public class EnchantedWeapon extends EquipmentDecorator {
    
    private int bonusAttack;
    private int bonusMagic;
    private String enchantmentName;
    
    public EnchantedWeapon(Equippable weapon, String enchantmentName, int bonusAttack, int bonusMagic) {
        super(weapon);
        this.enchantmentName = enchantmentName;
        this.bonusAttack = bonusAttack;
        this.bonusMagic = bonusMagic;
    }
    
    @Override
    public void applyStats(Character character) {
        // Primero aplica los stats del item base
        super.applyStats(character);
        
        // Luego añade los bonus del encantamiento
        character.addAttackBonus(bonusAttack);
        character.addMagicBonus(bonusMagic);
        
        System.out.println("✨ Encantamiento [" + enchantmentName + "] aplicado! +" 
            + bonusAttack + " ATK, +" + bonusMagic + " MAG");
    }
    
    @Override
    public void removeStats(Character character) {
        // Quita los bonus del encantamiento
        character.addAttackBonus(-bonusAttack);
        character.addMagicBonus(-bonusMagic);
        
        // Luego quita los stats del item base
        super.removeStats(character);
        
        System.out.println("✨ Encantamiento [" + enchantmentName + "] removido.");
    }
    
    public String getEnchantmentName() {
        return enchantmentName;
    }
    
    @Override
    public String getName() {
        return decoratedItem.getName() + " [" + enchantmentName + "]";
    }
}
