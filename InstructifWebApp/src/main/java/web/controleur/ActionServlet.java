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
