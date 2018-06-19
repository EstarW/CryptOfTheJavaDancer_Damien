/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Cases.Type_Case;
import cryptofthejavadancer.Model.Carte.Graphes.Algorithmes.Astar;
import cryptofthejavadancer.Model.Carte.Map;
import cryptofthejavadancer.Model.Entites.Entite;
/**
 *
 * @author dj715494
 */
public class IA_Astar extends IA{
    private Astar astar;
    private Entite entite;
    private boolean mur;
    
    public IA_Astar(Entite _entite) {
        super(_entite);
        this.astar=null;
        this.entite=_entite;
        this.mur=false;
    }
    @Override
    public Type_Action action() {
        Type_Action action = Type_Action.attendre;
        //Calcul Dijkstra
        if(this.astar == null) {
            this.astar=new Astar(this.getEntite().getMap().getGraphe_complexe());
        }
        Map map = this.getEntite().getMap();
        if (this.astar.getPath().isEmpty()){
            if (this.entite.getCase() == map.getCase(map.getSortie().getLigne(), map.getSortie().getColonne())){
                action = Type_Action.sortir;
            }
            else{
                this.astar.calcul(map.getGraphe_complexe().getNoeud(this.getCase()),map.getGraphe_complexe().getNoeud(map.getCase(map.getSortie().getLigne(),map.getSortie().getColonne())));
            }
        }
        else {
            action = calculAction(this.astar.getPath().get(0).getCase());
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
                this.astar.destroyFirst();
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
                this.astar.destroyFirst();
            }
        }
        /*
        else if (CaseSuivante.getType() == Type_Case.Mur){
            res=this.directionInteraction(X, Y, CaseSuivante);
            //Si on creuse un mur, on change le type de la case dans le graphe
            for (Noeud v : this.astar.getGraph().getNoeuds().values()){
                if (v.getVoisins().contains(this.astar.getGraph().getNoeud(CaseSuivante))){
                    CoupleNoeud vC = new CoupleNoeud(v,this.astar.getGraph().getNoeud(CaseSuivante));
                    this.astar.getGraph().getLabels().put(vC, 1);
                }
            }
            astar.getGraph().getNoeuds().put(new Case_Sol(CaseSuivante.getLigne(), CaseSuivante.getColonne(),this.getMap()), astar.getGraph().getNoeud(CaseSuivante));
        }
        */
        return res;
    }
}
