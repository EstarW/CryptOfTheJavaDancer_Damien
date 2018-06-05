/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Cases.Type_Case;
import cryptofthejavadancer.Model.Entites.Entite;

/**
 *
 * @author dj715494
 */
public class IA_droite extends IA{
    
    private int tour=0;
    
    public IA_droite(Entite _entite) {
        super(_entite);
    }

    @Override
    public Type_Action action() {
        Case c;
        c=getMap().getCase(getMap().getJoueur().getCase().getLigne(),getMap().getJoueur().getCase().getColonne()+1);
        if (getMap().getJoueur().getCase().getObjet()!=null){
            return Type_Action.ramasser;
        }
        else{
            if (c.getType()==Type_Case.Sol && c.getEntite()==null && c.getObjet()==null){
                return Type_Action.deplacement_droite;
            }
            if (c.getType()==Type_Case.MurIndestructible){
                return Type_Action.attendre;
            }
            if (c.getType()==Type_Case.Mur || c.getEntite()!=null){
                return Type_Action.interagir_droite;
            }
        }
        return Type_Action.attendre;
    }
    
}
