package rpg.inventory;

import java.io.Serializable;

/**
 * Representa un item potencial que puede ser dropeado por un enemigo.
 * Incluye el item y la probabilidad de que aparezca.
 */
public class ItemDrop implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Item item;
    private double dropChance; // Probabilidad de 0.0 a 1.0 (ej. 0.5 = 50%)
    
    /**
     * Constructor para un drop de item.
     * @param item El item que puede ser dropeado
     * @param dropChance Probabilidad de drop (0.0 a 1.0)
     */
    public ItemDrop(Item item, double dropChance) {
        this.item = item;
        this.dropChance = Math.max(0.0, Math.min(1.0, dropChance)); // Clamp entre 0 y 1
    }
    
    /**
     * Determina si este item es dropeado basándose en su probabilidad.
     * @return true si el item debe ser dropeado, false en caso contrario
     */
    public boolean shouldDrop() {
        return Math.random() < dropChance;
    }
    
    // Getters
    public Item getItem() {
        return item;
    }
    
    public double getDropChance() {
        return dropChance;
    }
    
    /**
     * Obtiene el porcentaje de drop para mostrar en UI.
     */
    public String getDropChancePercentage() {
        return String.format("%.0f%%", dropChance * 100);
    }
    
    @Override
    public String toString() {
        return item.getName() + " (" + getDropChancePercentage() + ")";
    }
}
