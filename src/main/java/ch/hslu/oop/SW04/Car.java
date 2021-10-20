package ch.hslu.oop.SW04;

public class Car implements Switchable {
    private final Switchable engine;
    private final Switchable light;

    // in this case, the contracts are actually _too_ loose.
    // You can pass in a light as an engine, which makes no sense.
    // You wouldn't want to use the concrete Engine class here though.
    // So the best case would probably be to have an Engine and Light interface
    // both implementing Switchable.
    public Car(Switchable engine, Switchable light) {
        this.engine = engine;
        this.light = light;
    }

    @Override
    public void switchOn() {
        engine.switchOn();
        light.switchOn();
    }

    @Override
    public void switchOff() {
        engine.switchOff();
        light.switchOff();
    }

    @Override
    public boolean isSwitchedOn() {
        return engine.isSwitchedOn();
    }
}
