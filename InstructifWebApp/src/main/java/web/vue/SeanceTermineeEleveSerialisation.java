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

public class SeanceTermineeEleveSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String noteStr = request.getParameter("note_eleve");
        Long note_eleve = null;
        try {
            note_eleve = Long.valueOf(noteStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(note_eleve);

        printlnConsoleIHM(json);

        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(json);
            out.flush();
        }
    }

}
