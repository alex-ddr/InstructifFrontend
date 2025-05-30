/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.controleur;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.JpaUtil;
import web.modele.*;
import web.vue.*;

/**
 *
 * @author adidier2
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        JpaUtil.creerFabriquePersistance();
    }

    @Override
    public void destroy() {
        JpaUtil.fermerFabriquePersistance();
        super.destroy(); //To change body of generated methods, choose Tools | Templates.
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String todo = request.getParameter("todo");

            if (todo != null) {
                switch (todo) {

                    case "afficher_avatar_nom":
                        GlobalSerialisation global_serialisation = new GlobalSerialisation();
                        global_serialisation.appliquer(request, response);
                        break;

                    case "lister-matieres":
                        ListerMatieresAction lister_matieres_action = new ListerMatieresAction();
                        lister_matieres_action.execute(request);
                        ListerMatieresSerialisation lister_matieres_serialisation = new ListerMatieresSerialisation();
                        lister_matieres_serialisation.appliquer(request, response);
                        break;

                    case "lister-etablissements":
                        ListerEtablissementsAction lister_etablissement_action = new ListerEtablissementsAction();
                        lister_etablissement_action.execute(request);
                        ListerEtablissementsSerialisation lister_etablissement_serialisation = new ListerEtablissementsSerialisation();
                        lister_etablissement_serialisation.appliquer(request, response);
                        break;

                    case "afficher-soutien":
                        AfficherSoutienAction a_s_a = new AfficherSoutienAction();
                        a_s_a.execute(request);
                        AfficherSoutienSerialisation a_s_s = new AfficherSoutienSerialisation();
                        a_s_s.appliquer(request, response);
                        break;

                    case "inscrire-eleve":
                        InscrireEleveAction inscrire_eleve_action = new InscrireEleveAction();
                        inscrire_eleve_action.execute(request);
                        InscrireEleveSerialisation inscrire_eleve_serialisation = new InscrireEleveSerialisation();
                        inscrire_eleve_serialisation.appliquer(request, response);
                        break;

                    case "demande":
                        DemandeAction demande_action = new DemandeAction();
                        demande_action.execute(request);
                        DemandeSerialisation demande_serialisation = new DemandeSerialisation();
                        demande_serialisation.appliquer(request, response);
                        break;

                    case "connexion":
                        ConnexionAction connexion_action = new ConnexionAction();
                        connexion_action.execute(request);
                        ConnexionSerialisation connexion_serialisation = new ConnexionSerialisation();
                        connexion_serialisation.appliquer(request, response);
                        break;
                    case "afficher_profil":
                        AfficherProfilAction profilAction = new AfficherProfilAction();
                        profilAction.execute(request);
                        AfficherProfilSerialisation profilSerial = new AfficherProfilSerialisation();
                        profilSerial.appliquer(request, response);
                        break;

                    case "lien-visio-eleve":
                        LienVisioEleveAction l_v_e_a = new LienVisioEleveAction();
                        l_v_e_a.execute(request);
                        LienVisioEleveSerialisation l_v_e_s = new LienVisioEleveSerialisation();
                        l_v_e_s.appliquer(request, response);
                        break;

                    case "lien-visio-intervenant":
                        LienVisioIntervenantAction l_v_i_a = new LienVisioIntervenantAction();
                        l_v_i_a.execute(request);
                        LienVisioIntervenantSerialisation l_v_i_s = new LienVisioIntervenantSerialisation();
                        l_v_i_s.appliquer(request, response);
                        break;

                    case "seance-terminee-eleve":
                        SeanceTermineeEleveAction s_t_e_a = new SeanceTermineeEleveAction();
                        s_t_e_a.execute(request);
                        SeanceTermineeEleveSerialisation s_t_e_s = new SeanceTermineeEleveSerialisation();
                        s_t_e_s.appliquer(request, response);
                        break;

                    case "bilan-soutien":
                        BilanAction ba = new BilanAction();
                        ba.execute(request);
                        BilanSerialisation bs = new BilanSerialisation();
                        bs.appliquer(request, response);
                        break;

                    case "lister-historique":
                        ListerHistoriqueAction l_h_a = new ListerHistoriqueAction();
                        l_h_a.execute(request);
                        ListerHistoriqueSerialisation l_h_s = new ListerHistoriqueSerialisation();
                        l_h_s.appliquer(request, response);
                        break;

                    case "afficher-detail-soutien":
                        AfficherDetailSoutienAction a_d_s_a = new AfficherDetailSoutienAction();
                        a_d_s_a.execute(request);
                        AfficherDetailSoutienSerialisation a_d_s_s = new AfficherDetailSoutienSerialisation();
                        a_d_s_s.appliquer(request, response);
                        break;
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
