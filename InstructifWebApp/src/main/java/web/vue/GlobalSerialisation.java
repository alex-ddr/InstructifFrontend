package web.vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Personne;
import metier.modele.Eleve;
import metier.modele.Intervenant;
import static console.Main.printlnConsoleIHM;
import javax.servlet.http.HttpSession;

public class GlobalSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String type = (String) session.getAttribute("type");

        JsonObject container = new JsonObject();

        if (type.equals("eleve")) {
            Eleve eleve = (Eleve) session.getAttribute("Eleve");
            container.addProperty("nom", eleve.getNom());
            container.addProperty("prenom", eleve.getPrenom());
        } else {
            Intervenant intervenant = (Intervenant) session.getAttribute("Intervenant");
            container.addProperty("nom", intervenant.getNom());
            container.addProperty("prenom", intervenant.getPrenom());
        }

        printlnConsoleIHM(container);
        // Création du JSON de réponse
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(container.toString());
            out.flush();
        }
    }
;
}
