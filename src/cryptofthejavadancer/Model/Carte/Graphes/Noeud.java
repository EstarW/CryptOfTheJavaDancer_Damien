/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.Carte.Graphes;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import java.util.ArrayList;

/**
 *
 * @author dj715494
 */
public class Noeud {
    private Graphe graph;
    private ArrayList<Noeud> voisins;
    private Case c;
    
    public Noeud(Graphe graph, Case c){
        this.graph=graph;
        this.voisins=new ArrayList();
        this.c=c;
    }
    public ArrayList<Noeud> getVoisins(){
        return this.voisins;
    }
    public void setVoisin(Noeud n){
        this.voisins.add(n);
    }
    public Case getCase(){
        return c;
    }

    public void setC(Case c) {
        this.c = c;
    }
    
    @Override
    public String toString(){
        return "["+this.c.getLigne()+","+this.c.getColonne()+"]";
    }
}
