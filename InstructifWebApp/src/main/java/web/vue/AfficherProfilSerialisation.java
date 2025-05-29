package web.vue;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import metier.modele.Eleve;

public class AfficherProfilSerialisation extends Serialisation {
    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        Eleve eleve = (Eleve) session.getAttribute("Eleve");

        JsonObject container = new JsonObject();
        container.addProperty("nom", eleve.getNom());
        container.addProperty("prenom", eleve.getPrenom());
        container.addProperty("dateNaissance", eleve.getDateNaissance().toString());
        container.addProperty("niveauScolaire", eleve.getNiveau());
        container.addProperty("etablissement", eleve.getEtablissement().getDenomination());

        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(container.toString());
            out.flush();
        }
    }
}