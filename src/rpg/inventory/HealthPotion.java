package rpg.inventory;
import rpg.core.Character;

/**
 * Poción de salud consumible.
 * 
 * FUNCIONALIDAD:
 * - Restaura HP del personaje al usarse
 * - Se consume automáticamente (se elimina del inventario)
 * - No se puede equipar, solo usar
 * 
 * TIPOS DISPONIBLES:
 * - Constructor por defecto: Poción pequeña (30 HP)
 * - Constructor personalizado: Permite crear pociones de cualquier tamaño
 * 
 * USO EN COMBATE:
 * Inventory.use(potion, player) → cura al jugador y elimina la poción
 * 
 * EJEMPLO:
 * HealthPotion pequeña = new HealthPotion(); // 30 HP
 * HealthPotion grande = new HealthPotion("Mega Poción", "Restaura 100 HP", 100);
 */
public class HealthPotion extends Item {
	
	private static final long serialVersionUID = 1L;
	private int healAmount;

	/**
	 * Constructor por defecto - crea una poción que cura 30 HP
	 */
	public HealthPotion() {
		super("Poción de Salud", "Restaura 30 HP");
		this.healAmount = 30;
	}

	/**
	 * Constructor personalizado
	 * @param name Nombre de la poción
	 * @param description Descripción
	 * @param healAmount Cantidad de HP que restaura
	 */
	public HealthPotion(String name, String description, int healAmount) {
		super(name, description);
		this.healAmount = healAmount;
	}

	@Override
	public void use(Character target) {
		System.out.println(target.getName() + " usa " + this.name);
		target.heal(healAmount);
		System.out.println("¡" + target.getName() + " ha sido curado por " + healAmount + " HP!");
	}
	
	public int getHealAmount() {
		return healAmount;
	}

}
