/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA.Monstre;

import cryptofthejavadancer.Model.IA.IA;
import cryptofthejavadancer.Model.IA.Type_Action;

/**
 *
 * @author dj715494
 */
public abstract class Etat {
    private IA ia;
    
    public Etat(IA _ia){
        ia=_ia;
    }
    
    public abstract Type_Action agir();
    public abstract Etat transition();

    public IA getIa() {
        return ia;
    }
}
