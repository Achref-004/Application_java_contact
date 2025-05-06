public class Contact {
    private int id;
    private String nom;
    private String prenom;
    private String telephone;
    private String categorie;
    private int utilisateurId;

    public Contact(String nom, String prenom, String telephone, String categorie) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.categorie = categorie;
    }

    public Contact(int id, String nom, String prenom, String telephone, String categorie, int utilisateurId) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.categorie = categorie;
        this.utilisateurId = utilisateurId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }
}