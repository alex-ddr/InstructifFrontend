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
import metier.modele.Etablissement;
import metier.service.Service;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author adidier2
 */
public class ListerEtablissementsAction extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        Service service = new Service();
        List<Etablissement> etablissements = service.listerEtablissements();

        List<Map<String, Object>> etabJsonList = new ArrayList<>();

        if (etablissements != null) {
            for (Etablissement etab : etablissements) {
                Map<String, Object> etabJson = new HashMap<>();

                etabJson.put("id", etab.getId());
                etabJson.put("nom", etab.getDenomination());
                etabJson.put("coords", etab.getCoord());
                etabJson.put("ips", etab.getIps());

                double nbSoutien = service.getNombreTotalSoutiensEtablissement(etab);
                double duree = service.getDureeMoyenneSoutiensEtablissement(etab);
                String dureeStr = ((int) duree) + "s";

                etabJson.put("nbSoutien", nbSoutien);
                etabJson.put("dureeMoyenne", dureeStr);

                etabJsonList.add(etabJson);
            }
        }

        request.setAttribute("etabList", etabJsonList);
    }

}
