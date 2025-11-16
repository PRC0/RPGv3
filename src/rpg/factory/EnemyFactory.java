// Archivo: rpg/factory/EnemyFactory.java
package rpg.factory;

// Importa las clases de otros paquetes
import rpg.core.Character;
import rpg.core.EnemyGroup;

// Importar clases específicas de enemigos
import rpg.core.Slime;
import rpg.core.Goblin;
import rpg.core.Skeleton;
import rpg.core.OrcWarrior;
import rpg.core.DarkMage;
import rpg.core.Wolf;
import rpg.core.DragonWhelp;
import rpg.core.Bandit;

public class EnemyFactory {

    public Character createEnemy(EnemyType type) {
        switch (type) {
            case SLIME:
                return new Slime();
            
            case GOBLIN:
                return new Goblin();
                
            case SKELETON:
                return new Skeleton();
            
            case ORC:
                return new OrcWarrior();
            
            case DARK_MAGE:
                return new DarkMage();
            
            case WOLF:
                return new Wolf();
            
            case DRAGON_WHELP:
                return new DragonWhelp();
            
            case BANDIT:
                return new Bandit();

            default:
                throw new IllegalArgumentException("Tipo de enemigo desconocido: " + type);
        }
    }
// --- ¡¡AÑADE ESTE MÉTODO NUEVO!! ---
    
    /**
     * Crea un "encuentro" de batalla aleatorio usando el Patrón Composite.
     * Genera grupos variados de enemigos para mayor diversión.
     * @return Un solo 'Character' que es un EnemyGroup o un Enemy individual.
     */
    public Character createEncounter() {
        double random = Math.random();
        
        // 40% probabilidad de enemigo individual
        if (random < 0.4) {
            return createRandomEnemy();
        }
        
        // 60% probabilidad de grupo de enemigos
        EnemyGroup encounter;
        
        if (random < 0.6) {
            // Grupo pequeño (2 enemigos)
            encounter = new EnemyGroup("Patrulla de Enemigos");
            encounter.addMember(createRandomEnemy());
            encounter.addMember(createRandomEnemy());
        } else if (random < 0.85) {
            // Grupo mediano (3 enemigos débiles)
            encounter = new EnemyGroup("Horda de Criaturas");
            encounter.addMember(createEnemy(EnemyType.SLIME));
            encounter.addMember(createEnemy(EnemyType.GOBLIN));
            encounter.addMember(createEnemy(EnemyType.WOLF));
        } else {
            // Encuentro difícil (mini-boss + secuaces)
            encounter = new EnemyGroup("Encuentro Peligroso");
            encounter.addMember(createEnemy(EnemyType.DRAGON_WHELP));
            encounter.addMember(createEnemy(EnemyType.DARK_MAGE));
        }
        
        return encounter;
    }
    
    /**
     * Crea un enemigo aleatorio de dificultad apropiada.
     * @return Un enemigo aleatorio
     */
    private Character createRandomEnemy() {
        EnemyType[] commonEnemies = {
            EnemyType.SLIME, EnemyType.GOBLIN, EnemyType.SKELETON,
            EnemyType.WOLF, EnemyType.BANDIT, EnemyType.ORC
        };
        
        int index = (int) (Math.random() * commonEnemies.length);
        return createEnemy(commonEnemies[index]);
    }   
}