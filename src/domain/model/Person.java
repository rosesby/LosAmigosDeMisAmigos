package domain.model;

import java.time.LocalDate;
import java.util.Objects;

public class Person {
    private String firstName;
    private String lastName;
    private Sex sex;
    private LocalDate birthDate;

    public Person(String firstName, String lastName, Sex sex, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.birthDate = birthDate;
    }

    public Person(Object firstName, Object lastName, Object sex, Object birthDate) {
        this((String)firstName, (String)lastName, (Sex)sex, (LocalDate)birthDate);
    }

    public Person(Object[] args){
        this(args[1], args[2], args[3], args[4]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return firstName.equals(person.firstName) &&
                lastName.equals(person.lastName) &&
                sex == person.sex &&
                birthDate.equals(person.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, sex, birthDate);
    }

    /*@Override
    public boolean equals(Object o) {
        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (getClass() != o.getClass())
            return false;
        Person person = (Person) o;
        // field comparison
        return Objects.equals(firstName, person.firstName)
                && Objects.equals(lastName, person.lastName)
                && Objects.equals(sex, person.sex)
                && Objects.equals(birthDate, person.birthDate);
    }*/

    @Override
    public String toString() {
        return "Person ( " + firstName + " , " + lastName + " , " + sex + " , " + birthDate + " )";
    }
}
