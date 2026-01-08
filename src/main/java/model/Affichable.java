package model;

/**
 * Interface pour permettre l'affichage polymorphique des entités.
 * Implémentée par Livre et Membre.
 */
public interface Affichable {
    /**
     * Affiche les détails de l'objet dans la console.
     */
    void afficherDetails();
}