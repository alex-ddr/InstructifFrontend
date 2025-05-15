package dao;

import metier.modele.Intervenant;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import javax.persistence.Query;

public class IntervenantDao {

    // Méthode pour créer un intervenant
    public void create(Intervenant intervenant) {
        JpaUtil.obtenirContextePersistance().persist(intervenant);
    }
    
    public void update(Intervenant intervenant) {
        JpaUtil.obtenirContextePersistance().merge(intervenant);
    }

    // Méthode pour rechercher un intervenant par son ID
    public Intervenant findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Intervenant.class, id);
    }

    // Méthode pour récupérer tous les intervenants
    public List<Intervenant> findAll() {
        String jpql = "SELECT i FROM Intervenant i ORDER BY i.nom ASC";
        TypedQuery<Intervenant> query = JpaUtil.obtenirContextePersistance().createQuery(jpql, Intervenant.class);
        return query.getResultList();
    }
    
    public Intervenant findBestIntervenant (Long niveau)
    {
        EntityManager em = JpaUtil.obtenirContextePersistance() ; 
        String requete = "SELECT i FROM Intervenant i WHERE :niveau MEMBER OF i.niveauCompetance AND i.disponibilite = true ORDER BY i.nbSolicitation ASC";
        Query query = em.createQuery(requete); 
        query.setParameter("niveau", niveau);  
        query.setMaxResults(1);  
        List<Intervenant> result = query.getResultList(); 
        return result.isEmpty() ? null : result.get(0);
    }
    
   }
