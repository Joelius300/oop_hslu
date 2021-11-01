package ch.hslu.oop.SW07;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    @Test
    void personEqualsContract() {
        EqualsVerifier.forClass(Person.class)
            .withOnlyTheseFields("id")
            .verify();
    }
}