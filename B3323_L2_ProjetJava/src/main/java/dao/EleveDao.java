/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Eleve;
/**
 *
 * @author ytaharaste
 */
public class EleveDao {

    public void create(Eleve eleve) {
        JpaUtil.obtenirContextePersistance().persist(eleve);
    }
    
    public Eleve findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Eleve.class, id);
    }

    public List<Eleve> findAll() {
        String s = "select e from Eleve e order by e.nom asc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Eleve.class);
        return query.getResultList();
    }
}
