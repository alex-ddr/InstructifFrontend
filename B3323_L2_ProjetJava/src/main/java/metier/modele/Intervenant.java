package metier.modele;

import java.io.IOException;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Intervenant extends Personne {
    private Boolean disponibilite ;  
    private Long nbSolicitation;
    private String tel;
    private String type ; 
    
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> niveauCompetance;
    
    @OneToMany(mappedBy="intervenant")
    private List<Soutien> soutiens;
    
    public Intervenant() {}

    
    public Intervenant( String nom, String prenom, String mail, String motDePasse, Long nbSolicitation, String tel, String Type, List<Long> niveauCompetance) throws IOException {
        super(nom, prenom, mail, motDePasse);
        this.nbSolicitation = nbSolicitation;
        this.tel = tel;
        this.niveauCompetance = niveauCompetance;
        this.type = type ;
        this.disponibilite = true ; 
        
    }

    public void setDisponibilite(Boolean disponibilite) {
        this.disponibilite = disponibilite;
    }

    public void setSoutiens(List<Soutien> soutiens) {
        this.soutiens = soutiens;
    }

    public Boolean getDisponibilite() {
        return disponibilite;
    }

    public List<Soutien> getSoutiens() {
        return soutiens;
    }

     public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public Long getNbSolicitation() {
        return this.nbSolicitation;
    }

    public void setNbSolicitation(Long nbSolicitation) {
        this.nbSolicitation = nbSolicitation;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public List<Long> getNiveauCompetance() {
        return this.niveauCompetance;
    }

    public void setNiveauCompetance(List<Long> niveauCompetance) {
        this.niveauCompetance = niveauCompetance;
    }

    
    @Override
    public String toString() {
        return "Intervenant{" + 
                super.toString() +
                ", nbSolicitation=" + this.nbSolicitation +
                ", tel='" + this.tel + '\'' +
                ", niveauCompetance=" + this.niveauCompetance +
                ", type=" + this.type +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", mail='" + getMail() + '\''
                ;
    }
}

