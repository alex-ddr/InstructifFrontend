package metier.modele;

import java.io.IOException;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
public class Enseignant extends Intervenant {

    private String typeEtablissement ;

    
    public Enseignant() {}

    
    public Enseignant(Long nbSolicitation, String tel, String nom, String prenom, String mail, String motDePasse, List<Long> niveauCompetance, String typeEtablissement , String Type) throws IOException {
        super(nom, prenom, mail, motDePasse,nbSolicitation,tel,Type, niveauCompetance);
        this.typeEtablissement = typeEtablissement ;}

    public String getTypeEtablissement() {
        return typeEtablissement;
    }

    public void setTypeEtablissement(String typeEtablissement) {
        this.typeEtablissement = typeEtablissement;
    }

    // Méthode toString()
    @Override
    public String toString() {
        return "Enseignent{" +
                super.toString() +
                ", typeEtablissement='" + typeEtablissement + '\'' +
                '}';
    }

    
}

