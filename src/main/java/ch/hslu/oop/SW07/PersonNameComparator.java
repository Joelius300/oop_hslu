package ch.hslu.oop.SW07;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * A Comparator for comparing a {@link Person} to another by their
 * last- and then firstname. You must specify the locale used
 * for the comparison. If you want locale-insensitive comparison, use
 * {@link Locale#ROOT}. You may also specify that the comparison should
 * be case-insensitive which will capitalize the names before comparing them.
 * Note that by default it will be case-sensitive and thus use the default
 * comparison of the given locale.
 */
public class PersonNameComparator implements Comparator<Person> {
    private final boolean caseInsensitive;
    private final Locale locale;
    private final Collator collator;

    public PersonNameComparator(Locale locale) {
        this(locale, false);
    }

    public PersonNameComparator(Locale locale, boolean caseInsensitive) {
        if (locale == null)
            throw new IllegalArgumentException("locale cannot be null.");

        this.locale = locale;
        this.caseInsensitive = caseInsensitive;
        this.collator = Collator.getInstance(locale);

        /* Instead of calling toUpper before comparing, one could work with the strengths
           that Collator provides but since those may be different concepts depending on
           the locale, I chose to just call toUpper which has to handle those different concepts
           itself and that allows me to disregard them :)
        if (caseInsensitive != null) {
            collator.setStrength(caseInsensitive ? Collator.SECONDARY : Collator.TERTIARY);
        }
         */
    }

    @Override
    public int compare(Person o1, Person o2) {
        String thisName = o1.getLastName() + o1.getFirstName();
        String thatName = o2.getLastName() + o2.getFirstName();

        if (caseInsensitive) {
            thisName = thisName.toUpperCase(locale);
            thatName = thisName.toUpperCase(locale);
        }

        return collator.compare(thisName, thatName);
    }
}
