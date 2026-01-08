package dao;

import model.Livre;
import dao.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDAO {
    public void ajouterLivre(Livre livre) {
        String sql = "INSERT INTO livres (titre, auteur, categorie, nombre_exemplaires) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, livre.getTitre());
            stmt.setString(2, livre.getAuteur());
            stmt.setString(3, livre.getCategorie());
            stmt.setInt(4, livre.getNombreExemplaires());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                livre.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Livre> rechercherParTitre(String titre) {
        List<Livre> resultats = new ArrayList<>();
        String sql = "SELECT * FROM livres WHERE LOWER(titre) LIKE LOWER(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + titre + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Livre l = new Livre(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("auteur"),
                        rs.getString("categorie"),
                        rs.getInt("nombre_exemplaires")
                );
                resultats.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultats;
    }
}
