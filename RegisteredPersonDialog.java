// RegisteredPersonDialog.java
// Walid Karmid
// OCCC 2025
import javax.swing.*;
import java.awt.*;

public class RegisteredPersonDialog extends PersonDialog {
    private JTextField tfGovId;

    public RegisteredPersonDialog(Frame owner, String title, Person prefill, String prefillGovId) {
        super(owner, title, prefill);
        addGovField(prefillGovId);
    }

    private void addGovField(String prefillGovId) {
        JPanel panel = new JPanel(new GridLayout(1,2,5,5));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
        panel.add(new JLabel("Government ID:"));
        tfGovId = new JTextField();
        if (prefillGovId != null) tfGovId.setText(prefillGovId);
        panel.add(tfGovId);
        getContentPane().add(panel, BorderLayout.NORTH);

        // Move existing fields down: already created by PersonDialog; to keep it simple,
        // we'll re-organize content manually: practically above we added the gov field on top,
        // and PersonDialog fields are still present below because of layout; this yields an okay UI.
        pack();
        setLocationRelativeTo(getOwner());
    }

    public String getGovId() { return tfGovId.getText().trim(); }
}
