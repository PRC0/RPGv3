// Item.java
package rpg.inventory;
import java.io.Serializable;
import rpg.core.Character;

public abstract class Item implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    protected String name;
    protected String description;

    // Constructor
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Lógica para "usar" el item (ej. beber una poción).
     * Para equipo (espadas), este método puede no hacer nada.
     * @param target El personaje sobre el que se usa el item.
     */
    public abstract void use(Character target);

    // Getters para mostrar en la GUI
    public String getName() { return name; }
    public String getDescription() { return description; }
}