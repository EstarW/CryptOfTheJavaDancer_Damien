/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA.Monstre.Skeleton;

import cryptofthejavadancer.Model.IA.IA;
import cryptofthejavadancer.Model.IA.Monstre.Etat;
import cryptofthejavadancer.Model.IA.Type_Action;

/**
 *
 * @author dj715494
 */
public class EtatDeplacement extends Etat{
    
    private Type_Action actionPrec;
    private boolean bloque;
    
    public EtatDeplacement(IA _ia, Type_Action _actionPrec) {
        super(_ia);
        actionPrec=_actionPrec;
        bloque=true;
    }

    @Override
    public Type_Action agir() {
        Type_Action res=Type_Action.attendre;
        if (bloque){
            int rand = (int) (Math.random()*(4-1));
            switch (rand){
                case 1:res=Type_Action.deplacement_bas;
                break;
                case 2:res=Type_Action.deplacement_droite;
                break;
                case 3:res=Type_Action.deplacement_gauche;
                break;
                case 4:res=Type_Action.deplacement_haut;
                break;
            }
            bloque=false;
        }
        return res;
    }

    @Override
    public Etat transition() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
