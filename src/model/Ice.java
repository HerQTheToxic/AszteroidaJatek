package model;

import view.IceDraw;

import java.io.Serializable;

/**
 * A Jeg nyersanyag kezeleset vegzo osztaly
 */
public class Ice extends Material implements Serializable {
    /**
     * hozzatartozo megjelenjto objektum
     */
    private transient IceDraw myDraw = null;
    /**
     * azonosito
     */
    public static int ID = 0;
    private int id;

    /**
     * konstruktor
     * @param a
     */
    public Ice(Asteroid a){
        id = ++ID;
        if(a != null)
            myAsteroid= a;
        myDraw = new IceDraw(this);
    }

    /**
     * setter, getter
     * @return
     */
    public String getId(){
        return "ice"+id;
    }
    public IceDraw getMyDraw(){
        if(myDraw == null)
            myDraw = new IceDraw(this);
        return myDraw;
    }

    /**
     * Megadott   model.Bill-t frissiti, jelzi, hogy jeg material
     * @param b - a   model.Bill
     */
    void updateBill(Bill b){
        b.decrIce();
    }

    /**
     * Lekezeli, hogy a jeg napkozelben van-e
     * @param tooClose - napkozelben van-e
     */
    void drilled(boolean tooClose){
        if(tooClose) {
            exposure();
        }
    }

    /**
     * A jeg napkozelben elszublimal, aszteroidajanak magja üresse valik.
     */
    void exposure(){
        myAsteroid.setCore(null);
    }

    /**
     *
     * @param m a parameterkent kapott materialrol eldonti, hogy ugyan az-e, mint amire meg lett hivva
     * @return
     */

    public boolean isEqual(Material m){
        return (this.getClass() == m.getClass());
    }

    @Override
    public String toString(){
        return getId();
    }

}
