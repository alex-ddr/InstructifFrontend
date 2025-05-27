/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.modele;

import static console.Main.printlnConsoleIHM;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import metier.modele.Eleve;
import metier.modele.Intervenant;
import metier.modele.Matiere;
import metier.service.Service;

/**
 *
 * @author adidier2
 */
public class DemandeAction extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Service service = new Service();

        HttpSession session = request.getSession();
        Eleve eleve = (Eleve) session.getAttribute("Eleve");

        String description = request.getParameter("description");
        Matiere matiere = null;
        
        
        String matiere_id_str = request.getParameter("matiere");
        Long matiere_id = null;
        try {
            matiere_id = Long.parseLong(matiere_id_str);
        } catch (NumberFormatException e) {
            System.out.println("ID de matière invalide : " + matiere_id_str);
            return;
        }

        List<Matiere> liste_matieres = service.listerMatieres();
        for (Matiere m : liste_matieres) {
            if (m.getId().equals(matiere_id)) {
                matiere = m;
                break;
            }
        }

        if (matiere == null) {
            System.out.println("Matière non trouvée avec l'ID : " + matiere_id);
            return;
        }

        printlnConsoleIHM("Paramètres : " + matiere.getIntitule() + " " + description);
        Boolean res = service.demandeSoutien(eleve, description, matiere);

        
        if (res == false) {
            System.out.println("Erreur du service DemandeAction");
        } else {
            request.setAttribute("soutien_res", res);
        }

    }
}
