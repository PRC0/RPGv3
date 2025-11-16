package rpg.core;

import java.util.ArrayList;
import java.util.List;
import rpg.combat.PassiveStrategy;

/**
 * Grupo de enemigos - Patrón Composite (GoF).
 * 
 * PROPÓSITO:
 * - Trata múltiples enemigos como una sola entidad Character
 * - Permite que BattleManager maneje grupos sin código especial
 * - Distribuye el daño recibido entre sus miembros
 * 
 * ARQUITECTURA:
 * Component (Character)
 *    ├── Leaf (Enemy individual)
 *    └── Composite (EnemyGroup) ← contiene múltiples Characters
 * 
 * COMPORTAMIENTO:
 * - receiveDamage(): Distribuye el daño a un miembro aleatorio
 * - isAlive(): El grupo vive mientras al menos un miembro esté vivo
 * - attack(): No hace nada (PassiveStrategy), cada miembro ataca individualmente
 * 
 * EJEMPLO:
 * EnemyGroup grupo = new EnemyGroup("Banda de Goblins");
 * grupo.addMember(new Enemy("Goblin 1", ...));
 * grupo.addMember(new Enemy("Goblin 2", ...));
 * // BattleManager lo trata como un solo enemigo
 */
public class EnemyGroup extends Character {

    private List<Character> members = new ArrayList<>();

    public EnemyGroup(String groupName) {
        // Un 'Grupo' no tiene stats por sí mismo, así que ponemos 0
        super(groupName, 1, 0, 0, 0, 0);
        
        // Un grupo no ataca por sí solo, sus miembros lo hacen
        this.setAttackStrategy(new PassiveStrategy()); 
    }

    // --- Métodos para manejar el grupo ---
    
    public void addMember(Character enemy) {
        this.members.add(enemy);
        // La vida total del grupo es la suma de sus miembros
        this.maxHp += enemy.getMaxHp();
        this.currentHp += enemy.getCurrentHp();
    }

    public void removeMember(Character enemy) {
        this.members.remove(enemy);
    }
    
    public List<Character> getMembers() {
        return this.members;
    }

    // --- Sobrescribimos los métodos de Character ---

    @Override
    public String receiveDamage(int amount) {
        // Cuando el 'Grupo' recibe daño, se lo pasa a un miembro aleatorio
        if (!this.isAlive()) return "";
        
        Character randomMember = members.get((int) (Math.random() * members.size()));
        String message = randomMember.receiveDamage(amount);
        
        // Actualizamos la vida total del grupo
        this.currentHp = 0;
        for (Character member : members) {
            this.currentHp += member.getCurrentHp();
        }
        
        return message;
    }

    @Override
    public boolean isAlive() {
        // El grupo está vivo si al menos un miembro lo está
        // (Aprovechamos para limpiar muertos)
        members.removeIf(member -> !member.isAlive());
        return !members.isEmpty();
    }
    
    // Los métodos abstractos deben ser implementados, aunque no hagan nada
    @Override
    public void specialAbility(Character target) {
        // El grupo no tiene habilidades, los miembros sí
    }

    @Override
    public void levelUp() {
        // El grupo no sube de nivel
    }
}