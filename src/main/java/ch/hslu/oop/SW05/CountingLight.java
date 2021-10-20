package ch.hslu.oop.SW05;

import ch.hslu.oop.SW04.Light;

public class CountingLight extends Light implements CountingSwitchable {
    private long switchCount;

    @Override
    public void switchOn() {
        super.switchOn();
        switchCount++;
    }

    @Override
    public void switchOff() {
        super.switchOff();
        switchCount++;
    }

    @Override
    public long getSwitchCount() {
        return switchCount;
    }
}
