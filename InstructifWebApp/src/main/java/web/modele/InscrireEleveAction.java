/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.modele;

import static console.Main.printlnConsoleIHM;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Eleve;
import metier.service.Service;

/**
 *
 * @author adidier2
 */
public class InscrireEleveAction extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String mdp = request.getParameter("mdp");
        String date_naissance = request.getParameter("date_naissance");
        String code = request.getParameter("code");
        Long niveau = Long.parseLong(request.getParameter("niveau"));

        Service service = new Service();

        printlnConsoleIHM("Inscription Eleve");
        Eleve e = null;
        try {
            e = new Eleve(date_naissance, niveau, nom, prenom, email, mdp);
            Boolean res = service.inscrireEleve(e, code);
            printlnConsoleIHM(res + " -> Inscription eleve " + e);
        } catch (IOException ex) {
            Logger.getLogger(InscrireEleveAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(InscrireEleveAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (e == null)
            printlnConsoleIHM("Insription Eleve ratée");
    }

}
