package model;

import java.time.LocalDate;

public class Emprunt {
    private int idEmprunt;
    private int membreId;
    private int livreId;
    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourEffective;

    public Emprunt(int idEmprunt, int membreId, int livreId,
                   LocalDate dateEmprunt, LocalDate dateRetourPrevue, LocalDate dateRetourEffective) {
        this.idEmprunt = idEmprunt;
        this.membreId = membreId;
        this.livreId = livreId;
        this.dateEmprunt = dateEmprunt;
        this.dateRetourPrevue = dateRetourPrevue;
        this.dateRetourEffective = dateRetourEffective;
    }

    public Emprunt(int membreId, int livreId, LocalDate dateEmprunt, LocalDate dateRetourPrevue) {
        this(0, membreId, livreId, dateEmprunt, dateRetourPrevue, null);
    }

    public int getIdEmprunt() { return idEmprunt; }
    public void setIdEmprunt(int idEmprunt) { this.idEmprunt = idEmprunt; }
    public int getMembreId() { return membreId; }
    public void setMembreId(int membreId) { this.membreId = membreId; }
    public int getLivreId() { return livreId; }
    public void setLivreId(int livreId) { this.livreId = livreId; }
    public LocalDate getDateEmprunt() { return dateEmprunt; }
    public void setDateEmprunt(LocalDate dateEmprunt) { this.dateEmprunt = dateEmprunt; }
    public LocalDate getDateRetourPrevue() { return dateRetourPrevue; }
    public void setDateRetourPrevue(LocalDate dateRetourPrevue) { this.dateRetourPrevue = dateRetourPrevue; }
    public LocalDate getDateRetourEffective() { return dateRetourEffective; }
    public void setDateRetourEffective(LocalDate dateRetourEffective) { this.dateRetourEffective = dateRetourEffective; }

    public void marquerRetour() {
        this.dateRetourEffective = LocalDate.now();
    }

    public boolean estEnRetard() {
        return dateRetourEffective == null && LocalDate.now().isAfter(dateRetourPrevue);
    }

    public long getJoursRetard() {
        LocalDate fin = (dateRetourEffective != null) ? dateRetourEffective : LocalDate.now();
        return Math.max(0, java.time.temporal.ChronoUnit.DAYS.between(dateRetourPrevue, fin));
    }

    public double calculerPenalite() {
        return getJoursRetard() * 100.0;
    }
}
