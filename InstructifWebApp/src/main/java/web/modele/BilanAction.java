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
import metier.modele.Eleve;
import metier.modele.Intervenant;
import metier.modele.Autre;
import metier.modele.Personne;
import metier.service.Service;

/**
 *
 * @author adidier2
 */
public class BilanAction extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Service service = new Service();
        
        String retourIntervenant = (String) request.getAttribute("retourIntervenant");
        HttpSession session = request.getSession();
        Intervenant intervenant = (Intervenant) session.getAttribute("Intervenant");
        
        Boolean res = service.cloturerSoutien(intervenant, retourIntervenant);
        
    }
}
