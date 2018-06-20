/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA.Monstre.Skeleton;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Cases.Type_Case;
import cryptofthejavadancer.Model.Entites.Type_Entite;
import cryptofthejavadancer.Model.IA.IA;
import cryptofthejavadancer.Model.IA.Monstre.Etat;
import cryptofthejavadancer.Model.IA.Type_Action;

/**
 *
 * @author dj715494
 */
public class EtatDeplacement extends Etat {

    private Type_Action action;
    private boolean bloque;

    public EtatDeplacement(IA _ia, Type_Action _action) {
        super(_ia);
        action=_action;
        bloque=false;
    }

    @Override
    public Type_Action agir() {
        Type_Action res = Type_Action.attendre;
        Case caseSuivante=null;
        switch(action){
            case deplacement_haut:
                caseSuivante=this.getIa().getMap().getCase(this.getIa().getCase().getLigne()-1, this.getIa().getCase().getColonne());
                if (caseSuivante.getType()==Type_Case.Sol){
                    if (caseSuivante.getEntite()==null){
                        res=Type_Action.deplacement_haut;
                    }
                    else if(caseSuivante.getEntite().getType()==Type_Entite.Cadence){
                        res=Type_Action.interagir_haut;
                    }
                }
                else bloque=true;
            break;
            case deplacement_bas:
                caseSuivante=this.getIa().getMap().getCase(this.getIa().getCase().getLigne()+1, this.getIa().getCase().getColonne());
                if (caseSuivante.getType()==Type_Case.Sol){
                    if (caseSuivante.getEntite()==null){
                        res=Type_Action.deplacement_bas;
                    }
                    else if(caseSuivante.getEntite().getType()==Type_Entite.Cadence){
                        res=Type_Action.interagir_bas;
                    }
                }
                else bloque=true;
            break;
            case deplacement_droite:
                caseSuivante=this.getIa().getMap().getCase(this.getIa().getCase().getLigne(), this.getIa().getCase().getColonne()+1);
                if (caseSuivante.getType()==Type_Case.Sol){
                    if (caseSuivante.getEntite()==null){
                        res=Type_Action.deplacement_droite;
                    }
                    else if(caseSuivante.getEntite().getType()==Type_Entite.Cadence){
                        res=Type_Action.interagir_droite;
                    }
                }
                else bloque=true;
            break;
            case deplacement_gauche:
                caseSuivante=this.getIa().getMap().getCase(this.getIa().getCase().getLigne(), this.getIa().getCase().getColonne()-1);
                if (caseSuivante.getType()==Type_Case.Sol){
                    if (caseSuivante.getEntite()==null){
                        res=Type_Action.deplacement_gauche;
                    }
                    else if(caseSuivante.getEntite().getType()==Type_Entite.Cadence){
                        res=Type_Action.interagir_gauche;
                    }
                }
                else bloque=true;
            break;
            case attendre:
                bloque=true;
            break;
        }
        return res;
    }

    @Override
    public Etat transition() {
        Etat res=null;
        if(bloque==true){
            res=new EtatRandom(this.getIa());
        }
        else{
            res=new EtatDeplacement(this.getIa(),action);
        }
        return res;
    }

}
