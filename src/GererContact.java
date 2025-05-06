import java.util.ArrayList;

public class GererContact {
    private ArrayList<Contact> contacts;
    private ContactDAO contactDAO;

    public GererContact(int utilisateurId) {
        this.contacts = new ArrayList<>();
        this.contactDAO = new ContactDAO(utilisateurId);
        chargerContacts();
    }

    private void chargerContacts() {
        this.contacts = contactDAO.getAllContacts();
    }

    public void ajouter(Contact contact) {
        if (contactDAO.addContact(contact)) {
            chargerContacts();
        }
    }

    public void modifier(int index, Contact contact) {
        if (index >= 0 && index < contacts.size()) {

            contact.setId(contacts.get(index).getId());

            if (contactDAO.updateContact(contact)) {
                chargerContacts();
            }
        }
    }

    public void supprimer(int index) {
        if (index >= 0 && index < contacts.size()) {
            int contactId = contacts.get(index).getId();

            if (contactDAO.deleteContact(contactId)) {
                chargerContacts();
            }
        }
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public ArrayList<Contact> rechercherParNom(String nom) {
        return contactDAO.searchContactsByName(nom);
    }
}