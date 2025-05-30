package web.vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import static console.Main.printlnConsoleIHM;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import metier.modele.Matiere;
import metier.modele.Soutien;


public class ListerHistoriqueSerialisation extends Serialisation{
   
    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<Long, String> histo_objets = (Map<Long, String>) request.getAttribute("historique");
        printlnConsoleIHM("1 :"+histo_objets);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(histo_objets);
        printlnConsoleIHM("2 :"+json);
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(json);
            out.flush();
        }
    }
}
