/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Cases.Case_Sol;
import cryptofthejavadancer.Model.Carte.Cases.Type_Case;
import cryptofthejavadancer.Model.Carte.Graphes.Algorithmes.Astar;
import cryptofthejavadancer.Model.Carte.Graphes.Algorithmes.Dijkstra;
import cryptofthejavadancer.Model.Carte.Graphes.CoupleNoeud;
import cryptofthejavadancer.Model.Carte.Graphes.Graphe;
import cryptofthejavadancer.Model.Carte.Graphes.Noeud;
import cryptofthejavadancer.Model.Carte.Map;
import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.Objet.Objet;
import cryptofthejavadancer.Model.Objet.Type_Objet;
import java.util.ArrayList;

/**
 *
 * @author Damien
 */
public class IA_Diamant_Complexe extends IA{
    
    private Dijkstra dijkstraSimple;
    private Dijkstra dijkstraComplexe;
    private Astar astar;
    private boolean mur;
    private ArrayList<Objet> diamants;
    private boolean tourUn;
    private Graphe grapheSimple;
    private Graphe grapheComplexe;
    private Map map;
    private boolean hasPelle;
    private Objet pelle;
    
    public IA_Diamant_Complexe(Entite _entite) {
        super(_entite);
        map=null;
        grapheSimple=null;
        grapheComplexe=null;
        dijkstraComplexe=null;
        mur=false;
        diamants = null;
        tourUn=true;
        grapheSimple=null;
        astar=null;
        hasPelle=false;
        pelle=null;
    }
    
    @Override
    public Type_Action action() {
        Type_Action action = Type_Action.attendre;
        map = this.getEntite().getMap();
        grapheSimple=map.getGrapheSimple();
        grapheComplexe=map.getGraphe_complexe();
        if (tourUn){
            //Génération de la liste des diamants
            diamants=new ArrayList<Objet>();
            for(Objet o : map.getListeObjet()){
                if (o.getType()==Type_Objet.Diamant){
                    diamants.add(o);
                }
                if (o.getType()==Type_Objet.Pelle){
                    pelle=o;
                }
            }
            //Génération de l'algo
            this.dijkstraSimple=new Dijkstra(grapheSimple);
            this.dijkstraComplexe=new Dijkstra(grapheComplexe);
            astar=new Astar(grapheSimple);
            dijkstraSimple.calcul(grapheSimple.getNoeud(this.getCase()), grapheSimple.getNoeud(map.caseSortie()));
            dijkstraComplexe.calcul(grapheComplexe.getNoeud(this.getCase()),grapheComplexe.getNoeud(map.caseSortie()));
            tourUn=false;
        }
        if (astar.getPath().isEmpty()){  
            //Interaction
            if (this.getCase().getObjet()!=null){
                if (this.getCase().getObjet().getType()==Type_Objet.Diamant){
                    action=Type_Action.ramasser;
                    diamants.remove(this.getCase().getObjet());
                }
                if (this.getCase().getObjet().getType()==Type_Objet.Pelle && !hasPelle){
                    action=Type_Action.ramasser;
                    hasPelle=true;
                    astar.setGraph(grapheComplexe);
                    System.out.println("pelle ramassée");
                    System.out.println(astar.getPath());
                }
                if (this.getCase().getObjet().getType()==Type_Objet.Sortie){
                    action=Type_Action.sortir;
                }
            }
            //Si il y a un diamant accessible on récupère le plus proche
            this.calculDest();
        }
        else{
            action = calculAction(astar.getPath().get(0).getCase());
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
                this.astar.destroyFirst();
            }
            //Si la case est occupée
            else{
                res=this.directionInteraction(X, Y, CaseSuivante);
            }
        }
        else if (CaseSuivante.getType() == Type_Case.Mur){
            res=this.directionInteraction(X, Y, CaseSuivante);
            for(Noeud v : grapheSimple.getNoeuds().values()){
                if (v.getVoisins().contains(grapheSimple.getNoeud(CaseSuivante))){
                    CoupleNoeud vC = new CoupleNoeud(v,grapheSimple.getNoeud(CaseSuivante));
                    grapheSimple.getLabels().replace(vC,2,1);
                }
            }
            for(Noeud n : grapheComplexe.getNoeuds().values()){
                if (n.getVoisins().contains(grapheComplexe.getNoeud(CaseSuivante))){
                    CoupleNoeud vC = new CoupleNoeud(n,grapheComplexe.getNoeud(CaseSuivante));
                    grapheComplexe.getLabels().replace(vC,2,1);
                }
            }
            Case test = new Case_Sol(CaseSuivante.getLigne(),CaseSuivante.getColonne(),getMap());
            Noeud v = grapheSimple.getNoeud(CaseSuivante);
            v.setC(test);
            grapheSimple.getNoeuds().remove(CaseSuivante,v);
            grapheSimple.getNoeuds().put(test,v);
            Noeud n = grapheComplexe.getNoeud(CaseSuivante);
            n.setC(test);
            grapheComplexe.getNoeuds().remove(CaseSuivante,v);
            grapheComplexe.getNoeuds().put(test,v);
        }
        else if (CaseSuivante.getType() == Type_Case.MurDur && hasPelle){
            res=this.directionInteraction(X, Y, CaseSuivante);
            for(Noeud v : grapheSimple.getNoeuds().values()){
                if (v.getVoisins().contains(grapheSimple.getNoeud(CaseSuivante))){
                    CoupleNoeud vC = new CoupleNoeud(v,grapheSimple.getNoeud(CaseSuivante));
                    grapheSimple.getLabels().replace(vC,2,1);
                }
            }
            for(Noeud n : grapheComplexe.getNoeuds().values()){
                if (n.getVoisins().contains(grapheComplexe.getNoeud(CaseSuivante))){
                    CoupleNoeud vC = new CoupleNoeud(n,grapheComplexe.getNoeud(CaseSuivante));
                    grapheComplexe.getLabels().replace(vC,2,1);
                }
            }
            Case test = new Case_Sol(CaseSuivante.getLigne(),CaseSuivante.getColonne(),getMap());
            Noeud v = grapheSimple.getNoeud(CaseSuivante);
            v.setC(test);
            grapheSimple.getNoeuds().remove(CaseSuivante,v);
            grapheSimple.getNoeuds().put(test,v);
            Noeud n = grapheComplexe.getNoeud(CaseSuivante);
            n.setC(test);
            grapheComplexe.getNoeuds().remove(CaseSuivante,v);
            grapheComplexe.getNoeuds().put(test,v);
        }
        return res;
    }
    
    public void calculDest(){
        Case dest=this.getCase();
        boolean diamAcc=false;
        for (Objet o : diamants){
            if (dijkstraComplexe.taillePath(grapheComplexe.getNoeud(o.getCase()))<dijkstraComplexe.getInfini()){
                diamAcc=true;
            }
        }
        if (diamAcc){
            int dist=dijkstraComplexe.getInfini()+1;
            for (Objet o : diamants){
                if (hasPelle){
                    dijkstraComplexe.calcul(grapheComplexe.getNoeud(this.getCase()),grapheComplexe.getNoeud(map.caseSortie()));
                    if (dijkstraComplexe.taillePath(grapheComplexe.getNoeud(o.getCase()))<dist && dijkstraComplexe.taillePath(grapheComplexe.getNoeud(o.getCase()))!=0){
                        dist=dijkstraComplexe.taillePath(grapheComplexe.getNoeud(o.getCase()));
                        dest=o.getCase();
                    }
                }
                else{
                    int distCadDiam = dijkstraSimple.taillePath(grapheSimple.getNoeud(o.getCase()));
                    System.out.println("distCadDiam "+distCadDiam);
                    astar.calcul(grapheSimple.getNoeud(this.getCase()), grapheSimple.getNoeud(pelle.getCase()));
                    //System.out.println(astar.getPath());
                    //System.out.println(astar.getDistance());
                    int distCadPelle = astar.taillePath(grapheComplexe.getNoeud(pelle.getCase()));
                    System.out.println("distCadPelle "+distCadPelle);
                    dijkstraComplexe.calcul(grapheComplexe.getNoeud(pelle.getCase()), grapheComplexe.getNoeud(o.getCase()));
                    int distPelleDiam = dijkstraComplexe.taillePath(grapheComplexe.getNoeud(o.getCase()));
                    System.out.println("distPelleDiam "+distPelleDiam);
                    if (distCadDiam<distCadPelle+distPelleDiam && distCadDiam!=0 && distCadPelle+distPelleDiam!=0){
                        if (distCadDiam<dist){
                            dist=distCadDiam;
                            dest=o.getCase();
                            System.out.println("dest diam");
                        }
                    }
                    else{
                        if (distCadPelle+distPelleDiam<dist){
                            dist=distCadPelle+distPelleDiam;
                            dest=pelle.getCase();
                            System.out.println("dest pelle");
                        }
                    }
                }
                
            }
        }
        else{
            dest=map.caseSortie();
        }
        if (hasPelle){
            astar.calcul(grapheComplexe.getNoeud(this.getCase()),grapheComplexe.getNoeud(dest));
        }
        else{
            astar.calcul(grapheSimple.getNoeud(this.getCase()), grapheSimple.getNoeud(dest));
        }
        
        
        if (dest.getObjet().getType()==Type_Objet.Diamant){
            System.out.println("dest diam final");
        }
        if (dest.getObjet().getType()==Type_Objet.Pelle){
            System.out.println("dest pelle final");
        }
    }
}
