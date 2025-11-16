package rpg.decorator;

import rpg.core.Character;
import rpg.inventory.Equippable;
import rpg.inventory.Slot;

/**
 * Decorador base para items equipables - Patrón Decorator (GoF).
 * 
 * PROPÓSITO:
 * - Añade funcionalidades dinámicamente a items equipables sin modificar su estructura
 * - Permite combinar múltiples mejoras (encantamientos, refuerzos) de forma flexible
 * - Mantiene la interfaz Equippable para transparencia
 * 
 * EJEMPLO:
 * Equippable espada = new Sword("Espada Larga", "Una espada común", 10);
 * espada = new EnchantedWeapon(espada, "Fuego", 5, 3); // +5 ATK, +3 MAG
 * 
 * @see EnchantedWeapon
 * @see ReinforcedArmor
 */
public abstract class EquipmentDecorator implements Equippable {
    
    protected Equippable decoratedItem;
    
    public EquipmentDecorator(Equippable item) {
        this.decoratedItem = item;
    }
    
    @Override
    public Slot getSlot() {
        return decoratedItem.getSlot();
    }
    
    @Override
    public void applyStats(Character character) {
        decoratedItem.applyStats(character);
    }
    
    @Override
    public void removeStats(Character character) {
        decoratedItem.removeStats(character);
    }
    
    @Override
    public String getName() {
        return decoratedItem.getName();
    }
}
