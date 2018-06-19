/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA.Monstre.Skeleton;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Cases.Type_Case;
import cryptofthejavadancer.Model.IA.IA;
import cryptofthejavadancer.Model.IA.Monstre.Etat;
import cryptofthejavadancer.Model.IA.Type_Action;

/**
 *
 * @author dj715494
 */
public class EtatDeplacement extends Etat {

    private Type_Action actionPrec;
    private boolean bloque;
    private boolean choc;
    private Case caseSuivante;

    public EtatDeplacement(IA _ia, Type_Action _actionPrec, boolean _bloque) {
        super(_ia);
        actionPrec = _actionPrec;
        bloque = _bloque;
        choc = true;
        caseSuivante = null;
    }

    @Override
    public Type_Action agir() {
        Type_Action res = Type_Action.attendre;
        while (choc) {
            if (bloque) {
                int rand = (int) (Math.random() * 4);
                switch (rand) {
                    case 1:
                        res = Type_Action.deplacement_bas;
                        caseSuivante = this.getIa().getMap().getCase(this.getIa().getCase().getLigne() + 1, this.getIa().getCase().getColonne());
                        if (caseSuivante.getType() == Type_Case.Sol && caseSuivante.getEntite() == null) {
                            choc = false;
                        }
                        break;
                    case 2:
                        res = Type_Action.deplacement_droite;
                        caseSuivante = this.getIa().getMap().getCase(this.getIa().getCase().getLigne(), this.getIa().getCase().getColonne() + 1);
                        if (caseSuivante.getType() == Type_Case.Sol && caseSuivante.getEntite() == null) {
                            choc = false;
                        }
                        break;
                    case 3:
                        res = Type_Action.deplacement_gauche;
                        caseSuivante = this.getIa().getMap().getCase(this.getIa().getCase().getLigne(), this.getIa().getCase().getColonne() - 1);
                        if (caseSuivante.getType() == Type_Case.Sol && caseSuivante.getEntite() == null) {
                            choc = false;
                        }
                        break;
                    case 4:
                        res = Type_Action.deplacement_haut;
                        caseSuivante = this.getIa().getMap().getCase(this.getIa().getCase().getLigne() - 1, this.getIa().getCase().getColonne());
                        if (caseSuivante.getType() == Type_Case.Sol && caseSuivante.getEntite() == null) {
                            choc = false;
                        }
                        break;
                }
                actionPrec=res;
            }
            bloque = false;
            
        }
        return res;
    }

    @Override
    public Etat transition() {
        Etat res=null;
        switch(actionPrec){
            case deplacement_bas:caseSuivante = this.getIa().getMap().getCase(this.getIa().getCase().getLigne() + 1, this.getIa().getCase().getColonne());
            break;
            case deplacement_droite:caseSuivante = this.getIa().getMap().getCase(this.getIa().getCase().getLigne(), this.getIa().getCase().getColonne()+1);
            break;
            case deplacement_gauche:caseSuivante = this.getIa().getMap().getCase(this.getIa().getCase().getLigne(), this.getIa().getCase().getColonne()-1);
            break;
            case deplacement_haut:caseSuivante = this.getIa().getMap().getCase(this.getIa().getCase().getLigne()-1, this.getIa().getCase().getColonne());
            break;
        }
        if (caseSuivante.getType() == Type_Case.Sol && caseSuivante.getEntite() == null){
            res=new EtatDeplacement(this.getIa(), actionPrec, false);
        }
        else{
            res=new EtatDeplacement(this.getIa(), null, true);
        }
        return res;
    }

}
