/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import metier.modele.Etablissement;
/**
 *
 * @author ytaharaste
 */
@Entity
public class Eleve extends Personne{
    
    @Temporal(TemporalType.DATE)  // spécifie que la date est uniquement la partie date (pas d'heure)
    private Date dateNaissance;
    private Long niveau;
    @ManyToOne
    private Etablissement etablissement;
    
    @OneToMany(mappedBy="eleve")
    private List<Soutien> soutiens;

    @Override
    public int hashCode() {
        int hash = 7;
     
        hash = 79 * hash + Objects.hashCode(this.dateNaissance);
        hash = 79 * hash + Objects.hashCode(this.niveau);
        hash = 79 * hash + Objects.hashCode(this.etablissement);
        hash = 79 * hash + Objects.hashCode(this.soutiens);
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
        final Eleve other = (Eleve) obj;
      
        if (!Objects.equals(this.dateNaissance, other.dateNaissance)) {
            return false;
        }
        if (!Objects.equals(this.niveau, other.niveau)) {
            return false;
        }
        if (!Objects.equals(this.etablissement, other.etablissement)) {
            return false;
        }
        if (!Objects.equals(this.soutiens, other.soutiens)) {
            return false;
        }
        return true;
    }

    public Eleve() {
    }
    
    public Eleve(String dateNaissance, Long niveau, String nom, String prenom, String mail, String motDePasse ) throws IOException, ParseException {
        super(nom, prenom, mail, motDePasse);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.dateNaissance = sdf.parse(dateNaissance);
        
        this.niveau = niveau;
        this.etablissement = null;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(dateNaissance);
                
        return "Eleve{" + super.toString() + ", dateNaissance=" + formattedDate + ", niveau=" + niveau + ", etablissement=" + etablissement  + '}';
    }
    
    public Date getDateNaissance() {
        return dateNaissance;
    }

    public Long getNiveau() {
        return niveau;
    }
    
    public Etablissement getEtablissement() {
        return etablissement;
    }

  
    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setniveau(Long niveau) {
        this.niveau = niveau;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

   
    
}
