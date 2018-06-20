/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA.Monstre.Bat;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Cases.Type_Case;
import cryptofthejavadancer.Model.IA.IA;
import cryptofthejavadancer.Model.IA.Monstre.Etat;
import cryptofthejavadancer.Model.IA.Type_Action;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author dj715494
 */
public class EtatRandom extends Etat{

    public EtatRandom(IA _ia) {
        super(_ia);
    }

    @Override
    public Type_Action agir() {
        ArrayList<Case> voisines=new ArrayList<Case>();
        Case voisineHaut=this.getIa().getMap().getCase(this.getIa().getCase().getLigne()-1, this.getIa().getCase().getColonne());
        Case voisineBas=this.getIa().getMap().getCase(this.getIa().getCase().getLigne()+1, this.getIa().getCase().getColonne());
        Case voisineGauche=this.getIa().getMap().getCase(this.getIa().getCase().getLigne(), this.getIa().getCase().getColonne()-1);
        Case voisineDroite=this.getIa().getMap().getCase(this.getIa().getCase().getLigne(), this.getIa().getCase().getColonne()+1);
        if (voisineHaut.getType()==Type_Case.Sol){
            voisines.add(voisineHaut);
        }
        if (voisineBas.getType()==Type_Case.Sol){
            voisines.add(voisineBas);
        }
        if (voisineGauche.getType()==Type_Case.Sol){
            voisines.add(voisineGauche);
        }
        if (voisineDroite.getType()==Type_Case.Sol){
            voisines.add(voisineDroite);
        }
        Random rand = new Random();
        int n=rand.nextInt(voisines.size());
        switch(n){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
        return null;
    }

    @Override
    public Etat transition() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
