// OCCCPerson.java
// Walid Karmid
// OCCC 2025
import java.io.Serializable;

public class OCCCPerson extends RegisteredPerson implements Serializable {
    private static final long serialVersionUID = 1L;

    private String studentID;

    public OCCCPerson(String firstName, String lastName, String govID, String studentID) {
        super(firstName, lastName, govID);
        this.studentID = studentID;
    }

    public OCCCPerson(RegisteredPerson p, String studentID) {
        super(p);
        this.studentID = studentID;
    }

    public OCCCPerson(OCCCPerson p) {
        super(p);
        this.studentID = p.studentID;
    }

    public String getStudentID() { return studentID; }

    public boolean equals(OCCCPerson p) {
        if (p == null) return false;
        return super.equals(p) && (this.studentID == null ? p.studentID == null : this.studentID.equalsIgnoreCase(p.studentID));
    }

    public boolean equals(RegisteredPerson p) { return super.equals(p); }
    public boolean equals(Person p) { return super.equals(p); }

    public String toString() {
        return super.toString() + " {" + studentID + "}";
    }
}
