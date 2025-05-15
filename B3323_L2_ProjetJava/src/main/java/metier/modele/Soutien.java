package metier.modele;


import java.sql.Date;
import java.sql.Time;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ytaharaste
 */
@Entity
public class Soutien {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private Date date;
    private Time heureDemande;
    private Time heureDebut;
    
    private String lienVisio;
    private Long noteEleve;
    private String retourIntervenant;
    private Long duree; // duree en minutes du soutien
    private String description;
    private Matiere matiere;
    
    @ManyToOne
    private Intervenant intervenant;
    
    @ManyToOne
    private Eleve eleve;

    public Soutien() {
    }
    
    public Soutien(Date date, Time heureDemande, String description, Matiere matiere) {
        this.date = date;
        this.heureDemande = heureDemande;
        this.description = description;
        this.matiere = matiere;
    }
    
    public void setHeureDebut(Time heureDebut) {
        this.heureDebut = heureDebut;
    }

    public void setHeureDemande(Time heureDemande) {
        this.heureDemande = heureDemande;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }

    public void setLienVisio(String lienVisio) {
        this.lienVisio = lienVisio;
    }

    public void setNoteEleve(Long noteEleve) {
        this.noteEleve = noteEleve;
    }

    public void setRetourIntervenant(String retourIntervenant) {
        this.retourIntervenant = retourIntervenant;
    }

    public void setDuree(Long duree) {
        this.duree = duree;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }
    
    public String getLienVisio() {
        return lienVisio;
    }

    public Matiere getMatiere() {
        return matiere;
    }
    
    public Time getHeureDebut() {
        return heureDebut;
    }

    public Time getHeureDemande() {
        return heureDemande;
    }

    public Date getDate() {
        return date;
    }

    public String getlienVisio() {
        return lienVisio;
    }

    public Long getNoteEleve() {
        return noteEleve;
    }

    public String getRetourIntervenant() {
        return retourIntervenant;
    }

    public Long getDuree() {
        return duree;
    }

    public String getDescription() {
        return description;
    }

    public Intervenant getIntervenant() {
        return intervenant;
    }

    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    
    @Override
    public String toString() {
        return "Soutien{" + "date=" + date + ", heureDemande=" + heureDemande + ", heureDebut=" + heureDebut + ", lienVisio=" + lienVisio + ", noteEleve=" + noteEleve + ", retourIntervenant=" + retourIntervenant + ", duree=" + duree + ", description=" + description + ", matiere=" + matiere + ", intervenant=" + intervenant + ", eleve=" + eleve + '}';
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Soutien other = (Soutien) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}