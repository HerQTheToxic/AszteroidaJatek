package model;

import view.CoalDraw;

import java.io.Serializable;

/**
 * A Szen nyersanyag kezeleset vegzo osztaly
 */
public class Coal extends Material implements Serializable {

    /** observer,m kirajzolashoz */
    transient private CoalDraw myDraw = null;

    /** asteroidok id-je */
    public static int ID = 0;

    /** szen sorszama */
    private int id;

    /**
     * konstruktor
     * @param a - melyik aszteroidaban van
     */
    public Coal(Asteroid a){
        id = ++ID;
        if(a != null)
            myAsteroid= a;
        myDraw = new CoalDraw(this);
    }

    /**
     * visszaadja az egyedi nevet
     * @return - id
     */
    public String getId(){
        return "coa"+id;
    }

    /**
     * visszaadja az observert
     * @return - observer
     */
    public CoalDraw getMyDraw(){
        if(myDraw == null)
            myDraw = new CoalDraw(this);
        return myDraw;
    }

    /**
     * billt frissiti, kivesz egy coalt belole
     * @param b - Maga a kapott bill, amirol levonodik a material
     */
    void updateBill(Bill b){
        b.decrCoal();
    }

    /**
     * robbanasnal mi tortenik
     */
    void exposure(){    }

    /**
     * megfurodasnal ha tortenne vele valami
     * @param b - napkozelseg lenne
     */
    void drilled(boolean b){

    }

    /**
     * gettype hoz kell
     * @param m a parameterkent kapott materialrol eldonti, hogy ugyan az-e, mint amire meg lett hivva
     * @return
     */
    public boolean isEqual(Material m){
        return (this.getClass() == m.getClass());
    }

    /**
     * stringge varazsolja a szenet
     * @return - id
     */
    @Override
    public String toString(){
        return getId();
    }

}
