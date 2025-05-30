package web.vue;

import com.google.gson.JsonObject;
import static console.Main.printlnConsoleIHM;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import metier.modele.Soutien;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AfficherDetailSoutienSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Soutien soutien = (Soutien) request.getAttribute("soutien");

        JsonObject container = new JsonObject();
        container.addProperty("eleve", soutien.getEleve().getPrenom() + " " + soutien.getEleve().getNom());
        container.addProperty("intervenant", soutien.getIntervenant().getPrenom() + " " + soutien.getIntervenant().getNom());
        container.addProperty("matiere", soutien.getMatiere().getIntitule());

        Date date = soutien.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = sdf.format(date);
        container.addProperty("date", formattedDate);

        Time heureDebut = soutien.getHeureDebut();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String formattedHeure = timeFormat.format(heureDebut);

        container.addProperty("heure_debut", formattedHeure);
        
        container.addProperty("duree", Long.toString(soutien.getDuree()) + "s");
        
        container.addProperty("description", soutien.getDescription());
        container.addProperty("bilan", soutien.getRetourIntervenant());
        container.addProperty("retour_eleve", soutien.getNoteEleve());

        printlnConsoleIHM(container);
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(container.toString());
            out.flush();
        }
    }
}
