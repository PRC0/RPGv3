package rpg.factory;

import rpg.core.Archer;
import rpg.core.Character;
import rpg.core.Mage;
import rpg.core.Priest;
import rpg.core.Warrior;

/**
 * Fábrica de personajes - Patrón Factory Method (GoF).
 * 
 * PROPÓSITO:
 * - Encapsula la lógica de creación de personajes
 * - Facilita añadir nuevas clases sin modificar código cliente
 * - Centraliza la configuración inicial de cada tipo
 * 
 * TIPOS SOPORTADOS:
 * - WARRIOR: Alto HP y ataque físico, sin maná
 * - MAGE: Alto poder mágico y maná, bajo HP
 * - ARCHER: Balanceado con ataques a distancia
 * - PRIEST: NPC curador, no combatiente
 * 
 * ALTERNATIVA:
 * Para personajes altamente personalizados, usar CharacterBuilder
 * 
 * @see rpg.builder.CharacterBuilder
 */
public class CharacterFactory {

    /**
     * Crea y devuelve una instancia de un personaje.
     * @param type El tipo de personaje a crear (WARRIOR, MAGE, ARCHER, PRIEST)
     * @param name El nombre que tendrá el personaje
     * @return Un objeto Character (Warrior, Mage, Archer o Priest)
     */
    public Character createCharacter(CharacterType type, String name) {
        // El 'switch' ahora vive DENTRO de la fábrica
        switch (type) {
            case WARRIOR:
                return new Warrior(name);
            case MAGE:
                return new Mage(name);
            case ARCHER:
            	return new Archer(name);
            case PRIEST:
            	return new Priest(name);
            default:
                // Lanza una excepción si el tipo no es válido
                throw new IllegalArgumentException("Tipo de personaje desconocido: " + type);
        }
    }
}