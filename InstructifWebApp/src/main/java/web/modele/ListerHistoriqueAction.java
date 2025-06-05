package web.modele;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Eleve;
import metier.modele.Intervenant;
import metier.modele.Soutien;
import metier.service.Service;

public class ListerHistoriqueAction extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Service service = new Service();

        HttpSession session = request.getSession();
        // On récupère le type ("eleve" ou "intervenant") stocké en session
        String type = (String) session.getAttribute("type");
        List<Soutien> historique = null;

        if ("eleve".equals(type)) {
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

        // On construit une map id→matière pour la sérialisation
        Map<Long, String> histo_objets = new LinkedHashMap<>();
        for (Soutien s : historique) {
            histo_objets.put(s.getId(), s.getMatiere().getIntitule());
        }

        // On place la map dans l'attribut "historique"
        request.setAttribute("historique", histo_objets);
        // On place également le rôle ("eleve" ou "intervenant") dans l'attribut "role"
        request.setAttribute("role", type);
    }
}