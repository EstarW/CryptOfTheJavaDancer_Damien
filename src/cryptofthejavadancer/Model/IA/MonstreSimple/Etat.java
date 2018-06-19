/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptofthejavadancer.Model.IA.MonstreSimple;

import cryptofthejavadancer.Model.IA.Type_Action;

/**
 *
 * @author dj715494
 */
public abstract class Etat {
    public abstract Type_Action agir();
    public abstract Etat transition();
}
