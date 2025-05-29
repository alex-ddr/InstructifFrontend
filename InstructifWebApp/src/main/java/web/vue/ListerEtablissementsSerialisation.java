package web.vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import metier.modele.Etablissement;

public class ListerEtablissementsSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<?> etabList = (List<?>) request.getAttribute("etabList");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(etabList);

        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(json);
            out.flush();
        }
    }

}
