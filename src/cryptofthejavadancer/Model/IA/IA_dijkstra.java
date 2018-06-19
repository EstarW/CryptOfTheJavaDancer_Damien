/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Cases.Type_Case;
import cryptofthejavadancer.Model.Carte.Graphes.Algorithmes.Dijkstra;
import cryptofthejavadancer.Model.Carte.Map;
import cryptofthejavadancer.Model.Entites.Entite;
/**
 *
 * @author dj715494
 */
public class IA_dijkstra extends IA{
    
    private Dijkstra algo;
    private Entite entite;
    private boolean mur;
    
    public IA_dijkstra(Entite _entite) {
        super(_entite);
        this.algo=null;
        this.entite=_entite;
        this.mur=false;
    }

    @Override
    public Type_Action action() {
        Type_Action action = Type_Action.attendre;
        //Calcul Dijkstra
        if(this.algo == null) {
            this.algo=new Dijkstra(this.getEntite().getMap().getGraphe_complexe());
        }
        Map map = this.getEntite().getMap();
        if (this.algo.getPath().isEmpty()){
            if (this.entite.getCase() == map.getCase(map.getSortie().getLigne(), map.getSortie().getColonne())){
                action = Type_Action.sortir;
            }
            else{
                this.algo.calcul(map.getGraphe_complexe().getNoeud(this.getCase()),map.getGraphe_complexe().getNoeud(map.getCase(map.getSortie().getLigne(),map.getSortie().getColonne())));
            }
        }
        else {
            action = calculAction(this.algo.getPath().get(0).getCase());
        }
        return action;
    }
    
    public Type_Action calculAction (Case CaseSuivante){
        Case caseCadence = this.entite.getCase();
        Type_Action res = Type_Action.attendre;
        int X = caseCadence.getLigne();
        int Y = caseCadence.getColonne();
        
        //Si la case suivante est une case Sol
        if (CaseSuivante.getType() == Type_Case.Sol){
            //Si la case est vide
            if (CaseSuivante.getEntite() == null){
                res=this.directionDeplacement(X,Y,CaseSuivante);
                this.algo.destroyFirst();
            }
            //Si la case est occupée
            else{
                res=this.directionInteraction(X, Y, CaseSuivante);
            }
        }
        else if (CaseSuivante.getType() == Type_Case.Mur){
            if (this.mur==false){
                res=this.directionInteraction(X, Y, CaseSuivante);
                this.mur=true;
            }
            else{
                res=this.directionDeplacement(X, Y, CaseSuivante);
                this.mur=false;
                this.algo.destroyFirst();
            }
        }
        return res;
    }
    
    
}
