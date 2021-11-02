package ch.hslu.oop.temperature;

public class Demo {
    public static void main(String[] args) {
        Element a = new Element("a", Temperature.fromKelvin(100), Temperature.fromKelvin(200));

        for (int i = 0; i < 300; i+= 50) {
            System.out.println("Phase at " + i + "K = " + a.getPhaseAt(Temperature.fromKelvin(i)));
        }
    }
}
