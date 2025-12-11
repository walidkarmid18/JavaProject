// RegisteredPerson.java
// Walid Karmid
// OCCC 2025
import java.io.Serializable;

public class RegisteredPerson extends Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private String govId;

    public RegisteredPerson(String firstName, String lastname, String govId) {
        super(firstName, lastname);
        this.govId = govId;
    }

    public RegisteredPerson(Person p, String govID){
        super(p);
        this.govId = govID;
    }

    public RegisteredPerson(RegisteredPerson p) {
        super(p);
        this.govId = p.govId;
    }

    public String getGovermentID(){ return govId; }

    public boolean equals(RegisteredPerson p) {
        if (p == null) return false;
        return super.equals(p) && (this.govId == null ? p.govId == null : this.govId.equalsIgnoreCase(p.govId));
    }

    public boolean equals(Person p) { return super.equals(p); }

    public String toString() {
        return super.toString() + " [" + govId + " ]";
    }
}
