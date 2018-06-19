package cryptofthejavadancer.Model.Carte;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Cases.Type_Case;
import cryptofthejavadancer.Model.Carte.Graphes.Graphe;
import cryptofthejavadancer.Model.Carte.Graphes.Noeud;
import cryptofthejavadancer.Model.Carte.Parseur.Fabrique_Cases;
import cryptofthejavadancer.Model.Carte.Parseur.Parseur;
import cryptofthejavadancer.Model.Entites.Entite;
import cryptofthejavadancer.Model.Entites.Entite_Cadence;
import cryptofthejavadancer.Model.Entites.Type_Entite;
import cryptofthejavadancer.Model.Objet.Objet;
import cryptofthejavadancer.Model.Objet.Type_Objet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Carte du jeu
 * @author Matthieu
 */
public class Map {

    private final HashMap<Coordonnees,Case> hashMapCases;                       //Stockage par coordonnées des cases
    private final ArrayList<Entite> listeEntite;                                //Liste des entités    
    private final ArrayList<Case> listeCase;                                    //Liste des cases
    private final ArrayList<Objet> listeObjet;                                  //Liste des objets
    private Coordonnees depart;                                                 //Position du point de départ
    private Coordonnees fin;                                                    //Position de la sortie
    
    private Entite_Cadence joueur;        //Cadence
    private Graphe graphe_simple;
    private Graphe graphe_complexe;
    
//---------- CONSTRUCTEURS -----------------------------------------------------

    public Map() {
        //Initialisation
        this.hashMapCases = new HashMap<>();
        this.listeCase = new ArrayList<>();
        this.listeEntite = new ArrayList<>();
        this.listeObjet = new ArrayList<>();
        this.joueur = null;
        this.graphe_simple=new Graphe();
        this.graphe_complexe=new Graphe();
    }
    
    //créer la map à partir d'un fichier texte
    public void chargerFichier(String adresseFichier) throws IOException {
        Parseur parseur = new Parseur(adresseFichier,this);
        parseur.lecture();
        /*
        System.out.println("Niveau chargé.");
        System.out.println("Le nombre de case est "+this.listeCase.size());
        System.out.println("Le nombre d'objet est "+this.listeObjet.size());
        System.out.println("Le nombre d'entités est "+this.listeEntite.size());
        int i=0;
        System.out.println("Liste des objets :");
        for (Objet o : listeObjet){
            System.out.println("\n"+o.getType());
            if (o.getType()==Type_Objet.Diamant){
                i++;
            }
        }
        System.out.println("Le nombre de diamant sur la map est :"+i);
        System.out.println("La case de fin est à la ligne : "+fin.getLigne()+"\n\tcolonne : "+fin.getColonne());
        int j=0;
        System.out.println("Liste des entites :");
        for (Entite e : listeEntite){
            System.out.println("\n"+e.getType());
        }
        System.out.println("Cadence est sur la case "+this.joueur.getCase().getLigne()+" "+this.joueur.getCase().getColonne());
        System.out.println("La case à gauche de Cadence est une case "+this.getCase(this.joueur.getCase().getLigne(),this.joueur.getCase().getColonne()-1).getType());
        //Il est possible de rajouter ICI des choses se réalisant juste après le chargement de la carte...
        */
        this.genererGrapheSimple();
        //this.graphe_simple.afficheMatriceAdjacence();
        this.genererGrapheComplexe();
        //this.graphe_complexe.afficheMatriceAdjacence();

    }
//------------------------------------------------------------------------------

//---------- GETEUR/SETEUR -----------------------------------------------------

    //Renvoie la case présente à ses coordonnées
    public Case getCase(int ligne,int colonne) {
        return this.hashMapCases.get(new Coordonnees(ligne,colonne));
    }
    
    //Change la case à ses coordonnées
    public void setCase(int ligne, int colonne, Case _case) {
        this.hashMapCases.put(new Coordonnees(ligne,colonne), _case);
        this.listeCase.add(_case);
    }
    
    //Change le type de case à ses coordonnées (lors du minage)
    public void changeTypeCase(Case caseInitiale, Type_Case typeNouvelleCase) {
        Case nouvelleCase = Fabrique_Cases.construireCase(typeNouvelleCase, caseInitiale.getLigne(), caseInitiale.getColonne(), this);
        this.setCase(caseInitiale.getLigne(), caseInitiale.getColonne(), nouvelleCase);
        this.listeCase.remove(caseInitiale);
        
        if (this.graphe_simple!=null){
            this.graphe_simple.replaceCase(caseInitiale, nouvelleCase);
        }
        if (this.graphe_complexe!=null){
            this.graphe_complexe.replaceCase(caseInitiale, nouvelleCase);
        }
    }
    
    
    //Ajoute une entité à la position donnée
    public void ajouteEntite(int ligne,int colonne, Entite entite) {
        this.getCase(ligne, colonne).setEntite(entite);
        entite.setCase(this.getCase(ligne, colonne));
        if(entite.getType() != Type_Entite.Cadence) {
            this.listeEntite.add(entite);
        }
    }
    
    //Fixe le joueur
    public void setJoueur(Entite_Cadence cadence) {
        this.joueur = cadence;
    }
    
    //Ajoute un objet à la position donnée
    public void ajouteObjet(int ligne,int colonne, Objet objet) {
        this.getCase(ligne, colonne).setObjet(objet);
        objet.setCase(this.getCase(ligne, colonne));
        this.listeObjet.add(objet);
    }
    
    //Renvoie la hashmap des cases
    public HashMap<Coordonnees,Case> getHashMapCases() {
        return this.hashMapCases;
    }
    
    //Fixe le point de départ
    public void setDepart(int numLigne,int numColonne) {
        this.depart = new Coordonnees(numLigne,numColonne);
    }
    
    //Fixe la position de la sortie
    public void setSortie(int numLigne,int numColonne) {
        this.fin = new Coordonnees(numLigne,numColonne);
    }
    
    //Renvoie la position du point de départ
    public Coordonnees getDepart() {
        return this.depart;
    }
    
    //Renvoie la position du point de sortie
    public Coordonnees getSortie() {
        return this.fin;
    }
    
    //Renvoie la liste des entités
    public ArrayList<Entite> getListeEntite() {
        return this.listeEntite;
    }
    
    //Renvoie la liste des cases
    public ArrayList<Case> getListeCase() {
        return this.listeCase;
    }
    
    //Renvoie la liste des objets
    public ArrayList<Objet> getListeObjet() {
        return this.listeObjet;
    }
    
    //Renvoie le joueur
    public Entite_Cadence getJoueur() {
        return this.joueur;
    }
    public Graphe getGrapheSimple(){
        return this.graphe_simple;
    }

    public Graphe getGraphe_complexe() {
        return graphe_complexe;
    }
    
    
    private void genererGrapheSimple(){
        //Génération des sommets
        for (Case c : this.listeCase){
            this.graphe_simple.addNoeud(c);
        }
        //Génération des edges
        for (Case c1 : this.listeCase){
            for (Case c2 : this.listeCase){
                if ((Math.abs(c1.getLigne()-c2.getLigne())+Math.abs(c1.getColonne()-c2.getColonne()))==1){
                    switch (c2.getType())
                    {
                        case Sol:
                            this.graphe_simple.addEdge(c1, c2);
                            this.graphe_simple.setLabel(c1,c2,1);
                            break;
                        case Mur:
                            this.graphe_simple.addEdge(c1, c2);
                            this.graphe_simple.setLabel(c1,c2,2);
                            break;
                        default :
                            //System.out.println("Bruh");
                            break;
                    }
                }
            }
        }
        
    }
    private void genererGrapheComplexe(){
        //Génération des sommets
        for (Case c : this.listeCase){
            this.graphe_complexe.addNoeud(c);
        }
        //Génération des edges
        for (Case c1 : this.listeCase){
            for (Case c2 : this.listeCase){
                if ((Math.abs(c1.getLigne()-c2.getLigne())+Math.abs(c1.getColonne()-c2.getColonne()))==1){
                    switch (c2.getType())
                    {
                        case Sol:
                            this.graphe_complexe.addEdge(c1, c2);
                            this.graphe_complexe.setLabel(c1,c2,1);
                            break;
                        case Mur:
                            this.graphe_complexe.addEdge(c1, c2);
                            this.graphe_complexe.setLabel(c1,c2,2);
                            break;
                        case MurDur:
                            this.graphe_complexe.addEdge(c1, c2);
                            this.graphe_complexe.setLabel(c1,c2,2);
                            break;
                        default :
                            //System.out.println("Bruh");
                            break;
                    }
                }
            }
        }
        
    }
    
    public Case caseSortie(){
        return this.getCase(this.fin.getLigne(), this.fin.getColonne());
    }
    
//------------------------------------------------------------------------------

}
