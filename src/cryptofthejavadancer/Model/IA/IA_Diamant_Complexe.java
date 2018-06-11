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
 * @author Damien
 */
public class IA_Diamant_Complexe extends IA{
    
    private Astar algo;
    private boolean mur;
    private ArrayList<Objet> diamants;
    private boolean tourUn;
    private Graphe graphe_simple;
    private Graphe graphe_complexe;
    private Objet pelle;
    private boolean hasPelle;
    
    public IA_Diamant_Complexe(Entite _entite) {
        super(_entite);
        algo=null;
        mur=false;
        diamants = null;
        tourUn=true;
        graphe_simple=null;
        graphe_complexe=null;
    }
    
    public Objet plusProcheObjet(Map m){
        Objet res=null;
        Astar algoDiamant = new Astar(graphe_simple);
        int min = algoDiamant.getInfini();
        int dist;
        for(Objet o:diamants){
            if (hasPelle){
                algoDiamant.calcul(graphe_complexe.getNoeud(this.getCase()), graphe_complexe.getNoeud(o.getCase()));
                dist=algoDiamant.getPath().size();
                if (dist<min && dist!=0){
                    res=o;
                    min=dist;
                }
            }
            else{
                int distPelle;
                algoDiamant.calcul(graphe_simple.getNoeud(this.getCase()), graphe_simple.getNoeud(o.getCase()));
                dist=algoDiamant.getPath().size();
                Astar algoPelle = new Astar(graphe_simple);
                algoPelle.calcul(graphe_simple.getNoeud(this.getCase()), graphe_simple.getNoeud(pelle.getCase()));
                distPelle=algoPelle.getPath().size();
                Astar algoPelleDiam = new Astar(graphe_simple);
                algoPelleDiam.calcul(graphe_complexe.getNoeud(pelle.getCase()), graphe_complexe.getNoeud(o.getCase()));
                distPelle+=algoPelleDiam.getPath().size();
                if (distPelle<dist){
                    dist=distPelle;
                    res=pelle;
                    System.out.println("Pelle");
                }
                else{
                    res=o;
                    min=dist;
                    System.out.println("Diam");
                }
            }
        }
        return res;
    }
    
    @Override
    public Type_Action action() {
        Type_Action action = Type_Action.attendre;
        Map map = this.getEntite().getMap();
        if (tourUn){
            //Génération de la liste des diamants
            diamants=new ArrayList<Objet>();
            graphe_simple=map.getGrapheSimple();
            hasPelle=false;
            for(Objet o : map.getListeObjet()){
                if (o.getType()==Type_Objet.Diamant){
                    diamants.add(o);
                }
                if (o.getType()==Type_Objet.Pelle){
                    pelle=o;
                }
            }
            //Génération de l'algo
            this.algo=new Astar(graphe_simple);
            
            if (!diamants.isEmpty()){
                algo.calcul(graphe_simple.getNoeud(this.getCase()), graphe_simple.getNoeud(plusProcheObjet(map).getCase()));
                //System.out.println(algo.getPath());
                diamants.remove(plusProcheObjet(map));
            }
            else{
                algo.calcul(graphe_simple.getNoeud(this.getCase()), graphe_simple.getNoeud(map.getCase(map.getSortie().getLigne(),map.getSortie().getColonne())));
                //System.out.println(algo.getPath());
            }
            tourUn=false;
        }
        
        if (this.getEntite().getCase().getObjet()!=null){
            if (this.getEntite().getCase().getObjet().getType()==Type_Objet.Diamant || this.getEntite().getCase().getObjet().getType()==Type_Objet.Pelle){
                
                action=Type_Action.ramasser;
                if (this.getEntite().getCase().getObjet().getType()==Type_Objet.Pelle){
                    graphe_simple=graphe_complexe;
                    hasPelle=true;
                    pelle=null;
                }
                if (!diamants.isEmpty()){
                    algo.calcul(graphe_simple.getNoeud(this.getCase()), graphe_simple.getNoeud(plusProcheObjet(map).getCase()));
                    //System.out.println(algo.getPath());
                    diamants.remove(plusProcheObjet(map));
                }
                else{
                    algo.calcul(graphe_simple.getNoeud(this.getCase()), graphe_simple.getNoeud(map.getCase(map.getSortie().getLigne(),map.getSortie().getColonne())));
                    //System.out.println(algo.getPath());
                }
            }
            else if(this.getEntite().getCase().getObjet().getType()==Type_Objet.Sortie){
                action=Type_Action.sortir;
            }
        }
        else{
            action=calculAction(this.algo.getPath().get(0).getCase());
            //System.out.println(algo.getPath());
        }
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
