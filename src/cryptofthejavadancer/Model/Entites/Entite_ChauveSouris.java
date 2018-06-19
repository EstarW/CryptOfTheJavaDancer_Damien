package cryptofthejavadancer.Model.Entites;

import cryptofthejavadancer.Model.IA.IA_Immobile;
import cryptofthejavadancer.Model.IA.Monstre.MonstreSimple.IA_MonstreSimple;

/**
 * Chauve Souris
 * @author Matthieu
 */
public class Entite_ChauveSouris extends Entite_Monstre {

//---------- CONSTRUCTEURS -----------------------------------------------------

    public Entite_ChauveSouris() {
        super(1);
        this.setIA(new IA_Immobile(this));
    }
    
//------------------------------------------------------------------------------

//---------- GETEUR/SETEUR -----------------------------------------------------

    @Override
    public Type_Entite getType() {
        return Type_Entite.ChauveSouris;
    }
    
//------------------------------------------------------------------------------

    @Override
    public int getGainOr() {
        return 5;
    }


}
