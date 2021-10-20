package ch.hslu.oop.SW04;

public class Light implements Switchable {
    private boolean isBurning;

    @Override
    public void switchOn() {
        isBurning = true;
    }

    @Override
    public void switchOff() {
        isBurning = false;
    }

    @Override
    public final boolean isSwitchedOn() {
        return isBurning;
    }
}
