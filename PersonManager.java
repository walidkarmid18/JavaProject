// PersonManager.java
// Walid Karmid
// OCCC 2025
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

public class PersonManager extends JFrame {
    private DefaultListModel<Person> listModel = new DefaultListModel<>();
    private JList<Person> personJList = new JList<>(listModel);
    private File currentFile = null;
    private boolean unsavedChanges = false;

    // Menus
    private JMenuItem miSave;
    private JMenuItem miSaveAs;

    public PersonManager() {
        super("Person Manager - OCCC 2025");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(700, 450);
        initMenu();
        initMain();
        initWindowListener();
        setLocationRelativeTo(null);
    }

    private void initMenu() {
        JMenuBar mb = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem miNew = new JMenuItem("New");
        JMenuItem miOpen = new JMenuItem("Open...");
        miSave = new JMenuItem("Save");
        miSaveAs = new JMenuItem("Save as...");
        JMenuItem miExit = new JMenuItem("Exit");

        file.add(miNew);
        file.add(miOpen);
        file.add(miSave);
        file.add(miSaveAs);
        file.addSeparator();
        file.add(miExit);

        miSave.setEnabled(false); // nothing to save initially
        miSaveAs.setEnabled(false);

        miNew.addActionListener(e -> doNew());
        miOpen.addActionListener(e -> doOpen());
        miSave.addActionListener(e -> doSave());
        miSaveAs.addActionListener(e -> doSaveAs());
        miExit.addActionListener(e -> attemptExit());

        JMenu help = new JMenu("Help");
        JMenuItem miHelp = new JMenuItem("About / Help");
        miHelp.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "Person Manager\nOCCC 2025\nFeatures:\n• New/Open/Save/Save As\n• Add/Delete Persons\n• Create from selected\n• DOB validation via OCCCDate",
            "Help", JOptionPane.INFORMATION_MESSAGE));
        help.add(miHelp);

        mb.add(file);
        mb.add(help);
        setJMenuBar(mb);
    }

    private void initMain() {
        JPanel p = new JPanel(new BorderLayout());
        personJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        personJList.setVisibleRowCount(15);
        JScrollPane sp = new JScrollPane(personJList);
        p.add(sp, BorderLayout.CENTER);

        JPanel right = new JPanel(new GridLayout(0,1,8,8));
        JButton btnAddPerson = new JButton("Add Person");
        JButton btnAddRegistered = new JButton("Add RegisteredPerson");
        JButton btnAddOCCC = new JButton("Add OCCCPerson");
        JButton btnAddFromSel = new JButton("Create from Selected");
        JButton btnDelete = new JButton("Delete Selected");

        right.add(btnAddPerson);
        right.add(btnAddRegistered);
        right.add(btnAddOCCC);
        right.add(btnAddFromSel);
        right.add(btnDelete);

        p.add(right, BorderLayout.EAST);

        btnAddPerson.addActionListener(e -> createPerson(null));
        btnAddRegistered.addActionListener(e -> createRegisteredPerson(null));
        btnAddOCCC.addActionListener(e -> createOCCCPerson(null));
        btnAddFromSel.addActionListener(e -> {
            Person sel = personJList.getSelectedValue();
            if (sel == null) {
                JOptionPane.showMessageDialog(this, "Select a Person to create from.");
                return;
            }
            // Ask which subtype to create
            String[] options = {"Person", "RegisteredPerson", "OCCCPerson"};
            int choice = JOptionPane.showOptionDialog(this, "Create which subtype from selected?",
                    "Create from selected", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options, options[0]);
            if (choice == 0) createPerson(sel);
            else if (choice == 1) createRegisteredPerson(sel);
            else if (choice == 2) createOCCCPerson(sel);
        });

        btnDelete.addActionListener(e -> {
            Person sel = personJList.getSelectedValue();
            if (sel == null) {
                JOptionPane.showMessageDialog(this, "No selection to delete.");
                return;
            }
            int ans = JOptionPane.showConfirmDialog(this, "Delete " + sel + "?", "Delete", JOptionPane.YES_NO_OPTION);
            if (ans == JOptionPane.YES_OPTION) {
                listModel.removeElement(sel);
                setUnsaved(true);
            }
        });

        add(p);
    }

    private void initWindowListener() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                attemptExit();
            }
        });
    }

    private void attemptExit() {
        if (unsavedChanges) {
            int ans = JOptionPane.showConfirmDialog(this, "Save before exit?", "Unsaved Changes", JOptionPane.YES_NO_CANCEL_OPTION);
            if (ans == JOptionPane.CANCEL_OPTION || ans == JOptionPane.CLOSED_OPTION) return;
            if (ans == JOptionPane.YES_OPTION) {
                if (!doSave()) return; // if save failed or cancelled, don't exit
            }
        }
        dispose();
        System.exit(0);
    }

    private void doNew() {
        if (!checkSaveBeforeProceed()) return;
        listModel.clear();
        currentFile = null;
        setUnsaved(false);
    }

    private void doOpen() {
        if (!checkSaveBeforeProceed()) return;
        JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            try {
                java.util.ArrayList<Person> loaded = SerializationUtil.loadFromFile(f);
                listModel.clear();
                for (Person p : loaded) listModel.addElement(p);
                currentFile = f;
                setUnsaved(false);
                miSave.setEnabled(true);
                miSaveAs.setEnabled(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to load file: " + ex.getMessage());
            }
        }
    }

    private boolean doSave() {
        if (currentFile == null) {
            return doSaveAs();
        } else {
            try {
                ArrayList<Person> arr = new ArrayList<>();
                for (int i=0;i<listModel.size();i++) arr.add(listModel.get(i));
                SerializationUtil.saveToFile(currentFile, arr);
                setUnsaved(false);
                return true;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Save failed: " + ex.getMessage());
                return false;
            }
        }
    }

    private boolean doSaveAs() {
        JFileChooser fc = new JFileChooser();
        int result = fc.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fc.getSelectedFile();
            try {
                ArrayList<Person> arr = new ArrayList<>();
                for (int i=0;i<listModel.size();i++) arr.add(listModel.get(i));
                SerializationUtil.saveToFile(currentFile, arr);
                setUnsaved(false);
                miSave.setEnabled(true);
                miSaveAs.setEnabled(true);
                return true;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Save failed: " + ex.getMessage());
                return false;
            }
        }
        return false; // user cancelled
    }

    private boolean checkSaveBeforeProceed() {
        if (unsavedChanges) {
            int ans = JOptionPane.showConfirmDialog(this, "Save before proceeding?", "Unsaved Changes", JOptionPane.YES_NO_CANCEL_OPTION);
            if (ans == JOptionPane.CANCEL_OPTION || ans == JOptionPane.CLOSED_OPTION) return false;
            if (ans == JOptionPane.YES_OPTION) {
                return doSave();
            }
            // NO -> continue without saving
        }
        return true;
    }

    private void createPerson(Person prefill) {
        // disable save while constructing
        setSaveEnabled(false);
        PersonDialog dlg = new PersonDialog(this, "Create Person", prefill);
        dlg.setVisible(true);
        if (dlg.isConfirmed()) {
            Person p = new Person(dlg.getFirstName(), dlg.getLastName());
            listModel.addElement(p);
            setUnsaved(true);
            miSaveAs.setEnabled(true);
        }
        setSaveEnabled(true);
    }

    private void createRegisteredPerson(Person prefill) {
        setSaveEnabled(false);
        String preGov = null;
        if (prefill instanceof RegisteredPerson) preGov = ((RegisteredPerson)prefill).getGovermentID();
        RegisteredPersonDialog dlg = new RegisteredPersonDialog(this, "Create RegisteredPerson", prefill, preGov);
        dlg.setVisible(true);
        if (dlg.isConfirmed()) {
            RegisteredPerson rp = new RegisteredPerson(dlg.getFirstName(), dlg.getLastName(), dlg.getGovId());
            listModel.addElement(rp);
            setUnsaved(true);
            miSaveAs.setEnabled(true);
        }
        setSaveEnabled(true);
    }

    private void createOCCCPerson(Person prefill) {
        setSaveEnabled(false);
        String preGov = null; String preStud = null;
        if (prefill instanceof RegisteredPerson) preGov = ((RegisteredPerson)prefill).getGovermentID();
        if (prefill instanceof OCCCPerson) preStud = ((OCCCPerson)prefill).getStudentID();

        OCCCPersonDialog dlg = new OCCCPersonDialog(this, "Create OCCCPerson", prefill, preGov, preStud);
        dlg.setVisible(true);
        if (dlg.isConfirmed()) {
            try {
                OCCCDate dob = dlg.getDOB(); // validate
                // if thrown OCCCDateException, we catch below
                String studentId = dlg.getStudentId();
                String gov = dlg.getGovId();
                OCCCPerson op = new OCCCPerson(dlg.getFirstName(), dlg.getLastName(), gov, studentId);
                listModel.addElement(op);
                setUnsaved(true);
                miSaveAs.setEnabled(true);
            } catch (OCCCDateException ode) {
                JOptionPane.showMessageDialog(this, "Invalid Date!");
                dlg.clearDOBFields();
                // re-enable save and return without adding person.
            }
        }
        setSaveEnabled(true);
    }

    private void setUnsaved(boolean val) {
        unsavedChanges = val;
        miSave.setEnabled(val || currentFile != null);
        miSaveAs.setEnabled(!val ? (currentFile != null) : true);
        setTitle("Person Manager - " + (currentFile == null ? "Untitled" : currentFile.getName()) + (unsavedChanges ? " *" : ""));
    }

    private void setSaveEnabled(boolean enabled) {
        miSave.setEnabled(enabled && (unsavedChanges || currentFile != null));
        miSaveAs.setEnabled(enabled);
    }

    public static void mainFrame() {
        SwingUtilities.invokeLater(() -> {
            PersonManager pm = new PersonManager();
            pm.setVisible(true);
        });
    }
}
