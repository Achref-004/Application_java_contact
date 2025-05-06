public class Utilisateur {
    private int id;
    private String nomUtil;
    private String motDePasse;

    public Utilisateur() {
    }
//Pour s'authentifier
    public Utilisateur(int id, String nomUtil, String motDePasse) {
        this.id = id;
        this.nomUtil = nomUtil;
        this.motDePasse = motDePasse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomUtil() {
        return nomUtil;
    }

    public void setNomUtil(String nomUtil) {
        this.nomUtil = nomUtil;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}