package console;

import dao.JpaUtil;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;
import metier.modele.Autre;
import metier.modele.Eleve;
import metier.modele.Enseignant;
import metier.modele.Etablissement;
import metier.modele.Etudiant;
import metier.modele.Intervenant;
import metier.modele.Matiere;
import metier.modele.Personne;
import metier.modele.Soutien;
import metier.service.Service;

public class Main {

    public static void main(String[] args) throws IOException, ParseException, Exception {
        //JpaUtil.desactiverLog();
        JpaUtil.creerFabriquePersistance();

        Service service = new Service();
        service.initialisationMatiere();
        service.initialisationIntervenant();

        testerInscrireEleves();
        ajouterEtablissements();
        testerConsulterListeEtablissements();

        Eleve eleve = new Eleve();
        Intervenant intervenant = new Intervenant();

        // Authentification Eleve
        Personne personne = service.Authentification("alice.pascal@free.fr", "1234");
        if (personne instanceof Eleve) {
            eleve = (Eleve) personne;
        } else {
            intervenant = (Intervenant) personne;
        }

        // Authentification Intervenant
        Personne personne2 = service.Authentification("pierre.durand@mail.com", "password123");
        if (personne2 instanceof Eleve) {
            eleve = (Eleve) personne2;
        } else {
            intervenant = (Intervenant) personne2;
        }

        testerDemandeSoutien(eleve);
        Soutien soutien = testerObtenirDemandeSoutien(intervenant);
        testerCommencerSoutien(soutien);
        testEvaluerProgression(eleve);
        testerCloturerSoutien(intervenant);

        testerHistoriqueEleve(eleve);
        testerHistoriqueIntervenant(intervenant);
        testerTableauDeBord();

        JpaUtil.fermerFabriquePersistance();
    }

//    private static void testerAuthentification() throws Exception {
//        Service service = new Service();
//        String mail = "alice.pascal@free.fr" ; 
//        String mdp = "1234" ; 
//        Personne personne = service.Authentification(mail,mdp) ; 
//        printlnConsoleIHM(personne);
//    }
    private static void testerHistoriqueEleve(Eleve eleve) throws Exception {
        try {
            Service service = new Service();

            // Historique
            List<Soutien> historique = service.ListerHistoriqueSoutienEleve(eleve);

            if (historique == null || historique.isEmpty()) {
                printlnConsoleIHM("Aucun soutien trouvé pour cette élève.");
            } else {
                printlnConsoleIHM(eleve);
                printlnConsoleIHM("LA LISTE DES SOUTIENS ");
                for (Soutien s : historique) {
                    printlnConsoleIHM(" - " + s); // ou affiche les détails utiles
                }
            }
        } catch (IOException | ParseException e) {
            System.err.println("Erreur lors de la récupération de l'historique : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void testerHistoriqueIntervenant(Intervenant intervenant) {
        Service service = new Service();

        try {
            // Historique
            List<Soutien> historique = service.ListerHistoriqueSoutienIntervenant(intervenant);

            if (historique == null || historique.isEmpty()) {
                printlnConsoleIHM("Aucun soutien trouvé pour cet intervenant : " + intervenant);
            } else {
                printlnConsoleIHM("Intervenant : " + intervenant);
                printlnConsoleIHM("Soutiens trouvés :");
                for (Soutien s : historique) {
                    printlnConsoleIHM(" - " + s); // ou affiche les détails utiles
                }
                printlnConsoleIHM("==== Test : Historique des soutiens d’un intervenant ====");
            }

        } catch (Exception e) {
            printlnConsoleIHM("Erreur lors du test d’historique des soutiens : " + e.getMessage());
            e.printStackTrace();
        }

        printlnConsoleIHM("==========================================================\n");
    }

    private static void ajouterEtablissements() throws IOException, ParseException {
        Service service = new Service();
        Vector<String> codes = new Vector<>();
        codes.add("0010018P");
        codes.add("0010037K");
        codes.add("0010083K");
        codes.add("0010939R");
        
        for (String code : codes)
            service.ajouterEtablissement(code);
    }

    private static void testerInscrireEleves() throws IOException, ParseException {
        Service service = new Service();
        String codeEtablissement = "0692155T";

        printlnConsoleIHM("Inscription Eleve E2");
        Eleve e1;

        e1 = new Eleve("2000-03-06", 3L, "Pascal", "Alice", "alice.pascal@free.fr", "1234");
        Boolean resultat1 = service.inscrireEleve(e1, codeEtablissement);
        printlnConsoleIHM(resultat1 + " -> Inscription eleve C1 " + e1);

        printlnConsoleIHM("Inscription Eleve E2");
        Eleve e2 = new Eleve("06-03-2004", 6L, "Bernard", "Tom", "tom.bernard@gmail.com", "5678");
        Boolean resultat2 = service.inscrireEleve(e2, codeEtablissement);
        printlnConsoleIHM(resultat2 + " -> Inscription eleve C2 " + e2);
    }

    private static void testerDemandeSoutien(Eleve eleve) throws IOException, ParseException {
        Service service = new Service();
        List<Matiere> matieres = service.listerMatieres();

        printlnConsoleIHM("Demande Soutien");
        String description = "Je n'ai pas tout compris sur le chapitre sur le Moyen-Âge";
        Boolean resultat = service.demandeSoutien(eleve, description, matieres.get(0));
        printlnConsoleIHM(resultat + " -> demandeSoutien ");
    }

    public static void testEvaluerProgression(Eleve eleve) {
        Service service = new Service();

        try {

            boolean resultat = service.evaluerProgression(eleve, 5L);
            System.out.println("Résultat de evaluerProgression : " + resultat);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors du test d'évaluation de la progression.");
        }
    }

    private static Soutien testerObtenirDemandeSoutien(Intervenant intervenant) {
        Service service = new Service();
        Soutien soutien = null;
        try {

            soutien = service.obtenirDemandeSoutien(intervenant);
            printlnConsoleIHM("Tentative d'obtention d'une demande de soutien pour " + intervenant.getPrenom());
            if (soutien != null) {
                printlnConsoleIHM("Soutien trouvé !" + soutien);
                printlnConsoleIHM("Élève : " + soutien.getEleve().getPrenom()
                        + " | Description : " + soutien.getDescription()
                        + " | Matiere : " + soutien.getMatiere());
            } else {
                printlnConsoleIHM("Aucune demande de soutien disponible pour l'instant.");
            }
        } catch (Exception e) {
            printlnConsoleIHM("Erreur lors de l'obtention de la demande de soutien : " + e.getMessage());
            e.printStackTrace();
        }
        return soutien;
    }

    private static void testerCommencerSoutien(Soutien soutien) {
        Service service = new Service();
        service.commencerSoutien(soutien);

    }

    public static void testerTableauDeBord() {
        Service service = new Service();
        List<Etablissement> etablissements = service.listerEtablissements();
        Etablissement etablissement = etablissements.get(0);
        Long totalSoutiens = service.getNombreTotalSoutiens();
        Long totalSoutiensEtablissement = service.getNombreTotalSoutiensEtablissement(etablissement);
        Double dureeMoyenne = service.getDureeMoyenneSoutiensEtablissement(etablissement);

        printlnConsoleIHM("=======================================");
        printlnConsoleIHM("       Tableau de Bord des Soutiens");
        printlnConsoleIHM("=======================================");
        printlnConsoleIHM("Nombre total de soutiens réalisés : " + totalSoutiens);
        printlnConsoleIHM("Nombre total de soutiens réalisés dans l'établissement " + etablissement.getDenomination() + " : " + totalSoutiens);

        if (dureeMoyenne != null) {
            printlnConsoleIHM("Durée moyenne des soutiens        : " + dureeMoyenne);
        } else {
            printlnConsoleIHM("Durée moyenne des soutiens        : Aucune donnée disponible");
        }

        if (etablissements != null && !etablissements.isEmpty()) {
            for (Etablissement etablissement2 : etablissements) {
                printlnConsoleIHM(etablissement2); // Affiche les établissements
            }
        } else {
            System.out.println("Aucun établissement trouvé.");
        }
        printlnConsoleIHM("=======================================\n");
    }

    public static void testerCloturerSoutien(Intervenant intervenant) {
        Service service = new Service();
        try {
            String retour = "Tout s'est bien passé, élève attentif.";
            boolean resultat = service.cloturerSoutien(intervenant, retour);
            System.out.println("Résultat de cloturerSoutien : " + resultat);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur pendant le test de cloture du soutien.");
        }
    }

//    private static void testerConsulterListeEleves() {
//        Service service = new Service();
//        List<Eleve> eleves = service.listerEleves();
//
//        if (eleves == null) {
//            printlnConsoleIHM("ERREUR du Service listerEleves");
//        } else {
//            printlnConsoleIHM("Liste des Eleves (" + eleves.size() + ")");
//            for (Eleve c : eleves) {
//                printlnConsoleIHM("#" + c);
//            }
//            printlnConsoleIHM("----");
//        }
//    }
    private static void testerConsulterListeEtablissements() {
        Service service = new Service();
        List<Etablissement> etablissements = service.listerEtablissements();

        if (etablissements == null) {
            printlnConsoleIHM("ERREUR du Service listerEtablissements : La liste est NULL");
        } else if (etablissements.isEmpty()) {
            printlnConsoleIHM("Aucun établissement trouvé");
        } else {
            printlnConsoleIHM("Liste des Etablissements (" + etablissements.size() + ")");
            for (Etablissement e : etablissements) {
                if (e != null) {
                    printlnConsoleIHM("#" + e);
                } else {
                    printlnConsoleIHM("Un établissement NULL");
                }
            }
            printlnConsoleIHM("----");
        }
    }
//
//    private static void testerConsulterListeSoutiens() {
//        Service service = new Service();
//        List<Soutien> soutiens = service.listerSoutiens();
//
//        if (soutiens == null) {
//            printlnConsoleIHM("ERREUR du Service listerSoutiens");
//        } else {
//            printlnConsoleIHM("Liste des Soutiens (" + soutiens.size() + ")");
//            for (Soutien s : soutiens) {
//                printlnConsoleIHM("#" + s);
//            }
//            printlnConsoleIHM("----");
//        }
//    }

    private static void testerConsulterListeMatieres() {
        Service service = new Service();
        List<Matiere> matieres = service.listerMatieres();

        if (matieres == null) {
            printlnConsoleIHM("ERREUR du Service listerMatieres");
        } else {
            printlnConsoleIHM("Liste des Matieres (" + matieres.size() + ")");
            for (Matiere m : matieres) {
                printlnConsoleIHM("#" + m);
            }
            printlnConsoleIHM("----");
        }
    }
//    
//    private static void testerConsulterListeIntervenants() {
//        Service service = new Service();
//        List<Intervenant> intervenants = service.listerIntervenants();
//
//        if (intervenants == null) {
//            printlnConsoleIHM("ERREUR du Service listerIntervenants");
//        } else {
//            printlnConsoleIHM("Liste des Intervenants (" + intervenants.size() + ")");
//            for (Intervenant i : intervenants) {
//                printlnConsoleIHM("#" + i);
//            }
//            printlnConsoleIHM("----");
//        }
//    }

    public static void printlnConsoleIHM(Object o) {
        String BG_CYAN = "\u001b[46m";
        String RESET = "\u001B[0m";

        System.out.print(BG_CYAN);
        System.out.println(String.format("%-80s", o));
        System.out.print(RESET);
    }

}
