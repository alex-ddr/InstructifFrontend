package web.vue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Eleve;
import static console.Main.printlnConsoleIHM;


public class InscrireEleveSerialisation extends Serialisation{
   
    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        Eleve eleve = (Eleve) request.getAttribute("eleve");
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(eleve);
        
        printlnConsoleIHM(gson.toJson(json));

        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(json);
            out.flush();
        }
    };
}
