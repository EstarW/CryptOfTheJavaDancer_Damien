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
    private int tour;
    private boolean enTour;
    
    public IA_dijkstra(Entite _entite) {
        super(_entite);
        this.algo=null;
        this.tour=0;
        this.enTour=false;
    }

    @Override
    public Type_Action action() {
        Type_Action retour = Type_Action.attendre;
        if (this.tour == 0){
            algo=new Dijkstra(this.getEntite().getMap().getGraphe_complexe());
            Map map = this.getEntite().getMap();
            this.algo.calcul(map.getGraphe_complexe().getNoeud(this.getCase()),map.getGraphe_complexe().getNoeud(map.getCase(map.getSortie().getLigne(),map.getSortie().getColonne())));
            System.out.println(algo.getPath());
        }
        return retour;
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
