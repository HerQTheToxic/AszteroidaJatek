package model;

import view.MaterialDraw;

import java.io.Serializable;

/**
 * A nyersanyagok absztrakt ososztalya
 */
public abstract class Material implements Serializable {
    /**
     * melyik aszteroidaban van
     */
    protected Asteroid myAsteroid;
    /**
     * ot megjelenito osztaly
     */
    private MaterialDraw myDraw;

    /**
     * setterek, getterek
     * @return
     */
    public MaterialDraw getMyDraw(){ return myDraw; }

    void setAsteroid(Asteroid ast){
        myAsteroid = ast;
    }

    Asteroid getAsteroid(){
        return myAsteroid;
    }

    public abstract String getId();

    /**
     * absztrakt fuggveny, leszarmazottaknak kell majd
     */
    abstract void exposure();

    /**
     * Azt mutatja, hogy a material, hogyan reagal a drill metodusra
     * @param tooCLose - Napkozelben van-e az aszteroida
     */
    abstract void drilled(boolean tooCLose);

    /**
     * Ezzel a függvennyel egy adott bill-nek a materialhoz tartozo parameteret lehet csokkenteni. A kifizetesnel lesz fontos
     * @param b - Maga a kapott bill, amirol levonodik a material
     */
    abstract void updateBill(Bill b);

    /**
     *
     * @param m a parameterkent kapott materialrol eldonti, hogy ugyan az-e, mint amire meg lett hivva
     * @return
     */
    public abstract boolean isEqual(Material m);

}
