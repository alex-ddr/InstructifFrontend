/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.modele;

import static console.Main.printlnConsoleIHM;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Eleve;
import metier.modele.Intervenant;
import metier.modele.Autre;
import metier.modele.Personne;
import metier.service.Service;

/**
 *
 * @author adidier2
 */
public class ConnexionAction extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        String email = request.getParameter("email");
        String mdp = request.getParameter("mdp");

        Service service = new Service();

        Personne personne = service.Authentification(email, mdp);
        if (personne != null) {

            Eleve eleve;
            Intervenant intervenant;
            
            if (personne instanceof Eleve) {
                eleve = (Eleve) personne;
            } else {
                intervenant = (Intervenant) personne;
            }
            
            window.location

        }
        else
            printlnConsoleIHM("Personne pas trouvée.");
        }
    }

}
