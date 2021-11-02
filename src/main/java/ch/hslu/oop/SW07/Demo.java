package ch.hslu.oop.SW07;

import java.util.Locale;

public class Demo {
    public static void main(String[] args) {
        Person p1 = new Person(213452, "AA", "BB");
        Person p2 = new Person(213452, "CC", "DD");
        Person p3 = new Person(213452, "CC", "AA");
        Person p4 = new Person(213452, "BB", "BB");
        System.out.println(getComparisonDirect(p1, p2));
        System.out.println(getComparisonDirect(p2, p3));
        System.out.println(getComparisonDirect(p3, p4));
        System.out.println(getComparisonDirect(p4, p1));

        System.out.println(getComparisonLocaleSensitive(p1, p2));
        System.out.println(getComparisonLocaleSensitive(p2, p3));
        System.out.println(getComparisonLocaleSensitive(p3, p4));
        System.out.println(getComparisonLocaleSensitive(p4, p1));
    }

    private static String getComparisonDirect(Person a, Person b) {
        int compared = a.compareTo(b);

        return getComparison(a, b, compared);
    }

    private static String getComparisonLocaleSensitive(Person a, Person b) {
        int compared = new PersonNameComparator(Locale.GERMAN).compare(a, b);

        return getComparison(a, b, compared);
    }

    private static String getComparison(Person a, Person b, int compared) {
        char op;
        if (compared == 0) {
            op = '=';
        } else {
            op = compared < 0 ? '<' : '>';
        }

        return "'" + a.getFirstName() + " " + a.getLastName() + "' " +
                op + " " +
                "'" + b.getFirstName() + " " + b.getLastName() + "'";
    }
}
