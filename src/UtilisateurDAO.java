import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurDAO {
    private Connection connection;

    public UtilisateurDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public Utilisateur authentifier(String nomUtil, String motDePasse) {
        String sql = "SELECT * FROM utilisateur WHERE nom_util = ? AND mot_de_passe = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nomUtil);
            pstmt.setString(2, motDePasse);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom_util"),
                        rs.getString("mot_de_passe")
                );
            }

            rs.close();
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'authentification: " + e.getMessage());
        }

        return null;
    }

    public boolean ajouterUtilisateur(Utilisateur utilisateur) {
        String sql = "INSERT INTO utilisateur (nom_util, mot_de_passe) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, utilisateur.getNomUtil());
            pstmt.setString(2, utilisateur.getMotDePasse());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'utilisateur: " + e.getMessage());
            return false;
        }
    }

    public boolean utilisateurExiste(String nomUtil) {
        String sql = "SELECT COUNT(*) FROM utilisateur WHERE nom_util = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nomUtil);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

            rs.close();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification d'utilisateur: " + e.getMessage());
        }

        return false;
    }
}