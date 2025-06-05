package web.vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListerHistoriqueSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Récupère la map id → matière
        @SuppressWarnings("unchecked")
        Map<Long, String> histo_objets = (Map<Long, String>) request.getAttribute("historique");
        // Récupère le rôle défini dans l’action
        String role = (String) request.getAttribute("role");

        // On crée un objet JSON où :
        //   - "historique" contient la map
        //   - "role" contient le rôle ("eleve" ou "intervenant")
        JsonObject wrapper = new JsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Sérialisation de la map en JSON (pour "historique")
        String histoJson = gson.toJson(histo_objets);
        // Ajouter la sous-structure "historique"
        wrapper.add("historique", gson.fromJson(histoJson, JsonObject.class));
        // Ajouter le champ "role"
        wrapper.addProperty("role", role);

        // Envoi de la réponse JSON au client
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(gson.toJson(wrapper));
            out.flush();
        }
    }
}