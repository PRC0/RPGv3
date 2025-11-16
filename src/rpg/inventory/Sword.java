// Archivo: rpg/inventory/Sword.java
package rpg.inventory;

import rpg.core.Character; // Importante

// Una Espada ES UN Item e IMPLEMENTA Equippable
public class Sword extends Item implements Equippable {

    private int attackBonus;

    public Sword(String name, String description, int attackBonus) {
        super(name, description);
        this.attackBonus = attackBonus;
    }

    @Override
    public void use(Character target) {
        System.out.println("No puedes 'usar' una espada, ¡equípala!");
    }

    // --- Métodos de Equippable ---
    
    @Override
    public Slot getSlot() {
        return Slot.WEAPON;
    }

    @Override
    public void applyStats(Character character) {
        System.out.println(this.name + " equipado. +" + this.attackBonus + " ATK");
        character.addAttackBonus(this.attackBonus);
    }

    @Override
    public void removeStats(Character character) {
        System.out.println(this.name + " desequipado. -" + this.attackBonus + " ATK");
        character.addAttackBonus(-this.attackBonus); // Resta el bonus
    }
}