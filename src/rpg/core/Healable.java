package rpg.core;

/**
 * Define el contrato para cualquier objeto en el juego que pueda
 * manejar vida (recibir daño, ser curado, etc.)
 */
public interface Healable {

    /**
     * Aplica una cantidad de curación al objeto.
     */
    void heal(int amount);

    /**
     * Aplica una cantidad de daño al objeto.
     * @return Un mensaje describiendo el daño recibido
     */
    String receiveDamage(int amount);

    /**
     * @return La vida actual del objeto.
     */
    int getCurrentHp();

    /**
     * @return La vida máxima del objeto.
     */
    int getMaxHp();

    /**
     * @return true si el objeto tiene más de 0 HP, false en caso contrario.
     */
    boolean isAlive();
}