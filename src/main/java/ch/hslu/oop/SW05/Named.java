package ch.hslu.oop.SW05;

/*
Frage war:
Jemand kommt auf die Idee ein Named-Interface zu definieren, mit welchem beliebige
Objekte einen Namen (Methoden void setName(final String name) und
String getName()) erhalten können. Welche Möglichkeiten haben Sie, dies für Ihre
«geschalteten» Klassen oder Interfaces zu nutzen?

Antwort:
Ehm ja, entweder Switchable implementiert Named aber das macht nicht wirklich Sinn, denn man braucht keinen Namen zum
Eingeschalten werden. Grundsätzlich macht es Sinn die Klassen direkt Named zu implementieren lassen.
Man könnte auch NamedSwitchable machen, die von beiden Interfaces erbt aber der Sinn.. naja.
 */
public interface Named {
    void setName(final String name); // final serves no purpose here IIRC: https://stackoverflow.com/q/5380177/10883465
    String getName();
}
