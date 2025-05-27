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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import metier.modele.Soutien;

public class AfficherSoutienSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Soutien soutien = (Soutien) request.getAttribute("soutien");

        JsonObject container = new JsonObject();

        container.addProperty("eleve", soutien.getEleve().getNom() + " " + soutien.getEleve().getPrenom());

        Date dateNaissance = soutien.getEleve().getDateNaissance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateStr = formatter.format(dateNaissance);
        container.addProperty("dateNaissance", dateStr);
        
        container.addProperty("niveau", soutien.getEleve().getNiveau());
        container.addProperty("etablissement", soutien.getEleve().getEtablissement().getDenomination());
        container.addProperty("matiere", soutien.getMatiere().getIntitule());
        container.addProperty("description", soutien.getDescription());
        container.addProperty("lien_visio", soutien.getLienVisio());
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(container);

        printlnConsoleIHM(json);

        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(json);
            out.flush();
        }
    }

}
