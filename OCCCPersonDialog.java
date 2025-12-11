// OCCCPersonDialog.java
// Walid Karmid
// OCCC 2025
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OCCCPersonDialog extends RegisteredPersonDialog {
    private JTextField tfStudentId;
    private JTextField tfDay, tfMonth, tfYear;

    public OCCCPersonDialog(Frame owner, String title, Person prefill, String prefillGovId, String prefillStudentId) {
        super(owner, title, prefill, prefillGovId);
        addOCCCFields(prefillStudentId);
    }

    // private void addOCCCFields(String prefillStudentId) {
    //     JPanel p = new JPanel(new GridLayout(2,2,5,5));
    //     p.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    //     p.add(new JLabel("Student ID:"));
    //     tfStudentId = new JTextField();
    //     if (prefillStudentId != null) tfStudentId.setText(prefillStudentId);
    //     p.add(tfStudentId);

    //     p.add(new JLabel("DOB (day month year):"));
    //     JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
    //     tfDay = new JTextField(2);
    //     tfMonth = new JTextField(2);
    //     tfYear = new JTextField(4);
    //     dobPanel.add(tfDay);
    //     dobPanel.add(new JLabel("/"));
    //     dobPanel.add(tfMonth);
    //     dobPanel.add(new JLabel("/"));
    //     dobPanel.add(tfYear);
    //     p.add(dobPanel);

    //     getContentPane().add(p, BorderLayout.SOUTH);

    //     pack();
    //     setLocationRelativeTo(getOwner());
    // }



private void addOCCCFields(String prefillStudentId) {
    // Main panel with vertical layout
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // --- Student ID panel ---
    JPanel studentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));

    mainPanel.add(Box.createVerticalStrut(30)); // spacing between rows

    studentPanel.add(new JLabel("Student ID:"));
    tfStudentId = new JTextField(15);
    if (prefillStudentId != null) {
        tfStudentId.setText(prefillStudentId);
    }
    studentPanel.add(tfStudentId);

    mainPanel.add(studentPanel);
    mainPanel.add(Box.createVerticalStrut(10)); // spacing between rows

    // --- DOB panel ---
    JPanel dobPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
    dobPanel.add(new JLabel("DOB (day / month / year):"));

    tfDay = new JTextField(2);
    tfMonth = new JTextField(2);
    tfYear = new JTextField(4);

    dobPanel.add(tfDay);
    dobPanel.add(new JLabel("/"));
    dobPanel.add(tfMonth);
    dobPanel.add(new JLabel("/"));
    dobPanel.add(tfYear);

    mainPanel.add(dobPanel);

    // Add main panel to dialog/frame
    getContentPane().add(mainPanel, BorderLayout.SOUTH);

    pack();
    setLocationRelativeTo(getOwner());
}








    public String getStudentId() { return tfStudentId.getText().trim(); }

    /**
     * Validate DOB fields and return an OCCCDate or null if none entered.
     * If invalid, throws OCCCDateException for caller to show message.
     */
    public OCCCDate getDOB() throws OCCCDateException {
        String sd = tfDay.getText().trim();
        String sm = tfMonth.getText().trim();
        String sy = tfYear.getText().trim();
        if (sd.isEmpty() && sm.isEmpty() && sy.isEmpty()) return null;
        try {
            int d = Integer.parseInt(sd);
            int m = Integer.parseInt(sm);
            int y = Integer.parseInt(sy);
            return new OCCCDate(d, m, y);
        } catch (NumberFormatException nfe) {
            throw new OCCCDateException("Invalid Date!");
        }
    }

    public void clearDOBFields() {
        tfDay.setText("");
        tfMonth.setText("");
        tfYear.setText("");
    }
}
