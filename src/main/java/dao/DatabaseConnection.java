package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/bibliotheque";
    private static final String USER = "postgres";
    private static final String PASSWORD = "biblio123";

    // Désactive le singleton → nouvelle connexion à chaque fois
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    /**
     * Crée les tables "livres", "membres" et "emprunts" si elles n'existent pas.
     */
    private static void initTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            // Table livres
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS livres (
                    id SERIAL PRIMARY KEY,
                    titre VARCHAR(255) NOT NULL,
                    auteur VARCHAR(255) NOT NULL,
                    categorie VARCHAR(100),
                    nombre_exemplaires INT DEFAULT 1
                )
            """);

            // Table membres
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS membres (
                    id SERIAL PRIMARY KEY,
                    nom VARCHAR(100) NOT NULL,
                    prenom VARCHAR(100) NOT NULL,
                    email VARCHAR(150) UNIQUE,
                    date_adhesion DATE NOT NULL
                )
            """);

            // Table emprunts
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS emprunts (
                    id SERIAL PRIMARY KEY,
                    membre_id INT NOT NULL REFERENCES membres(id),
                    livre_id INT NOT NULL REFERENCES livres(id),
                    date_emprunt DATE NOT NULL,
                    date_retour_prevue DATE NOT NULL,
                    date_retour_effective DATE
                )
            """);
        }
    }


}
