/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;


import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Stats;
/**
 *
 * @author ytaharaste
 */
public class StatsDAO {

    public void create(Stats stats) {
        JpaUtil.obtenirContextePersistance().persist(stats);
    }
    
    public Stats find() {
        return JpaUtil.obtenirContextePersistance().find(Stats.class, 1);
    }
}