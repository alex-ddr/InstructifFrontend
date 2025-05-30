/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.modele;

import static console.Main.printlnConsoleIHM;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import metier.modele.Eleve;
import metier.modele.Intervenant;
import metier.modele.Matiere;
import metier.modele.Soutien;
import metier.service.Service;

/**
 *
 * @author adidier2
 */
public class ListerHistoriqueAction extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Service service = new Service();

        HttpSession session = request.getSession();
        String type = (String) session.getAttribute("type");
        List<Soutien> historique = null;

        if (type.equals("eleve")) {
            Eleve eleve = (Eleve) session.getAttribute("Eleve");
            try {
                historique = service.ListerHistoriqueSoutienEleve(eleve);
            } catch (Exception ex) {
                Logger.getLogger(ListerHistoriqueAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Intervenant intervenant = (Intervenant) session.getAttribute("Intervenant");
            try {
                historique = service.ListerHistoriqueSoutienIntervenant(intervenant);
            } catch (Exception ex) {
                Logger.getLogger(ListerHistoriqueAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Map<Long, String> histo_objets= new LinkedHashMap<>();
        System.out.println("Nombre de soutiens dans l'historique :" + historique.size());
        for (Soutien s : historique) {
            histo_objets.put(s.getId(), s.getMatiere().getIntitule());
            System.out.println(s.getHeureDebut());
        }
        System.out.println(histo_objets);
        request.setAttribute("historique", histo_objets);

    }
}
