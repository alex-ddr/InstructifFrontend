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
import javax.servlet.http.HttpSession;
import java.util.List;
import metier.modele.Matiere;
import metier.service.Service;

/**
 *
 * @author adidier2
 */
public class ListerMatieresAction extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        List<Matiere> matieres = service.listerMatieres();
        if (matieres == null) {
            System.out.println("Erreur du service ListerMatiere");
        } else {
            System.out.println("Liste des Matières (" + matieres.size() + ")");
            for (Matiere m : matieres) {
                System.out.println(m.getIntitule());
            }
            request.setAttribute("matieres", matieres);

        }

    }
}
