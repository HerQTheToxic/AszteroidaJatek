package model;

import view.IronDraw;

import java.io.Serializable;

/**
 * A Vas nyersanyag kezeleset vegzo osztaly
 */
public class Iron extends Material implements Serializable {
    /**
     * ot megjelenito objektum
     */
    private transient IronDraw myDraw = null;
    /**
     * ID
     */
    public static int ID = 0;
    private int id;

    /**
     * konstruktor
     * @param a
     */
    public Iron(Asteroid a){
        id = ++ID;
        if(a != null)
            myAsteroid= a;
        myDraw = new IronDraw(this);
    }

    /**
     * setter, getter
     * @return
     */

    public String getId(){
        return "iro"+id;
    }
    public IronDraw getMyDraw(){
        if(myDraw == null)
            myDraw = new IronDraw(this);
        return myDraw;
    }

    /**
     * levon egyet a bill-bol
     * @param b - Maga a kapott bill, amirol levonodik a material
     */
    void updateBill(Bill b){
        b.decrIron();
    }

    void exposure(){ }
    void drilled(boolean tooCLose) { }

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
