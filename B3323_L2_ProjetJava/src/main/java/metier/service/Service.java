/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.model.LatLng;
import static console.Main.printlnConsoleIHM;
import metier.modele.Eleve;
import dao.EleveDao;
import dao.EtablissementDAO;
import dao.IntervenantDao;
import dao.JpaUtil;
import dao.MatiereDao;
import dao.PersonneDAO;
import dao.SoutienDao;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import metier.modele.Autre;
import metier.modele.Enseignant;
import metier.modele.Etablissement;
import metier.modele.Etudiant;
import metier.modele.Intervenant;
import metier.modele.Matiere;
import metier.modele.Personne;
import metier.modele.Soutien;
import util.EducNetApi;
import util.GeoNetApi;
import util.Message;

/**
 *
 * @author ytaharaste
 */
public class Service {

    public Etablissement creerEtablissement(String code) throws IOException {
        EducNetApi api = new EducNetApi();
        List<String> result = api.getInformationEtablissement(code);

        if (result != null) {
            String denomination = result.get(1);

            Float ips = Float.parseFloat(result.get(8));

            String nomCommune = result.get(4);

            System.out.println("Etablissement " + code + ": " + denomination + " à " + nomCommune);

            String adresse = denomination + ", " + nomCommune;
            LatLng coord = GeoNetApi.getLatLng(adresse);
            Etablissement etablissement = new Etablissement(code, denomination, ips, coord);
            return etablissement;
        } else {
            System.out.println("Etablissement inconnu");
            return null;
        }

    }

    public void ajouterEtablissement(String code) {
        EtablissementDAO etablissementDAO = new EtablissementDAO();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            Etablissement e = creerEtablissement(code);
            etablissementDAO.createEtablissment(e);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally { // dans tous les cas, on ferme l'entity manager
            JpaUtil.fermerContextePersistance();
        }
    }

    public Boolean inscrireEleve(Eleve eleve, String code) {
        EleveDao eleveDao = new EleveDao();
        EtablissementDAO etablissementDAO = new EtablissementDAO();
        Message message = new Message();
        Boolean resultat = true;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            //String code = eleve.getCodeEtablissement(); 
            Etablissement e = etablissementDAO.findByCode(code);
            if (e == null) {
                e = creerEtablissement(code);
                etablissementDAO.createEtablissment(e);
            }
            eleve.setEtablissement(e);
            eleveDao.create(eleve);
            JpaUtil.validerTransaction();
            // envoyer le mail de confirmation
            message.envoyerMail("contact@instruct.if", eleve.getMail(), "Bienvenue dans le réseau INSTRUCT'IF", "Bonjour " + eleve.getPrenom() + ", nous te confirmons ton inscription sur le réseau INSTRUC'IF. Si tu as besoin d'un soutien pour tes leçons ou tes devoirs, rends-toi sur notre site pour une mise en relation avec un intervenant.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            message.envoyerMail("contact@instruct.if", eleve.getMail(), "Echec de l'inscription sur le réseau INSTRUCT'IF", "Bonjour " + eleve.getPrenom() + ", ton inscription sur le réseau INSTRUCT'IF a malencontreusement échouée... Merci de recommencer ultérieurement");
            resultat = false; // on pourrait aussi lancer une exception
        } finally { // dans tous les cas, on ferme l'entity manager
            JpaUtil.fermerContextePersistance();

        }
        return resultat;
    }

    public String genererLienVisio(String mailEleve, String nomIntervenant, String prenomIntervenant) {
        String lienVisio = "https://servif.insa-lyon.fr/InteractIF/visio.html?eleve=" + mailEleve
                + "&intervenant=" + prenomIntervenant.charAt(0) + nomIntervenant;
        return lienVisio;
    }

    // Service pour l'élève
    public Boolean demandeSoutien(Eleve eleve, String description, Matiere matiere) {
        SoutienDao soutienDao = new SoutienDao();
        IntervenantDao intervenantDao = new IntervenantDao();
        Message message = new Message();

        Date date = Date.valueOf(LocalDate.now());
        Time heureDemande = Time.valueOf(LocalTime.now());
        Soutien soutien = new Soutien(date, heureDemande, description, matiere);
        Boolean resultat = true;
        try {
            JpaUtil.creerContextePersistance();
            soutien.setEleve(eleve);
            Intervenant intervenant = intervenantDao.findBestIntervenant(eleve.getNiveau());
            if (intervenant == null) {
                System.out.println("Aucun intervenant disponible.");
                resultat = false;
            }
            soutien.setIntervenant(intervenant);
            String lienVisio = genererLienVisio(eleve.getMail(), intervenant.getNom().toLowerCase(), intervenant.getPrenom().toLowerCase());
            soutien.setLienVisio(lienVisio);

            JpaUtil.ouvrirTransaction();
            soutienDao.create(soutien);
            printlnConsoleIHM(" -> soutien " + soutien);
            intervenant.setDisponibilite(false);
            intervenant.setNbSolicitation(intervenant.getNbSolicitation() + 1);
            intervenantDao.update(intervenant);
            JpaUtil.validerTransaction();

            message.envoyerNotification(intervenant.getTel(), "Bonjour " + intervenant.getPrenom()
                    + ". Merci de prendre en charge la demande de soutien en « "
                    + soutien.getMatiere().getIntitule()
                    + " » demandée à " + soutien.getHeureDemande() + " par " + eleve.getPrenom()
                    + " en classe de " + eleve.getNiveau() + "ème.");
            resultat = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            resultat = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    public boolean evaluerProgression(Eleve eleve, Long note) {
        SoutienDao soutienDao = new SoutienDao();
        Boolean resultat = true;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            // Récupération du dernier soutien de l'élève
            Soutien dernierSoutien = soutienDao.findLastSoutienByEleve(eleve);

            if (dernierSoutien == null) {
                System.out.println("Aucun soutien trouvé pour cet élève.");
                resultat = false;
            }
            dernierSoutien.setNoteEleve(note);
            soutienDao.update(dernierSoutien);
            JpaUtil.validerTransaction();
            resultat = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            resultat = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    public Soutien obtenirDemandeSoutienEleve(Eleve eleve) throws Exception {
        SoutienDao soutienDao = new SoutienDao();
        Soutien soutien = null;

        JpaUtil.creerContextePersistance();

        soutien = soutienDao.findActualSoutienEleve(eleve);
        JpaUtil.fermerContextePersistance();

        return soutien;
    }

    // Services pour l'intervenant
    public Soutien obtenirDemandeSoutien(Intervenant intervenant) throws Exception {
        SoutienDao soutienDao = new SoutienDao();
        IntervenantDao intervenantDao = new IntervenantDao();
        Soutien soutien = null;

        JpaUtil.creerContextePersistance();

        intervenant = intervenantDao.findById(intervenant.getId());
        if (intervenant.getDisponibilite()) {
            printlnConsoleIHM("Pas de demande de soutien pour" + intervenant.getPrenom());
        } else {
            soutien = soutienDao.findActualSoutien(intervenant);
        }
        JpaUtil.fermerContextePersistance();

        return soutien;
    }

    public boolean commencerSoutien(Soutien soutien) {
        SoutienDao soutienDao = new SoutienDao();
        boolean resultat = true;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            Time heureDebut = Time.valueOf(LocalTime.now());
            soutien.setHeureDebut(heureDebut);
            soutienDao.update(soutien);
            JpaUtil.validerTransaction();

        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            resultat = false;

        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    public boolean cloturerSoutien(Intervenant intervenant, String retourIntervenant) {
        SoutienDao soutienDao = new SoutienDao();
        IntervenantDao intervenantDao = new IntervenantDao();
        Message message = new Message();
        boolean resultat = true;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            // On récupère le soutien actuel
            Soutien soutien = soutienDao.findActualSoutien(intervenant);

            if (soutien == null) {
                System.out.println("Aucun soutien actif trouvé pour l'intervenant.");
                JpaUtil.annulerTransaction();
                resultat = false;
            }

            soutien.setRetourIntervenant(retourIntervenant);
            intervenant.setDisponibilite(true);
            // Calcule la durée en minutes
            LocalTime heureDebut = soutien.getHeureDebut().toLocalTime();
            LocalTime heureFin = LocalTime.now();
            Long dureeSecondes = java.time.Duration.between(heureDebut, heureFin).toMillis() / 1000;
            soutien.setDuree(dureeSecondes);

            soutienDao.update(soutien);
            intervenantDao.update(intervenant);
            JpaUtil.validerTransaction();

            String email = soutien.getEleve().getMail();
            String sujet = "Retour de votre soutien";
            String contenu = "Bonjour " + soutien.getEleve().getPrenom() + ",\n\n"
                    + "Voici le retour de votre intervenant suite au soutien :\n\n"
                    + retourIntervenant + "\n\n"
                    + "Bonne continuation !";
            message.envoyerMail("contact@instruct.if", email, sujet, contenu);

            resultat = true;
        } catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
            resultat = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return resultat;
    }

    public List<Etablissement> listerEtablissements() {
        List<Etablissement> etablissements = new ArrayList<>();
        EtablissementDAO etablissementDao = new EtablissementDAO();
        try {
            JpaUtil.creerContextePersistance();
            etablissements = etablissementDao.findAll();
        } catch (Exception ex) { // ça n'a pas marché, on affiche l'exception
            ex.printStackTrace();
        } finally { // dans tous les cas, on ferme l'entity manager
            JpaUtil.fermerContextePersistance();
        }
        return etablissements;
    }

    public List<Matiere> listerMatieres() {
        List<Matiere> matieres = new ArrayList<>();
        MatiereDao matiereDao = new MatiereDao();
        try {
            JpaUtil.creerContextePersistance();
            matieres = matiereDao.findAll();
        } catch (Exception ex) {
            ex.printStackTrace(); // Affiche l'exception si ça plante
        } finally {
            JpaUtil.fermerContextePersistance(); // Ferme toujours l'EntityManager
        }
        return matieres;
    }

    public void initialisationMatiere() {
        MatiereDao matiereDao = new MatiereDao();
        Matiere matiere;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            List<String> intitules = Arrays.asList(
                    "Mathématiques",
                    "Physique-Chimie",
                    "Français",
                    "Histoire-Géographie",
                    "Anglais",
                    "Espagnol",
                    "Sciences de la Vie et de la Terre",
                    "Philosophie",
                    "Informatique",
                    "Économie"
            );

            for (String intitule : intitules) {
                matiere = new Matiere(intitule);
                matiereDao.create(matiere);
            }
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally { // dans tous les cas, on ferme l'entity manager
            JpaUtil.fermerContextePersistance();
        }
    }

    public void initialisationIntervenant() throws Exception {
        IntervenantDao intervenantDao = new IntervenantDao();

        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            Enseignant enseignant1 = new Enseignant(15L, "01 23 45 67 89", "Durand", "Pierre", "pierre.durand@mail.com", "password123",
                    Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L), "Lycée", "Mathématiques");
            Enseignant enseignant2 = new Enseignant(16L, "01 34 56 78 90", "Lemoine", "Julien", "julien.lemoine@mail.com", "password124",
                    Arrays.asList(1L, 2L, 4L), "Collège", "Physique");
            Autre autre1 = new Autre(17L, "01 45 67 89 01", "Hernandez", "Ana", "ana.hernandez@mail.com", "password125",
                    Arrays.asList(2L, 3L, 5L), "Consultante", "Consultant en marketing");
            Autre autre2 = new Autre(18L, "01 56 78 90 12", "Robert", "Clément", "clement.robert@mail.com", "password126",
                    Arrays.asList(3L, 4L, 5L), "Freelance", "Développeur full-stack");
            Etudiant etudiant1 = new Etudiant(19L, "06 12 34 56 78", "Martin", "Léa", "lea.martin@mail.com", "password127",
                    Arrays.asList(2L, 5L, 6L), "Etudiant", "Université Paris", "Informatique");
            Etudiant etudiant2 = new Etudiant(20L, "06 23 45 67 89", "Lemoine", "Marc", "marc.lemoine@mail.com", "password128",
                    Arrays.asList(1L, 2L, 4L), "Etudiant", "Université Lyon", "Mathématiques");
            Enseignant enseignant3 = new Enseignant(21L, "01 67 89 01 23", "Dupont", "Luc", "luc.dupont@mail.com", "password129",
                    Arrays.asList(3L, 4L, 5L), "Lycée", "Histoire");
            Autre autre3 = new Autre(22L, "01 78 90 12 34", "Meyer", "Sophie", "sophie.meyer@mail.com", "password130",
                    Arrays.asList(4L, 5L, 6L), "Consultante", "Coach en développement personnel");
            Etudiant etudiant3 = new Etudiant(23L, "06 34 56 78 90", "Ferrer", "Carlos", "carlos.ferrer@mail.com", "password131",
                    Arrays.asList(2L, 4L, 6L), "Etudiant", "Université Nice", "Physique");
            Enseignant enseignant4 = new Enseignant(24L, "01 89 01 23 45", "Martinez", "Sophie", "sophie.martinez@mail.com", "password132",
                    Arrays.asList(1L, 3L, 5L), "Collège", "Anglais");

            // Persiste les intervenants dans la base de données avec intervenantDao.create()
            intervenantDao.create(enseignant1);
            intervenantDao.create(enseignant2);
            intervenantDao.create(autre1);
            intervenantDao.create(autre2);
            intervenantDao.create(etudiant1);
            intervenantDao.create(etudiant2);
            intervenantDao.create(enseignant3);
            intervenantDao.create(autre3);
            intervenantDao.create(etudiant3);
            intervenantDao.create(enseignant4);

            JpaUtil.validerTransaction();
        } catch (IOException e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally { // dans tous les cas, on ferme l'entity manager
            JpaUtil.fermerContextePersistance();
        }
    }

    public List<Soutien> ListerHistoriqueSoutienEleve(Eleve eleve) throws Exception {
        List<Soutien> soutiens;
        try {
            JpaUtil.creerContextePersistance();
            SoutienDao soutienDAO = new SoutienDao();
            soutiens = soutienDAO.findByEleve(eleve);
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération de l'historique des soutiens.", e);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return soutiens;
    }

    public List<Soutien> ListerHistoriqueSoutienIntervenant(Intervenant intervenant) throws Exception {
        List<Soutien> soutiens;
        try {
            JpaUtil.creerContextePersistance();
            SoutienDao soutienDAO = new SoutienDao();
            soutiens = soutienDAO.findByIntervenant(intervenant);
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération des soutiens", e);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return soutiens;
    }

    // Statistiques
    public long getNombreTotalSoutiens() {
        SoutienDao soutienDao = new SoutienDao();
        JpaUtil.creerContextePersistance();
        long total = 0;
        try {
            total = soutienDao.countAll();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return total;
    }

    public long getNombreTotalSoutiensEtablissement(Etablissement etablissement) {
        SoutienDao soutienDao = new SoutienDao();
        JpaUtil.creerContextePersistance();
        long total = 0;
        try {
            total = soutienDao.countAllByEtablissement(etablissement);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return total;
    }

    public double getDureeMoyenneSoutiensEtablissement(Etablissement etablissement) {
        SoutienDao soutienDao = new SoutienDao();
        JpaUtil.creerContextePersistance();
        Double moyenne = null;
        try {
            moyenne = soutienDao.getDureeMoyenneByEtablissement(etablissement);
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return moyenne != null ? moyenne : 0.0;
    }

    public Personne Authentification(String mail, String mdp) {
        Personne personne;
        try {
            JpaUtil.creerContextePersistance(); // Important !
            PersonneDAO personneDAO = new PersonneDAO();
            personne = personneDAO.findByMailAndMdp(mail, mdp);
            if (personne == null) {
                System.out.println("Identifiants incorrects.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return personne;
//        if (personne instanceof Eleve) {
//            return (Eleve) personne;
//        } else if (personne instanceof Enseignant) {
//            return (Enseignant) personne;
//        } else if (personne instanceof Etudiant) {
//            return (Etudiant) personne;
//        } else {
//            return (Autre) personne;
//        }
    }

    public Soutien getSoutienById(Long id) {
        SoutienDao soutienDao = new SoutienDao();
        Soutien soutien = null;

        try {
            JpaUtil.creerContextePersistance();
            soutien = soutienDao.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }

        return soutien;
    }

}
