import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InscriptionInterface extends JFrame implements ActionListener {
    private JTextField nom;
    private JPasswordField motpass;
    private JPasswordField confirmMotpass;
    private JLabel lblmsg;
    private UtilisateurDAO utilisateurDAO;

    public InscriptionInterface() {

        utilisateurDAO = new UtilisateurDAO();

        setTitle("Inscription");
        setSize(550, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        LoginBackgroundPanel panel = new LoginBackgroundPanel();
        panel.setLayout(null);
        setContentPane(panel);

        JLabel lblNom = new JLabel("Nom d'utilisateur:");
        lblNom.setForeground(Color.WHITE);
        lblNom.setBounds(50, 50, 150, 25);
        nom = new JTextField();
        nom.setBounds(200, 50, 200, 25);

        JLabel lblMotpass = new JLabel("Mot de passe:");
        lblMotpass.setForeground(Color.WHITE);
        lblMotpass.setBounds(50, 100, 150, 25);
        motpass = new JPasswordField();
        motpass.setBounds(200, 100, 200, 25);

        JLabel lblConfirmMotpass = new JLabel("Confirmer mot de passe:");
        lblConfirmMotpass.setForeground(Color.WHITE);
        lblConfirmMotpass.setBounds(50, 150, 150, 25);
        confirmMotpass = new JPasswordField();
        confirmMotpass.setBounds(200, 150, 200, 25);

        JButton btnInscription = new JButton("S'inscrire");
        btnInscription.setBounds(200, 200, 120, 30);

        JButton btnRetour = new JButton("Retour");
        btnRetour.setBounds(70, 200, 120, 30);

        lblmsg = new JLabel();
        lblmsg.setForeground(Color.YELLOW);
        lblmsg.setBounds(200, 240, 300, 25);

        panel.add(lblNom);
        panel.add(nom);
        panel.add(lblMotpass);
        panel.add(motpass);
        panel.add(lblConfirmMotpass);
        panel.add(confirmMotpass);
        panel.add(btnInscription);
        panel.add(btnRetour);
        panel.add(lblmsg);

        btnInscription.addActionListener(this);
        btnRetour.addActionListener(e -> {
            new LoginInterface().setVisible(true);
            dispose();
        });
    }

    public void actionPerformed(ActionEvent e) {
        String username = nom.getText();
        String password = new String(motpass.getPassword());
        String confirmPassword = new String(confirmMotpass.getPassword());


        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            lblmsg.setText("Tous les champs sont obligatoires!");
            return;
        }


        if (!password.equals(confirmPassword)) {
            lblmsg.setText("Les mots de passe ne correspondent pas!");
            return;
        }

        // Verifier si l'utilisateur existe dja dans la base de donnee
        if (utilisateurDAO.utilisateurExiste(username)) {
            lblmsg.setText("Ce nom d'utilisateur existe déjà!");
            return;
        }


        Utilisateur nouvelUtilisateur = new Utilisateur();
        nouvelUtilisateur.setNomUtil(username);
        nouvelUtilisateur.setMotDePasse(password);
        // Ajouter l'utilisateur a la base de donnee avec l dao
        if (utilisateurDAO.ajouterUtilisateur(nouvelUtilisateur)) {
            JOptionPane.showMessageDialog(this, "Inscription réussie!", "Succès", JOptionPane.INFORMATION_MESSAGE);
            new LoginInterface().setVisible(true);
            dispose();
        } else {
            lblmsg.setText("Erreur lors de l'inscription!");
        }
    }
}