import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class ContactInterface extends JFrame implements ActionListener, DocumentListener, ListSelectionListener {
    private GererContact contactManager;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblMessage;
    private int utilisateurId;

    private JTextField tfNom;
    private JTextField tfPrenom;
    private JTextField tfTelephone;
    private JComboBox<String> cbCategorie;
    private JTextField tfRecherche;

    private JButton btnAjouter, btnModifier, btnSupprimer;

    public ContactInterface(int utilisateurId) {
        this.utilisateurId = utilisateurId;
        this.contactManager = new GererContact(utilisateurId);

        setTitle("Gestion des Contacts");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tableModel = new DefaultTableModel(new String[]{"Nom", "Prénom", "Téléphone", "Catégorie"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.getSelectionModel().addListSelectionListener(this);
        rafraichirTable();

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tfRecherche = new JTextField(15);
        searchPanel.add(new JLabel("Rechercher par nom:"));
        searchPanel.add(tfRecherche);
        tfRecherche.getDocument().addDocumentListener(this);

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        tfNom = new JTextField();
        tfPrenom = new JTextField();
        tfTelephone = new JTextField();
        cbCategorie = new JComboBox<>(new String[]{"Famille", "Amis", "Travail"});
        formPanel.add(new JLabel("Nom:"));
        formPanel.add(tfNom);
        formPanel.add(new JLabel("Prénom:"));
        formPanel.add(tfPrenom);
        formPanel.add(new JLabel("Téléphone:"));
        formPanel.add(tfTelephone);
        formPanel.add(new JLabel("Catégorie:"));
        formPanel.add(cbCategorie);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(formPanel, BorderLayout.SOUTH);

        btnAjouter = new JButton("Ajouter");
        btnModifier = new JButton("Modifier");
        btnSupprimer = new JButton("Supprimer");

        btnAjouter.addActionListener(this);
        btnModifier.addActionListener(this);
        btnSupprimer.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAjouter);
        buttonPanel.add(btnModifier);
        buttonPanel.add(btnSupprimer);

        lblMessage = new JLabel(" ");

        Font policeTexte = new Font("Arial", Font.PLAIN, 14);
        Font policeTitre = new Font("Arial", Font.BOLD, 16);

        tfNom.setFont(policeTexte);
        tfPrenom.setFont(policeTexte);
        tfTelephone.setFont(policeTexte);
        cbCategorie.setFont(policeTexte);
        tfRecherche.setFont(policeTexte);

        for (Component comp : formPanel.getComponents()) {
            if (comp instanceof JLabel) {
                comp.setFont(policeTexte);
            }
        }

        table.setFont(policeTexte);
        table.setRowHeight(25);
        table.getTableHeader().setFont(policeTitre);
        table.getTableHeader().setBackground(new Color(200, 220, 240));

        btnAjouter.setBackground(new Color(144, 238, 144));
        btnModifier.setBackground(new Color(255, 255, 102));
        btnSupprimer.setBackground(new Color(255, 99, 71));

        btnAjouter.setFont(policeTexte);
        btnModifier.setFont(policeTexte);
        btnSupprimer.setFont(policeTexte);

        lblMessage.setFont(new Font("Arial", Font.ITALIC, 12));
        lblMessage.setForeground(Color.RED);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(buttonPanel, BorderLayout.CENTER);
        southPanel.add(lblMessage, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        Color backgroundColor = new Color(230, 240, 255);

        searchPanel.setBackground(backgroundColor);
        formPanel.setBackground(backgroundColor);
        topPanel.setBackground(backgroundColor);
        buttonPanel.setBackground(backgroundColor);
        southPanel.setBackground(backgroundColor);

        getContentPane().setBackground(backgroundColor);


    }

    private void rafraichirTable() {
        tableModel.setRowCount(0);
        for (Contact contact : contactManager.getContacts()) {
            tableModel.addRow(new Object[]{
                    contact.getNom(),
                    contact.getPrenom(),
                    contact.getTelephone(),
                    contact.getCategorie()
            });
        }
    }

    private void filtrerContacts() {
        String txtRecherche = tfRecherche.getText().trim().toLowerCase();

        if (txtRecherche.isEmpty()) {
            rafraichirTable();
            return;
        }

        tableModel.setRowCount(0);
        for (Contact contact : contactManager.rechercherParNom(txtRecherche)) {
            tableModel.addRow(new Object[]{
                    contact.getNom(),
                    contact.getPrenom(),
                    contact.getTelephone(),
                    contact.getCategorie()
            });
        }
    }

    private void viderChamps() {
        tfNom.setText("");
        tfPrenom.setText("");
        tfTelephone.setText("");
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAjouter) {
            String nom = tfNom.getText();
            String prenom = tfPrenom.getText();
            String telephone = tfTelephone.getText();
            String categorie = (String) cbCategorie.getSelectedItem();
            if (!nom.isEmpty() && !prenom.isEmpty() && !telephone.isEmpty()) {
                Contact contact = new Contact(nom, prenom, telephone, categorie);
                contact.setUtilisateurId(utilisateurId);
                contactManager.ajouter(contact);
                rafraichirTable();
                viderChamps();
            } else {
                lblMessage.setText("Veuillez remplir tous les champs !");
            }
        }

        if (e.getSource() == btnModifier) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String nom = tfNom.getText();
                String prenom = tfPrenom.getText();
                String telephone = tfTelephone.getText();
                String categorie = (String) cbCategorie.getSelectedItem();
                if (!nom.isEmpty() && !prenom.isEmpty() && !telephone.isEmpty()) {
                    Contact contact = new Contact(nom, prenom, telephone, categorie);
                    contact.setUtilisateurId(utilisateurId);
                    contactManager.modifier(selectedRow, contact);
                    rafraichirTable();
                    viderChamps();
                } else {
                    lblMessage.setText("Veuillez remplir tous les champs !");
                }
            } else {
                lblMessage.setText("Sélectionnez un contact à modifier !");
            }
        }

        if (e.getSource() == btnSupprimer) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                contactManager.supprimer(selectedRow);
                rafraichirTable();
                viderChamps();
            } else {
                lblMessage.setText("Sélectionnez un contact à supprimer !");
            }
        }
    }

    public void insertUpdate(DocumentEvent e) {
        filtrerContacts();
    }

    public void removeUpdate(DocumentEvent e) {
        filtrerContacts();
    }

    public void changedUpdate(DocumentEvent e) {
        filtrerContacts();
    }

    public void valueChanged(ListSelectionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            tfNom.setText((String) tableModel.getValueAt(selectedRow, 0));
            tfPrenom.setText((String) tableModel.getValueAt(selectedRow, 1));
            tfTelephone.setText((String) tableModel.getValueAt(selectedRow, 2));
            cbCategorie.setSelectedItem((String) tableModel.getValueAt(selectedRow, 3));
        }
    }
}