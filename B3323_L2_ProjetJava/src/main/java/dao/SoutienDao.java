/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import metier.modele.Eleve;
import metier.modele.Etablissement;
import metier.modele.Soutien;
import metier.modele.Intervenant;

/**
 *
 * @author ytaharaste
 */
public class SoutienDao {

    public void create(Soutien soutien) {
        JpaUtil.obtenirContextePersistance().persist(soutien);
    }
    
    public void update(Soutien soutien) {
        JpaUtil.obtenirContextePersistance().merge(soutien);
    }
    
    public Soutien findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Soutien.class, id);
    }

    public List<Soutien> findAll() {
        String s = "select s from Soutien s order by s.date asc, s.heureDemande asc";
        TypedQuery<Soutien> query = JpaUtil.obtenirContextePersistance().createQuery(s, Soutien.class);
        return query.getResultList();
    }
    
    public Soutien findActualSoutien(Intervenant intervenant) {
        EntityManager em = JpaUtil.obtenirContextePersistance() ; 
        String request = "select s from Soutien s where s.intervenant = :intervenant order by s.date desc, s.heureDemande desc";
        Query query = em.createQuery(request); 
        query.setParameter("intervenant", intervenant);  
        query.setMaxResults(1); 
        
        
        List<Soutien> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
    
    public Soutien findLastSoutienByEleve(Eleve eleve) {
        EntityManager em = JpaUtil.obtenirContextePersistance() ;
        String request = "SELECT s FROM Soutien s WHERE s.eleve = :eleve ORDER BY s.date DESC, s.heureDemande DESC";
        TypedQuery<Soutien> query = em.createQuery(request, Soutien.class);
        query.setParameter("eleve", eleve);
        query.setMaxResults(1);

        List<Soutien> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
    
    public List<Soutien> findByIntervenant (Intervenant intervenant)
    {
        EntityManager em = JpaUtil.obtenirContextePersistance() ; 
        String request = "select s from Soutien s where s.intervenant = :intervenant order by s.date desc, s.heureDemande desc";
        Query query = em.createQuery(request); 
        query.setParameter("intervenant", intervenant);
        List<Soutien> soutiens = query.getResultList();
        return soutiens ; 
    }
    
    public List<Soutien> findByEleve (Eleve eleve)
    {
        EntityManager em = JpaUtil.obtenirContextePersistance() ; 
        String request = "select s from Soutien s where s.eleve = :eleve order by s.date desc, s.heureDemande desc";
        Query query = em.createQuery(request); 
        query.setParameter("eleve", eleve);
        List<Soutien> soutiens = query.getResultList();
        return soutiens ; 
    }
    
    public long countAll() 
    {
        EntityManager em = JpaUtil.obtenirContextePersistance() ; 
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(s) FROM Soutien s", Long.class);
        return query.getSingleResult();
    }
    
    public long countAllByEtablissement( Etablissement etablissement ) 
    {
        EntityManager em = JpaUtil.obtenirContextePersistance() ; 
        String request = "SELECT COUNT(s) FROM Soutien s where s.eleve.etablissement = :etablissement";
        TypedQuery<Long> query = em.createQuery(request, Long.class);
        query.setParameter("etablissement", etablissement);  
        return query.getSingleResult(); 
    }

    public Double getDureeMoyenneByEtablissement( Etablissement etablissement ) 
    {
        EntityManager em = JpaUtil.obtenirContextePersistance() ; 
        String request =  "SELECT AVG(s.duree) FROM Soutien s WHERE s.eleve.etablissement = :etablissement AND s.duree IS NOT NULL";
        TypedQuery<Double> query = em.createQuery(request, Double.class);
        query.setParameter("etablissement", etablissement);  
        return query.getSingleResult();
    }
}
