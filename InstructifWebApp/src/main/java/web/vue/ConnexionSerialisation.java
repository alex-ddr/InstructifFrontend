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

public class ConnexionSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String type = (String) session.getAttribute("type");

        String redirection = "";
        if (type == null)
            redirection = "";
        else if (type.equals("eleve")) {
            redirection = "demande.html";
        }
        else if (type.equals("intervenant")) {
            Intervenant intervenant = (Intervenant) session.getAttribute("Intervenant");
            
            if (intervenant.getDisponibilite())
                redirection = "dashboard.html";
            else
                redirection = "demandesoutien.html";
        }

        // Création du JSON de réponse
        JsonObject container = new JsonObject();
        container.addProperty("redirection", redirection);

        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(container.toString());
            out.flush();
        }
    }
;
}
