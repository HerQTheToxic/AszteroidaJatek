package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * model.Being ososztaly, aki el az ez.
 */
public abstract class Being implements Serializable {
    /**
     * az model.Asteroid ahol tartozkodik a model.Being
     */
    protected Asteroid myAst;

    /**
     * Visszaadja a munkas aktualis aszteroidajat
     * @return myAst - Aszteroida, amin a munkas tartozkodik
     */
    public Asteroid getAsteroid(){
        return myAst;
    }
    /**
     * Beallitja, hogy a munkas melyik aszteroidan van.
     * @param uj - az uj aszteroida, amire atlep
     */
    public void setMyAst(Asteroid uj){
        myAst = uj;
    }

    /**
     * A munkas atlep egy masik aszteroidara
     * @param n - a szomszed, amire / amin keresztül lep

     */
    public void move(Neighbour n){
        n.moveTo(this);
    }

    /**
     * Munkas halalat kezeli le
     */
    public abstract void die();

    /**
     * a felrobbanast kezeli le
     */
    public abstract void kaboom();

    /**
     * Mivel a   model.Robot es urleny nem tarol nyersanyagot, nyilvan üres listaval ter vissza. Az Telepes Felülirja ezt a függvenyt
     *
     */
    public ArrayList<Material> getMaterials(){
        return new ArrayList<Material>();
    }

}
