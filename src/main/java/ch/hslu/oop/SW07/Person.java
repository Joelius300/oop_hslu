package ch.hslu.oop.SW07;

import java.util.Objects;

public class Person {
    private final long id;
    private String firstName;
    private String lastName;

    public Person(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    // This equals contract specifies that every Person,
    // no matter their subtype and name, is determined solely
    // by their id. To ensure subclasses comply with this
    // contract, the equals and hashCode methods are sealed.
    @Override
    public final boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof Person that))
            return false;

        return this.id == that.id;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id);
    }
}
