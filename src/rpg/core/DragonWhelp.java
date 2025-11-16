package rpg.core;

import rpg.combat.MagicAttack;
import rpg.ai.MageAI;
import rpg.events.EventType;
import rpg.events.GameEventManager;
import rpg.inventory.*;

/**
 * Cachorro de Dragón - Mini-Boss con ataques mágicos devastadores.
 * 
 * CARACTERÍSTICAS ESPECIALES:
 * - Puede usar Aliento de Fuego (daño mágico masivo)
 * - Regenera 5 HP por turno (pasivo)
 * - Cambia a modo Furia cuando HP < 30%
 * 
 * HABILIDAD ESPECIAL: [ALIENTO DE FUEGO]
 * - Daño: Magia × 2.5
 * - Efecto visual único
 */
public class DragonWhelp extends Enemy {
    
    private static final long serialVersionUID = 1L;
    private boolean furyMode = false;
    private static final int REGEN_AMOUNT = 5;
    
    public DragonWhelp() {
        super("Cachorro de Dragón", 12, 12, 10, 120, 
              new MagicAttack(), new MageAI(), 150);
        
        // Inicializar con maná para ataques mágicos
        this.currentMana = 50;
        this.maxMana = 50;
        
        configureLoot();
    }
    
    private void configureLoot() {
        LootTable loot = new LootTable();
        
        // Oro abundante (tesoro de dragón)
        loot.setGoldReward(100, 150); // 100-250 oro
        
        // Items raros y poderosos (boss loot)
        loot.addDrop(new ManaPotion("Poción de Maná Grande", "Restaura 80 de maná", 80), 0.60); // 60% chance
        loot.addDrop(new HealthPotion(), 0.50); // 50% chance
        loot.addDrop(new Sword("Garra de Dragón", "Espada forjada con una garra de dragón", 20), 0.25); // 25% chance
        loot.addDrop(new Chestplate("Escamas de Dragón", "Armadura hecha de escamas brillantes", 18), 0.20); // 20% chance
        loot.addDrop(new Ring("Anillo de Fuego", "Anillo imbuido con fuego dracónico", 5, 8, 0), 0.15); // 15% chance
        
        this.setLootTable(loot);
    }
    
    @Override
    public void specialAbility(Character target) {
        String message = "*** " + this.name + " inhala profundamente...";
        System.out.println(message);
        GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, message);
        
        // Aliento de Fuego - 2.5x daño mágico
        int damage = (int) (this.baseMagic * 2.5);
        
        String fireMessage = ">>> ¡¡¡ALIENTO DE FUEGO!!! <<<";
        System.out.println(fireMessage);
        GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, fireMessage);
        
        String damageMessage = target.receiveDamage(damage);
        if (!damageMessage.isEmpty()) {
            System.out.println(damageMessage);
            GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, damageMessage);
        }
    }
    
    @Override
    public void performAIAction(Character target) {
        // Regeneración pasiva
        regenerate();
        
        // Activar modo furia si HP < 30%
        double hpPercent = (double) this.currentHp / this.maxHp;
        if (hpPercent < 0.30 && !furyMode) {
            activateFuryMode();
        }
        
        // Ejecutar IA normal
        super.performAIAction(target);
    }
    
    private void regenerate() {
        if (this.currentHp < this.maxHp) {
            int oldHp = this.currentHp;
            this.currentHp = Math.min(this.currentHp + REGEN_AMOUNT, this.maxHp);
            int healed = this.currentHp - oldHp;
            
            if (healed > 0) {
                String regenMsg = "[REGEN] " + this.name + " regenera " + healed + " HP (escamas draconicas)";
                System.out.println(regenMsg);
                GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, regenMsg);
            }
        }
    }
    
    private void activateFuryMode() {
        furyMode = true;
        this.baseAttack += 5;
        this.baseMagic += 5;
        
        String furyMsg = "*** ¡¡" + this.name + " entra en MODO FURIA!! ***\n" +
                        "¡Ataque y Magia aumentados!";
        System.out.println(furyMsg);
        GameEventManager.getInstance().notify(EventType.NEW_MESSAGE_LOGGED, furyMsg);
    }
}
