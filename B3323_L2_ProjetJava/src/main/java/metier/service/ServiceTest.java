/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import dao.EleveDao;
import dao.IntervenantDao;
import dao.JpaUtil;
import dao.MatiereDao;
import dao.SoutienDao;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import metier.modele.Eleve;
import metier.modele.Intervenant;
import metier.modele.Matiere;
import metier.modele.Soutien;

/**
 *
 * @author ytaharaste
 */
public class ServiceTest {
    // Méthodes pour  tests, inutiles pour l'application
    
    public void remplirSoutiensFictifs() throws Exception {
        SoutienDao soutienDao = new SoutienDao();
        EleveDao eleveDao = new EleveDao();
        IntervenantDao intervenantDao = new IntervenantDao();
        MatiereDao matiereDao = new MatiereDao();

        Random random = new Random();

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            List<Eleve> eleves = eleveDao.findAll();
            List<Intervenant> intervenants = intervenantDao.findAll();
            List<Matiere> matieres = matiereDao.findAll();

            if (eleves.isEmpty() || intervenants.isEmpty() || matieres.isEmpty()) {
                System.out.println("Pas assez de données pour générer les soutiens.");
                JpaUtil.annulerTransaction();
                return;
            }

            for (int i = 0; i < 20; i++) {
                Date date = new Date(System.currentTimeMillis() - random.nextInt(1000000000)); // date aléatoire récente
                Time heureDemande = new Time(8 + random.nextInt(10), random.nextInt(60), 0); // entre 8h et 18h
                Time heureDebut = new Time(heureDemande.getHours(), heureDemande.getMinutes() + 5, 0);

                Long duree = 30L + random.nextInt(61); // entre 30 et 90 minutes
                String description = "Soutien numéro " + (i + 1);
                String lienVisio = "https://visio.fake/soutien" + (i + 1);

                Soutien s = new Soutien(date, heureDemande, description, matieres.get(random.nextInt(matieres.size())));
                s.setHeureDebut(heureDebut);
                s.setLienVisio(lienVisio);
                s.setDuree(duree);
                s.setNoteEleve((long) (1 + random.nextInt(5)));
                s.setRetourIntervenant("Retour fictif #" + (i + 1));
                s.setEleve(eleves.get(random.nextInt(eleves.size())));
                s.setIntervenant(intervenants.get(random.nextInt(intervenants.size())));

                soutienDao.create(s);
            }

            JpaUtil.validerTransaction();
            System.out.println("20 soutiens fictifs ont été ajoutés avec succès !");
        } catch (Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

    
    public List<Intervenant> listerIntervenants() {
        List<Intervenant> intervenants = new ArrayList<>();
        IntervenantDao intervenantDao = new IntervenantDao();
        try {
            JpaUtil.creerContextePersistance();
            intervenants = intervenantDao.findAll();
        } catch (Exception ex) {
            ex.printStackTrace(); // Affiche l'exception si ça plante
        } finally {
            JpaUtil.fermerContextePersistance(); // Ferme toujours l'EntityManager
        }
        return intervenants;
    }
    

    
    public List<Eleve> listerEleves() {
        List<Eleve> eleves = new ArrayList<>();
        EleveDao eleveDao = new EleveDao();
        try {
            JpaUtil.creerContextePersistance();
            eleves = eleveDao.findAll();
        } catch (Exception ex) { // ça n'a pas marché, on affiche l'exception
            ex.printStackTrace();
        } finally { // dans tous les cas, on ferme l'entity manager
            JpaUtil.fermerContextePersistance();
        }
        return eleves;
    }
    
    
    public List<Soutien> listerSoutiens() {
        List<Soutien> soutiens = new ArrayList<>();
        SoutienDao soutienDao = new SoutienDao();
        try {
            JpaUtil.creerContextePersistance();
            soutiens = soutienDao.findAll();
        } catch (Exception ex) {
            ex.printStackTrace(); // Affiche l'exception si ça plante
        } finally {
            JpaUtil.fermerContextePersistance(); // Ferme toujours l'EntityManager
        }
        return soutiens;
    }
    
}
