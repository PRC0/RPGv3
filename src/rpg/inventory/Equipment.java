// Equipment.java
package rpg.inventory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import rpg.core.Character;

public class Equipment implements Serializable {
    
    private static final long serialVersionUID = 1L;

    // Un mapa que guarda el Slot (la llave) y el Item (el valor)
    private Map<Slot, Equippable> equippedItems;

    public Equipment() {
        this.equippedItems = new HashMap<>();
    }

    public void equip(Character character, Equippable item) {
        Slot slot = item.getSlot();

        // 1. Revisa si ya hay algo en ese slot
        if (equippedItems.containsKey(slot)) {
            // Si hay, desequípalo primero
            unequip(character, slot);
        }

        // 2. Coloca el item nuevo y aplica stats
        equippedItems.put(slot, item);
        item.applyStats(character);
     // Quita el item del inventario al equiparlo
        character.getInventory().remove(item);
    
    }

    public void unequip(Character character, Slot slot) {
        Equippable item = equippedItems.get(slot);
        
        if (item != null) {
            // 1. Quita los stats
            item.removeStats(character);
            // 2. Quita el item del mapa
            equippedItems.remove(slot);
            if (item instanceof Item) {
                character.getInventory().add((Item) item);
            }
        }
    }
    
    // Getters para la GUI
    public Equippable getWeapon() {
        return equippedItems.get(Slot.WEAPON);
    }
    
    public Equippable getChestplate() {
        return equippedItems.get(Slot.CHEST);
    }
}