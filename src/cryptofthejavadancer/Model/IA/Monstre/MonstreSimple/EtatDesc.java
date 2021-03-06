/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA.Monstre.MonstreSimple;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Cases.Type_Case;
import cryptofthejavadancer.Model.IA.IA;
import cryptofthejavadancer.Model.IA.Monstre.Etat;
import cryptofthejavadancer.Model.IA.Type_Action;

/**
 *
 * @author dj715494
 */
public class EtatDesc extends Etat{

    public EtatDesc(IA _ia) {
        super(_ia);
    }
    
    @Override
    public Type_Action agir() {
        Type_Action res=Type_Action.attendre;
        Case caseSuivante=this.getIa().getMap().getCase(this.getIa().getCase().getLigne()+1, this.getIa().getCase().getColonne());
        if (caseSuivante.getType()==Type_Case.Sol && caseSuivante.getEntite()==null){
            res=Type_Action.deplacement_bas;
        }
        return res;
    }

    @Override
    public Etat transition() {
        return new EtatMont(this.getIa());
    }
    
}
