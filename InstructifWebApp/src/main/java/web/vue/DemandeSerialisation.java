package web.vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import metier.modele.Soutien;
import metier.modele.Matiere;


public class DemandeSerialisation extends Serialisation{
   
    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Boolean soutien_res = (Boolean) request.getAttribute("soutien_res");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(soutien_res);

        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(json);
            out.flush();
        }
    }
}
