package model;

import view.UranDraw;

import java.io.Serializable;

/**
 * Az model.Uran nyersanyag kezeleset vegzo osztaly
 */
public class Uran extends Material implements Serializable {
    /**
     * hozza tartozo view.UranDraw-t tarolja
     */
    private transient UranDraw myDraw = null;
    /**
     * azonositoja
     */
    public static int ID = 0;
    private int id;

    /**
     * konstruktor
     * @param a melyik aszteroidan van
     */
    public Uran(Asteroid a){
        id = ++ID;
        if(a != null)
            myAsteroid= a;
    }


    /**
     * vissza adja az ID-jat
     * @return
     */
    public String getId(){
        return "ura"+id;
    }

    /**
     * visszaadja a hozza tartozo view.UranDraw-t
     * @return
     */
    public UranDraw getMyDraw(){
        if(myDraw == null)
            myDraw = new UranDraw(this);
        return myDraw;
    }

    /**
     * Tarolja, hogy hany alkalommal volt napkozelben az   model.Uran alapanyag.
     */
    private int expCount = 0;

    /**
     * getter, hogy hanyszor erte nap
     * @return
     */
    public int getExpCount() {
        return expCount;
    }

    /**
     * Megadott   model.Bill-t frissiti, jelzi, hogy o uran material
     * @param b - a   model.Bill
     */
    void updateBill(Bill b){
        b.decrUran();
    }

    /**
     * Lekezeli, hogy az uran napkozelben van-e
     * @param tooClose - napkozelben van-e
     */
    void drilled(boolean tooClose){
        if(tooClose){
            exposure();
        }
    }

    /**
     * Napkozelben erteket noveli, ha ez elerte a harmat, az uran felrobban, es vele együtt az aszteroida is.
     */
    void exposure(){
        expCount++;
        if(expCount >= 3)
        {
            if(myAsteroid != null)
                myAsteroid.asteroidKaboom();
        }
    }

    /**
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
