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

public class LienVisioIntervenantSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Boolean seance_commencee = (Boolean) request.getAttribute("seance_commencee");

        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(seance_commencee);

        printlnConsoleIHM(json);

        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(json);
            out.flush();
        }
    }

}
