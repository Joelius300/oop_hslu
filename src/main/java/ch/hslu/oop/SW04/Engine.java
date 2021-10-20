package ch.hslu.oop.SW04;

public class Engine implements Switchable {
    private int rpm;

    @Override
    public void switchOn() {
        rpm = 1800;
    }

    @Override
    public void switchOff() {
        rpm = 0;
    }

    @Override
    public boolean isSwitchedOn() {
        return rpm > 0;
    }

    public int getRpm() {
        return rpm;
    }
}
