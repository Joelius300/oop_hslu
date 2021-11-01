package ch.hslu.oop.SW07;

import java.util.Objects;

public class Person implements Comparable<Person> {
    private final long id;
    private String firstName;
    private String lastName;

    public Person(long id, String firstName, String lastName) {
        if (id < 0)
            throw new IllegalArgumentException("id cannot be negative.");

        if (firstName == null || firstName.isEmpty())
            throw new IllegalArgumentException("firstName cannot be null or empty.");

        if (lastName == null || lastName.isEmpty())
            throw new IllegalArgumentException("firstName cannot be null or empty.");

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

    // I think of the id as non-incremental but
    // randomly generated and assigned so sorting
    // by the id doesn't make sense.
    // Next most "natural" order would be the name but,
    // I think putting this logic into Person itself isn't great.
    @Override
    public int compareTo(Person o) {
        // One of the reasons this logic shouldn't go in here is the
        // lack of localization which could be implemented in a Comparator.
        final String thisName = getLastName() + getFirstName();
        final String thatName = o.getLastName() + o.getFirstName();

        return thisName.compareToIgnoreCase(thatName);
    }
}
