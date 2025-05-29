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

public class LienVisioEleveSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String lien_visio = (String) request.getAttribute("lien-visio");

        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(lien_visio);

        printlnConsoleIHM(json);

        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(json);
            out.flush();
        }
    }

}
