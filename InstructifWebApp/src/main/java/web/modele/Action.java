/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.modele;

import javax.servlet.http.HttpServletRequest;
import metier.service.Service;
/**
 *
 * @author adidier2
 */
public abstract class Action {
    protected Service service;
    
    public Action(){
        this.service = new Service();
    }
    
    public abstract void execute(HttpServletRequest request);
}
