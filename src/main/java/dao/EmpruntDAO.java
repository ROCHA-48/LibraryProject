package dao;

import model.Emprunt;
import dao.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpruntDAO {
    public void enregistrerEmprunt(Emprunt emprunt) {
        String sql = "INSERT INTO emprunts (membre_id, livre_id, date_emprunt, date_retour_prevue) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, emprunt.getMembreId());
            stmt.setInt(2, emprunt.getLivreId());
            stmt.setDate(3, Date.valueOf(emprunt.getDateEmprunt()));
            stmt.setDate(4, Date.valueOf(emprunt.getDateRetourPrevue()));
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                emprunt.setIdEmprunt(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Emprunt> getEmpruntsEnRetard() {
        List<Emprunt> retards = new ArrayList<>();
        String sql = "SELECT id, membre_id, livre_id, date_emprunt, date_retour_prevue " +
                     "FROM emprunts " +
                     "WHERE date_retour_effective IS NULL AND date_retour_prevue < CURRENT_DATE";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Emprunt e = new Emprunt(
                        rs.getInt("id"),
                        rs.getInt("membre_id"),
                        rs.getInt("livre_id"),
                        rs.getDate("date_emprunt").toLocalDate(),
                        rs.getDate("date_retour_prevue").toLocalDate(),
                        null  // dateRetourEffective = null (pas encore retourné)
                );
                retards.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retards;
    }
}
