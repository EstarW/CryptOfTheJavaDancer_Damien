/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA.Monstre.BlueSlime;

import cryptofthejavadancer.Model.IA.IA;
import cryptofthejavadancer.Model.IA.Monstre.Etat;
import cryptofthejavadancer.Model.IA.Type_Action;

/**
 *
 * @author dj715494
 */
public class EtatWait extends Etat{

    public EtatWait(IA _ia) {
        super(_ia);
    }

    @Override
    public Type_Action agir() {
        return Type_Action.attendre;
    }

    @Override
    public Etat transition() {
        return new EtatDesc(this.getIa());
    }
    
}
