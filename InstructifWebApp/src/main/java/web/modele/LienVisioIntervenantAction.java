/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.modele;

import static console.Main.printlnConsoleIHM;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Eleve;
import metier.modele.Intervenant;
import metier.modele.Autre;
import metier.modele.Personne;
import metier.modele.Soutien;
import metier.service.Service;

/**
 *
 * @author adidier2
 */
public class LienVisioIntervenantAction extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Service service = new Service();

        HttpSession session = request.getSession();
        Intervenant intervenant = (Intervenant) session.getAttribute("Intervenant");
        
        try {
            Soutien soutien = service.obtenirDemandeSoutien(intervenant);
            service.commencerSoutien(soutien);
            request.setAttribute("seance_commencee", true);
            
        } catch (Exception ex) {
            Logger.getLogger(AfficherSoutienAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
