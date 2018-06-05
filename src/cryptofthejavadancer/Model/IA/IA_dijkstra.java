/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Cases.Case_Sol;
import cryptofthejavadancer.Model.Carte.Cases.Type_Case;
import cryptofthejavadancer.Model.Carte.Graphes.Algorithmes.Dijkstra;
import cryptofthejavadancer.Model.Carte.Map;
import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.Entites.Type_Entite;
import cryptofthejavadancer.Model.Objet.Objet;
import cryptofthejavadancer.Model.Objet.Objet_Diamant;
import cryptofthejavadancer.Model.Objet.Type_Objet;

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
        this.algo=new Dijkstra(this.getEntite().getMap().getGraphe_complexe());
        Map map = this.getEntite().getMap();
        this.algo.calcul(map.getGraphe_complexe().getNoeud(this.getCase()),map.getGraphe_complexe().getNoeud(map.getCase(map.getSortie().getLigne(),map.getSortie().getColonne())));
        //Affichage du chemin
        //System.out.println(algo.getPath());
        
        if (this.algo.getPath().isEmpty()){
            action = Type_Action.sortir;
        }
        else {
            action = calculAction(this.algo.getPath().get(0).getCase());
        }
        //this.algo.destroy();
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
    
    public void plusProcheObjet(Map m){
        Objet_Diamant res;
        for(Objet o:m.getListeObjet()){
            if(o.getType()==Type_Objet.Diamant){
                this.algo=new Dijkstra(m.getGraphe_complexe());
                this.algo.calcul(m.getGraphe_complexe().getNoeud(this.getCase()), m.getGraphe_complexe().getNoeud(o.getCase()));
                
            }
        }
    }
}
