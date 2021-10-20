package ch.hslu.oop.SW04;

/**
 * An object which can be switched on and off.
 */
public interface Switchable {
    /**
     * Switch the object on.
     */
    void switchOn();

    /**
     * Switch the object off.
     */
    void switchOff();

    /**
     * Check if object is turned on.
     * @return {@code true} if the object is switched on, {@code false} otherwise.
     */
    boolean isSwitchedOn();
}
