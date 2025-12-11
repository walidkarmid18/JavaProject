// PersonDialog.java
// Walid Karmid
// OCCC 2025
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PersonDialog extends JDialog {
    protected JTextField tfFirst;
    protected JTextField tfLast;
    protected boolean confirmed = false;

    public PersonDialog(Frame owner, String title, Person prefill) {
        super(owner, title, true);
        init(prefill);
    }

    private void init(Person prefill) {
        setLayout(new BorderLayout());
        JPanel fields = new JPanel(new GridLayout(2,2,5,5));
        fields.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        fields.add(new JLabel("First name:"));
        tfFirst = new JTextField();
        fields.add(tfFirst);
        fields.add(new JLabel("Last name:"));
        tfLast = new JTextField();
        fields.add(tfLast);

        if (prefill != null) {
            tfFirst.setText(prefill.getFirstName());
            tfLast.setText(prefill.getLastName());
        }

        add(fields, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");
        buttons.add(ok);
        buttons.add(cancel);
        add(buttons, BorderLayout.SOUTH);

        ok.addActionListener(e -> {
            if (tfFirst.getText().trim().isEmpty() || tfLast.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both first and last name.");
                return;
            }
            confirmed = true;
            setVisible(false);
        });

        cancel.addActionListener(e -> {
            confirmed = false;
            setVisible(false);
        });

        pack();
        setLocationRelativeTo(getOwner());
    }

    public boolean isConfirmed() { return confirmed; }
    public String getFirstName() { return tfFirst.getText().trim(); }
    public String getLastName() { return tfLast.getText().trim(); }
}
