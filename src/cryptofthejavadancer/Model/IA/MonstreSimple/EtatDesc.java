/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA.MonstreSimple;

import cryptofthejavadancer.Model.IA.Type_Action;

/**
 *
 * @author dj715494
 */
public class EtatDesc extends Etat{
    
    @Override
    public Type_Action agir() {
        return Type_Action.deplacement_bas;
    }

    @Override
    public Etat transition() {
        return new EtatMont();
    }
    
}
