import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class LoginBackgroundPanel extends JPanel {
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint gp = new GradientPaint(0, 0, new Color(66, 133, 244), 0, getHeight(), new Color(219, 68, 55));
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}

public class LoginInterface extends JFrame implements ActionListener {
    private JTextField nom;
    private JPasswordField motpass;
    private JLabel lblmsg;
    private UtilisateurDAO utilisateurDAO;

    public LoginInterface() {

        utilisateurDAO = new UtilisateurDAO();

        setTitle("Login");
        setSize(550, 300);
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

        JButton btnLogin = new JButton("Connexion");
        btnLogin.setBounds(200, 150, 120, 30);

        JButton btnInscription = new JButton("S'inscrire");
        btnInscription.setBounds(330, 150, 120, 30);

        lblmsg = new JLabel();
        lblmsg.setForeground(Color.YELLOW);
        lblmsg.setBounds(200, 190, 300, 25);

        panel.add(lblNom);
        panel.add(nom);
        panel.add(lblMotpass);
        panel.add(motpass);
        panel.add(btnLogin);
        panel.add(btnInscription);
        panel.add(lblmsg);

        btnLogin.addActionListener(this);
        btnInscription.addActionListener(e -> {
            new InscriptionInterface().setVisible(true);
            dispose();
        });
    }

    public void actionPerformed(ActionEvent e) {
        String username = nom.getText();
        String password = new String(motpass.getPassword());

        Utilisateur utilisateur = utilisateurDAO.authentifier(username, password);

        if (utilisateur != null) {
            new ContactInterface(utilisateur.getId()).setVisible(true);
            dispose();
        } else {
            lblmsg.setText("Nom d'utilisateur ou mot de passe incorrect !");
        }
    }
}