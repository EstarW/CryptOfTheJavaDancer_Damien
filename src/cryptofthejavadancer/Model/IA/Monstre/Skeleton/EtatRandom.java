/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA.Monstre.Skeleton;

import cryptofthejavadancer.Model.IA.IA;
import cryptofthejavadancer.Model.IA.Monstre.Etat;
import cryptofthejavadancer.Model.IA.Type_Action;
import java.util.Random;

/**
 *
 * @author dj715494
 */
public class EtatRandom extends Etat{
    
    private Type_Action action;

    public EtatRandom(IA _ia) {
        super(_ia);
        action=Type_Action.attendre;
    }

    @Override
    public Type_Action agir() {
        Random rand=new Random();
        int n=rand.nextInt(4);
        switch(n){
            case 0:action=Type_Action.deplacement_haut;
            break;
            case 1:action=Type_Action.deplacement_bas;
            break;
            case 2:action=Type_Action.deplacement_gauche;
            break;
            case 3:action=Type_Action.deplacement_droite;
            break;
        }
        return Type_Action.attendre;
    }

    @Override
    public Etat transition() {
        return new EtatDeplacement(this.getIa(),action);
    }
    
}
