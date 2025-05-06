import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContactDAO {
    private Connection connection;
    private int utilisateurId;

    public ContactDAO(int utilisateurId) {
        this.connection = DatabaseConnection.getConnection();
        this.utilisateurId = utilisateurId;
    }

    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contact WHERE utilisateur_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, utilisateurId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Contact contact = new Contact(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("telephone"),
                        rs.getString("categorie"),
                        rs.getInt("utilisateur_id")
                );
                contacts.add(contact);
            }

            rs.close();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des contacts: " + e.getMessage());
        }

        return contacts;
    }

    public ArrayList<Contact> searchContactsByName(String nom) {
        ArrayList<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contact WHERE utilisateur_id = ? AND nom LIKE ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, utilisateurId);
            pstmt.setString(2, "%" + nom + "%");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Contact contact = new Contact(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("telephone"),
                        rs.getString("categorie"),
                        rs.getInt("utilisateur_id")
                );
                contacts.add(contact);
            }

            rs.close();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche des contacts: " + e.getMessage());
        }

        return contacts;
    }

    public boolean addContact(Contact contact) {
        String sql = "INSERT INTO contact (nom, prenom, telephone, categorie, utilisateur_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, contact.getNom());
            pstmt.setString(2, contact.getPrenom());
            pstmt.setString(3, contact.getTelephone());
            pstmt.setString(4, contact.getCategorie());
            pstmt.setInt(5, utilisateurId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du contact: " + e.getMessage());
            return false;
        }
    }

    public boolean updateContact(Contact contact) {
        String sql = "UPDATE contact SET nom = ?, prenom = ?, telephone = ?, categorie = ? WHERE id = ? AND utilisateur_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, contact.getNom());
            pstmt.setString(2, contact.getPrenom());
            pstmt.setString(3, contact.getTelephone());
            pstmt.setString(4, contact.getCategorie());
            pstmt.setInt(5, contact.getId());
            pstmt.setInt(6, utilisateurId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du contact: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteContact(int contactId) {
        String sql = "DELETE FROM contact WHERE id = ? AND utilisateur_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, contactId);
            pstmt.setInt(2, utilisateurId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du contact: " + e.getMessage());
            return false;
        }
    }
}