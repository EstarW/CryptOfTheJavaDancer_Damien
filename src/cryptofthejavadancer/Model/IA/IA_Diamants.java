/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Cases.Type_Case;
import cryptofthejavadancer.Model.Carte.Graphes.Algorithmes.Astar;
import cryptofthejavadancer.Model.Carte.Graphes.Algorithmes.Dijkstra;
import cryptofthejavadancer.Model.Carte.Graphes.Graphe;
import cryptofthejavadancer.Model.Carte.Map;
import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.Objet.Objet;
import cryptofthejavadancer.Model.Objet.Objet_Diamant;
import cryptofthejavadancer.Model.Objet.Type_Objet;
import java.util.ArrayList;

/**
 *
 * @author dj715494
 */
public class IA_Diamants extends IA{
    
    private Dijkstra algo;
    private boolean mur;
    private ArrayList<Objet> diamants;
    private boolean tourUn;
    private Graphe grapheSimple;
    private Map map;

    public IA_Diamants(Entite _entite) {
        super(_entite);
        algo=null;
        mur=false;
        diamants = null;
        tourUn=true;
        grapheSimple=null;
    }
    
    public Objet plusProcheDiamant(Map m){
        Objet res=null;
        Astar algoDiamant = new Astar(grapheSimple);
        int min = algoDiamant.getInfini();
        int dist;
        for(Objet o:diamants){
            algoDiamant.calcul(grapheSimple.getNoeud(this.getCase()), grapheSimple.getNoeud(o.getCase()));
            dist=algoDiamant.getPath().size();
            if (dist<min && dist!=0){
                res=o;
                min=dist;
            }
        }
        return res;
    }
    
    @Override
    public Type_Action action() {
        Type_Action action = Type_Action.attendre;
        if (tourUn){
            map = this.getEntite().getMap();
            //Génération de la liste des diamants
            diamants=new ArrayList<Objet>();
            grapheSimple=map.getGrapheSimple();
            for(Objet o : map.getListeObjet()){
                if (o.getType()==Type_Objet.Diamant){
                    diamants.add(o);
                }
            }
            //Génération de l'algo
            this.algo=new Dijkstra(grapheSimple);
            algo.calcul(grapheSimple.getNoeud(this.getCase()), grapheSimple.getNoeud());
            tourUn=false;
        }
        if ()
        
        return action;
    }
    
    public Type_Action calculAction (Case CaseSuivante){
        Case caseCadence = this.getEntite().getCase();
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
