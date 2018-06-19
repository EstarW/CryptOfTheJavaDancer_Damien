/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA.Monstre.Skeleton;

import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.IA.IA;
import cryptofthejavadancer.Model.IA.Monstre.Etat;
import cryptofthejavadancer.Model.IA.Type_Action;

/**
 *
 * @author dj715494
 */
public class Skeleton_Automaton extends IA{
    private Etat etatCourant;

    public Skeleton_Automaton(Entite _entite) {
        super(_entite);
        this.etatCourant=new EtatDeplacement(this,null,true);
    }

    @Override
    public Type_Action action() {
        Type_Action res;
        res=this.etatCourant.agir();
        this.etatCourant=this.etatCourant.transition();
        return res;
    }
    
    
}
