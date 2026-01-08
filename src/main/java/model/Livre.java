package model;

public class Livre implements Affichable {
    private int id;
    private String titre;
    private String auteur;
    private String categorie;
    private int nombreExemplaires;

    public Livre(int id, String titre, String auteur, String categorie, int nombreExemplaires) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.categorie = categorie;
        this.nombreExemplaires = nombreExemplaires;
    }

    public Livre(String titre, String auteur, String categorie, int nombreExemplaires) {
        this(0, titre, auteur, categorie, nombreExemplaires);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }
    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public int getNombreExemplaires() { return nombreExemplaires; }
    public void setNombreExemplaires(int nombreExemplaires) { this.nombreExemplaires = nombreExemplaires; }

    @Override
    public void afficherDetails() {
        System.out.printf("Livre[ID=%d] : %s par %s (%s) - %d ex.%n",
                id, titre, auteur, categorie, nombreExemplaires);
    }

    public boolean estDisponible() {
        return nombreExemplaires > 0;
    }

    public void emprunter() {
        if (nombreExemplaires > 0) nombreExemplaires--;
    }

    public void retourner() {
        nombreExemplaires++;
    }
}
