/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Personne;
import metier.modele.Soutien;

/**
 *
 * @author ichebbi
 */
public class PersonneDAO {
    
    public Personne findByMailAndMdp (String mail , String mdp )
    {
        EntityManager em = JpaUtil.obtenirContextePersistance() ; 
        String request = "select p from Personne p where p.mail =:mail and p.motDePasse =:mdp";
        Query query = em.createQuery(request); 
        query.setParameter("mail", mail);  
        query.setParameter("mdp", mdp);  
        
        List<Personne> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
    
   
}
