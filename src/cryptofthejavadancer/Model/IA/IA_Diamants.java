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
import cryptofthejavadancer.Model.Objet.Objet;
import cryptofthejavadancer.Model.Objet.Objet_Diamant;
import cryptofthejavadancer.Model.Objet.Type_Objet;

/**
 *
 * @author dj715494
 */
public class IA_Diamants extends IA{
    
    private Dijkstra dijkstra;
    private boolean mur;

    public IA_Diamants(Entite _entite) {
        super(_entite);
        dijkstra=null;
        mur=false;
    }
    
    public void plusProcheObjet(Map m){
        Objet_Diamant res;
        for(Objet o:m.getListeObjet()){
            if(o.getType()==Type_Objet.Diamant){
                this.dijkstra=new Dijkstra(m.getGraphe_complexe());
                this.dijkstra.calcul(m.getGraphe_complexe().getNoeud(this.getCase()), m.getGraphe_complexe().getNoeud(o.getCase()));
                
            }
        }
    }
    
    @Override
    public Type_Action action() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                this.dijkstra.destroyFirst();
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
                this.dijkstra.destroyFirst();
            }
        }
        return res;
    }
    
}
