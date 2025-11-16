package rpg.inventory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Tabla de loot que contiene todos los posibles items que puede dropear un enemigo.
 * Cada item tiene su propia probabilidad de ser dropeado.
 */
public class LootTable implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private List<ItemDrop> possibleDrops;
    private int guaranteedGold; // Oro que siempre dropea
    private int bonusGoldRange;  // Oro adicional aleatorio (0 a bonusGoldRange)
    
    public LootTable() {
        this.possibleDrops = new ArrayList<>();
        this.guaranteedGold = 0;
        this.bonusGoldRange = 0;
    }
    
    /**
     * Añade un item a la tabla de loot.
     * @param item El item a añadir
     * @param dropChance Probabilidad de drop (0.0 a 1.0)
     */
    public void addDrop(Item item, double dropChance) {
        possibleDrops.add(new ItemDrop(item, dropChance));
    }
    
    /**
     * Establece el oro que siempre dropea el enemigo.
     * @param guaranteedGold Oro garantizado
     * @param bonusGoldRange Oro adicional aleatorio (0 a este valor)
     */
    public void setGoldReward(int guaranteedGold, int bonusGoldRange) {
        this.guaranteedGold = guaranteedGold;
        this.bonusGoldRange = bonusGoldRange;
    }
    
    /**
     * Genera los items que son dropeados basándose en las probabilidades.
     * @return Lista de items dropeados
     */
    public List<Item> generateDrops() {
        List<Item> droppedItems = new ArrayList<>();
        
        for (ItemDrop drop : possibleDrops) {
            if (drop.shouldDrop()) {
                droppedItems.add(drop.getItem());
            }
        }
        
        return droppedItems;
    }
    
    /**
     * Calcula el oro total dropeado.
     * @return Cantidad de oro dropeado
     */
    public int generateGold() {
        if (bonusGoldRange > 0) {
            return guaranteedGold + (int)(Math.random() * bonusGoldRange);
        }
        return guaranteedGold;
    }
    
    /**
     * Obtiene información sobre los posibles drops para mostrar en UI.
     */
    public String getDropInfo() {
        if (possibleDrops.isEmpty()) {
            return "No dropea items";
        }
        
        StringBuilder sb = new StringBuilder("Posibles drops:\n");
        for (ItemDrop drop : possibleDrops) {
            sb.append("- ").append(drop.toString()).append("\n");
        }
        
        if (guaranteedGold > 0 || bonusGoldRange > 0) {
            sb.append("Oro: ").append(guaranteedGold);
            if (bonusGoldRange > 0) {
                sb.append("-").append(guaranteedGold + bonusGoldRange);
            }
            sb.append(" monedas\n");
        }
        
        return sb.toString();
    }
    
    // Getters
    public List<ItemDrop> getPossibleDrops() {
        return possibleDrops;
    }
    
    public int getGuaranteedGold() {
        return guaranteedGold;
    }
    
    public int getBonusGoldRange() {
        return bonusGoldRange;
    }
}
