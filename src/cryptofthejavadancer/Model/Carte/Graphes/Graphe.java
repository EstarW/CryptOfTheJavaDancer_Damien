/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.Carte.Graphes;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import java.util.HashMap;

/**
 *
 * @author dj715494
 */
public class Graphe {
    private HashMap<Case,Noeud> Noeuds;
    private HashMap<CoupleNoeud,Integer> Labels;
    
    public Graphe(){
        this.Noeuds=new HashMap();
        this.Labels=new HashMap();
    }
    public void addNoeud(Case c){
        this.Noeuds.put(c, new Noeud(this,c));
    }
    public void addEdge(Case c1, Case c2){
        Noeud n1=this.getNoeud(c1);
        Noeud n2=this.getNoeud(c2);
        n1.setVoisin(n2);
        n2.setVoisin(n1);
    }
    public Noeud getNoeud(Case c){
        return this.Noeuds.get(c);
    }
    public Integer getLabel(Case c1,Case c2){
        return Labels.get(new CoupleNoeud(Noeuds.get(c1),Noeuds.get(c2)));
    }
    public void setLabel(Case c1,Case c2,int nb){
        this.Labels.put(new CoupleNoeud(Noeuds.get(c1),Noeuds.get(c2)), nb);
    }

    public HashMap<CoupleNoeud, Integer> getLabels() {
        return Labels;
    }
    public void afficheMatriceAdjacence(){
        for (Case c1:Noeuds.keySet()){
            System.out.println("");
            for (Case c2:Noeuds.keySet()){
                if (this.getLabel(c1, c2)!=null){
                    System.out.print(this.getLabel(c1, c2));
                }
                else{
                    System.out.print(0);
                }
            }
        }
    }

    public HashMap<Case, Noeud> getNoeuds() {
        return Noeuds;
    }
    
    public void replaceCase(Case caseInitiale, Case nouvelleCase){
        Noeud n = this.getNoeud(caseInitiale);
        n.setC(nouvelleCase);
        this.Noeuds.remove(caseInitiale);
        this.Noeuds.put(nouvelleCase, n);
        for (Noeud v : this.getNoeuds().values()) {
                if (v.getVoisins().contains(this.getNoeud(nouvelleCase))) {
                    CoupleNoeud vC = new CoupleNoeud(v, this.getNoeud(nouvelleCase));
                    this.getLabels().replace(vC, 2, 1);
                }
            }
    }
    
}
