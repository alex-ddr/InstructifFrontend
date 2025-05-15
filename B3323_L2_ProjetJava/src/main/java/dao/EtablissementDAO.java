/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import com.google.maps.model.LatLng;
import metier.modele.Etablissement ;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import dao.JpaUtil.* ; 
import java.io.IOException;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
/**
 *
 * @author ytaharaste
 */
public class EtablissementDAO {

    public void createEtablissment (Etablissement e )
    {
        JpaUtil.obtenirContextePersistance().persist(e);
    }
    
    public Etablissement findByCode (String code ) throws IOException{
        EntityManager em = JpaUtil.obtenirContextePersistance();
        Etablissement e;
        try {
            Query query = em.createQuery("SELECT e FROM Etablissement e WHERE e.code = :code");
            query.setParameter("code", code);
            e = (Etablissement) query.getSingleResult();
        } 
        catch (NoResultException ex) {
            e = null;
        }
    return e;
    }

    public List<Etablissement> findAll() {
        String s = "select e from Etablissement e order by e.code asc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Etablissement.class);
        return query.getResultList();
    }
    
    public List<LatLng> findAllPoints () {
        String s = "select distinct e.coord from Etablissement e order by e.code asc";
        Query query = JpaUtil.obtenirContextePersistance().createQuery(s);
        return query.getResultList();
    }
}
/*
 public Etablissement FindByCode (String code ) throws IOException{
        EntityManager em = JpaUtil.obtenirContextePersistance();
        Etablissement e;
        if (tableExists(em, "Etablissement")) { 
                Query query = em.createQuery("SELECT e FROM Etablissement e WHERE e.code = :code");
                query.setParameter("code", code);
                e = (Etablissement) query.getSingleResult();
                if (e == null)
                {
                    e = new Etablissement(code);
                    em.persist(e);
                }
        } 
        else {
            e = new Etablissement(code);
            em.persist(e); 
        }
        return e ;
    }
}*/