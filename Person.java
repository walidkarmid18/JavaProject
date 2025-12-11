// Person.java
// Walid Karmid
// OCCC 2025
import java.io.Serializable;

public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(Person p) {
        this.firstName = p.firstName;
        this.lastName = p.lastName;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public void eat(){ System.out.println( firstName + ", " + lastName + ": is eating... !" ); }
    public void sleep(){ System.out.println( getClass().getSimpleName() + " is sleeping... !" ); }
    public void play(){ System.out.println( getClass().getSimpleName() + " is playing... !" ); }
    public void run(){ System.out.println( getClass().getSimpleName() + " is running... !" ); }

    public String toString() {
        return lastName + ", " + firstName;
    }

    public boolean equals(Person p) {
        if (p == null) return false;
        if (this.firstName == null || this.lastName == null) return false;
        return this.firstName.equalsIgnoreCase(p.firstName) && this.lastName.equalsIgnoreCase(p.lastName);
    }
}
