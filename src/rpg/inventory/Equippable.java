// Equippable.java
package rpg.inventory;

import rpg.core.Character;

public interface Equippable {

    /**
     * @return El Slot (lugar) donde va este item (ej. WEAPON, CHEST).
     */
    Slot getSlot();
    
    /**
     * Aplica los modificadores de stats al personaje.
     * Se llama cuando el item se equipa.
     * @param character El personaje que equipa el item.
     */
    void applyStats(Character character);

    /**
     * Quita los modificadores de stats del personaje.
     * Se llama cuando el item se desequipa.
     * @param character El personaje que desequipa el item.
     */
    void removeStats(Character character);
    
    /**
     * Obtiene el nombre del item equipable.
     * @return Nombre del item
     */
    String getName();
}