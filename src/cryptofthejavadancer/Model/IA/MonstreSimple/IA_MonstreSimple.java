/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA.MonstreSimple;

import cryptofthejavadancer.Model.Carte.Cases.Type_Case;
import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.IA.IA;
import cryptofthejavadancer.Model.IA.Type_Action;

/**
 *
 * @author dj715494
 */
public class IA_MonstreSimple extends IA{
    
    private Etat etatCourant;
    
    public IA_MonstreSimple(Entite _entite) {
        super(_entite);
        this.etatCourant=new EtatDesc();
    }
    
    @Override
    public Type_Action action() {
        Type_Action res=Type_Action.attendre;
        if (this.getCase().getType()==Type_Case.Sol){
            res=this.etatCourant.agir();
        }
        this.etatCourant=this.etatCourant.transition();
        return res;
    }
    
}
