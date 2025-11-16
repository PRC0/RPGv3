// Archivo: rpg/inventory/Chestplate.java
package rpg.inventory;

import rpg.core.Character; // Importante

// Una Pechera ES UN Item e IMPLEMENTA Equippable
public class Chestplate extends Item implements Equippable {

    private int defenseBonus;

    public Chestplate(String name, String description, int defenseBonus) {
        super(name, description);
        this.defenseBonus = defenseBonus;
    }

    @Override
    public void use(Character target) {
        System.out.println("No puedes 'usar' una armadura, ¡equípala!");
    }

    // --- Métodos de Equippable ---
    
    @Override
    public Slot getSlot() {
        return Slot.CHEST;
    }

    @Override
    public void applyStats(Character character) {
        System.out.println(this.name + " equipado. +" + this.defenseBonus + " DEF");
        character.addDefenseBonus(this.defenseBonus);
    }

    @Override
    public void removeStats(Character character) {
        System.out.println(this.name + " desequipado. -" + this.defenseBonus + " DEF");
        character.addDefenseBonus(-this.defenseBonus); // Resta el bonus
    }
}