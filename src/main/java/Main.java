import model.Livre;
import model.Membre;
import model.Emprunt;
import dao.LivreDAO;
import dao.MembreDAO;
import dao.EmpruntDAO;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principale de l'application de gestion de bibliothèque.
 * Fournit un menu interactif en ligne de commande.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LivreDAO livreDAO = new LivreDAO();
    private static final MembreDAO membreDAO = new MembreDAO();
    private static final EmpruntDAO empruntDAO = new EmpruntDAO();

    public static void main(String[] args) {
        System.out.println("🎉 Bienvenue dans la Gestion de Bibliothèque !");
        int choix;
        do {
            afficherMenu();
            choix = lireEntier();
            traiterChoix(choix);
        } while (choix != 6);
        System.out.println("👋 Au revoir !");
        scanner.close();
    }

    private static void afficherMenu() {
        System.out.println("\n=== 📚 MENU PRINCIPAL ===");
        System.out.println("1 - Ajouter un livre");
        System.out.println("2 - Rechercher un livre");
        System.out.println("3 - Inscrire un membre");
        System.out.println("4 - Enregistrer un emprunt");
        System.out.println("5 - Afficher les emprunts en retard");
        System.out.println("6 - Quitter");
        System.out.print("➡️ Votre choix : ");
    }

    private static int lireEntier() {
        while (!scanner.hasNextInt()) {
            System.out.print("⚠️ Veuillez entrer un nombre : ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void traiterChoix(int choix) {
        switch (choix) {
            case 1 -> ajouterLivre();
            case 2 -> rechercherLivre();
            case 3 -> inscrireMembre();
            case 4 -> enregistrerEmprunt();
            case 5 -> afficherEmpruntsEnRetard();
            case 6 -> System.out.println(" Fermeture...");
            default -> System.out.println(" ❌ Choix invalide. Veuillez réessayer.");
        }
    }

    private static void ajouterLivre() {
        System.out.println("\n➕ Ajout d'un livre");
        System.out.print("Titre : ");
        String titre = scanner.next();
        System.out.print("Auteur : ");
        String auteur = scanner.next();
        System.out.print("Catégorie : ");
        String categorie = scanner.next();
        System.out.print("Nombre d'exemplaires : ");
        int nb = lireEntier();

        Livre livre = new Livre(0, titre, auteur, categorie, nb); // id=0 → généré par DB
        livreDAO.ajouterLivre(livre);
        System.out.println("✅ Livre ajouté avec ID = " + livre.getId());
    }

    private static void rechercherLivre() {
        System.out.println("\n🔍 Recherche de livre");
        System.out.print("Entrez un mot-clé (titre) : ");
        String motCle = scanner.next();
        List<Livre> resultats = livreDAO.rechercherParTitre(motCle);
        if (resultats.isEmpty()) {
            System.out.println("❌ Aucun livre trouvé.");
        } else {
            System.out.println("✅ " + resultats.size() + " résultat(s) :");
            resultats.forEach(l -> System.out.println(" • " + l.getTitre() + " par " + l.getAuteur()));
        }
    }

    private static void inscrireMembre() {
        System.out.println("\n📝 Inscription d'un membre");
        System.out.print("Nom : ");
        String nom = scanner.next();
        System.out.print("Prénom : ");
        String prenom = scanner.next();
        System.out.print("Email : ");
        String email = scanner.next();

        Membre membre = new Membre(0, nom, prenom, email, LocalDate.now());
        membreDAO.inscrireMembre(membre);
        System.out.println("✅ Membre inscrit avec ID = " + membre.getId());
    }

    private static void enregistrerEmprunt() {
        System.out.println("\n📥 Enregistrement d'un emprunt");
        System.out.print("ID du membre : ");
        int membreId = lireEntier();
        System.out.print("ID du livre : ");
        int livreId = lireEntier();
        System.out.print("Date d'emprunt (ex: 2026-01-07) ou appuyez sur Entrée pour aujourd'hui : ");
        scanner.nextLine(); // consommer \n
        String dateStr = scanner.nextLine().trim();
        LocalDate dateEmprunt = dateStr.isEmpty() ? LocalDate.now() : LocalDate.parse(dateStr);
        LocalDate dateRetour = dateEmprunt.plusDays(14); // prêt de 14 jours

        Emprunt emprunt = new Emprunt(membreId, livreId, dateEmprunt, dateRetour);
        empruntDAO.enregistrerEmprunt(emprunt);
        System.out.println("✅ Emprunt enregistré avec ID = " + emprunt.getIdEmprunt() +
                " (retour prévu le " + dateRetour + ")");
    }

    private static void afficherEmpruntsEnRetard() {
        System.out.println("\n❗ Emprunts en retard");
        List<Emprunt> retards = empruntDAO.getEmpruntsEnRetard();
        if (retards.isEmpty()) {
            System.out.println("✅ Aucun emprunt en retard.");
        } else {
            System.out.println("⚠️ " + retards.size() + " emprunt(s) en retard :");
            for (Emprunt e : retards) {
                long jours = e.getJoursRetard();
                double penalite = e.calculerPenalite();
                System.out.printf(" • ID=%d, Membre=%d, Livre=%d, Retard=%d j, Pénalité=%.0f F CFA%n",
                        e.getIdEmprunt(), e.getMembreId(), e.getLivreId(), jours, penalite);
            }
        }
    }
}