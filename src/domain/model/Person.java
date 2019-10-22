package domain.model;

import util.ConsoleColor;

import java.time.LocalDate;
import java.util.Objects;

public class Person {
    private final String firstName;
    private final String lastName;
    private final Sex sex;
    private final LocalDate birthDate;

    public Person(String firstName, String lastName, Sex sex, LocalDate birthDate) {
        this.firstName = firstName.replaceAll("[\\s]+"," ");
        this.lastName = lastName.replaceAll("[\\s]+"," ");
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

    @Override
    public String toString() {
        return  "Person " + "[ " + firstName + " , " + lastName + " , " + sex + " , " + birthDate + " ]";
    }

    /*@Override
    public String toString() {
        return  ConsoleColor.PURPLE +"Person "
                + ConsoleColor.BLUE + "[ "
                + ConsoleColor.CYAN+ firstName
                + ConsoleColor.BLUE+ " , "
                + ConsoleColor.CYAN + lastName
                + ConsoleColor.BLUE+ " , "
                + ConsoleColor.CYAN + sex
                + ConsoleColor.BLUE + " , "
                + ConsoleColor.CYAN + birthDate
                + ConsoleColor.BLUE + " ]"
                + ConsoleColor.RESET;
    }*/
}
