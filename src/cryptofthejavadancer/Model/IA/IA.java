package cryptofthejavadancer.Model.IA;

import cryptofthejavadancer.Model.Carte.Cases.Case;
import cryptofthejavadancer.Model.Carte.Cases.Type_Case;
import cryptofthejavadancer.Model.Carte.Graphes.Noeud;
import cryptofthejavadancer.Model.Carte.Map;
import cryptofthejavadancer.Model.Entites.Entite;

/**
 * Classe générique des IA
 * @author Matthieu
 */
public abstract class IA {

    private Entite entite;                                                      //Entité liée à l'IA
    
//---------- CONSTRUCTEURS -----------------------------------------------------
    
    public IA(Entite _entite) {
        this.entite = _entite;
    }
//------------------------------------------------------------------------------

//---------- GETEUR/SETEUR -----------------------------------------------------

    //Renvoie l'entité
    public Entite getEntite() {
        return this.entite;
    }
    
    //Renvoie la case de l'entité
    public Case getCase() {
        return this.entite.getCase();
    }
    
    //Renvoie la carte du jeu
    public Map getMap() {
        return this.entite.getMap();
    }
    
    //Méthode appelé à chaque pulsation de la musique et devant renvoyer l'action à réaliser
    public abstract Type_Action action();
//------------------------------------------------------------------------------

    public Type_Action directionDeplacement(int X, int Y, Case caseSuivante){
        Type_Action res=Type_Action.attendre;
        if (caseSuivante.getLigne() == (X - 1)){
            res = Type_Action.deplacement_haut;
        }
        else if (caseSuivante.getLigne() == (X+1)){
            res = Type_Action.deplacement_bas;
        }
        else if (caseSuivante.getColonne() == (Y-1)){
            res = Type_Action.deplacement_gauche;
        }
        else if (caseSuivante.getColonne() == (Y+1) ){
            res = Type_Action.deplacement_droite;
        }
        return res;
    }
    
    public Type_Action directionInteraction(int X, int Y, Case caseSuivante){
        Type_Action res = Type_Action.attendre;
        if (caseSuivante.getLigne() == (X - 1)){
            res = Type_Action.interagir_haut;
        }
        else if (caseSuivante.getLigne() == (X+1)){
            res = Type_Action.interagir_bas;
        }
        else if (caseSuivante.getColonne() == (Y-1)){
            res = Type_Action.interagir_gauche;
        }
        else if (caseSuivante.getColonne() == (Y+1) ){
            res = Type_Action.interagir_droite;
        }
        if (caseSuivante.getType()==Type_Case.Mur){
            Noeud n = this.getMap().getGraphe_complexe().getNoeud(caseSuivante);
            for (Noeud v : this.getMap().getGraphe_complexe().getNoeuds().values()){
                if (n.getVoisins().contains(v)){
                    this.getMap().getGraphe_complexe().setLabel(v, n, 1);
                }
            }
        }
        return res;
    }
}
