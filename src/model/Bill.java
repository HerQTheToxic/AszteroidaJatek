package model;

import java.io.Serializable;

/**
 * Ez az osztaly felel az recepthez szükseges alapanyagok nyilvantartasaert
 */
public class Bill implements Serializable {

    /**
     * a billhez kello coal
     */
    private int coal;

    /**
     * a billhez kello ice
     */
    private int ice;

    /**
     * a billhez kello iron
     */
    private int iron;

    /**
     * a billhez kello uran
     */
    private int uran;

    /**
     * Letrehozza a   model.Bill-t a kapott alapanyag-mennyisegek szerint
     * @param nCoal - a   model.Bill-hez szükkseges szen szama
     * @param nIce - a   model.Bill-hez szükkseges jeg szama
     * @param nIron - a   model.Bill-hez szükkseges vas szama
     * @param nUran - a   model.Bill-hez szükkseges uran szama
     */
    public Bill(int nCoal,int nIce, int nIron, int nUran){
        coal = nCoal;
        ice = nIce;
        iron = nIron;
        uran = nUran;
    }

    /**
     * masolo konstruktor
     * @param bill a masolando bill
     */
    public Bill(Bill bill){
        coal = bill.getCoal();
        ice = bill.getIce();
        iron = bill.getIron();
        uran = bill.getUran();
    }

    /**
     * visszaadja, hogy a bill ki lett e fizetve
     * @return true, ha minden attributumanak erteke 0, egyebkent false
     */
    public boolean isPaid(){
        return coal <= 0 && ice <= 0 && iron <= 0 && uran <= 0;
    }

    /**
     * Csokkenti az adott craftolashoz szükseges vas szamat egy egyseggel
     */
    public void decrIron(){ iron--; }

    /**
     * Csokkenti az adott craftolashoz szükseges szen szamat egy egyseggel
     */
    public void decrCoal(){ coal--; }

    /**
     * Csokkenti az adott craftolashoz szükseges uran szamat egy egyseggel
     */
    public void decrUran(){ uran--; }

    /**
     * Csokkenti az adott craftolashoz szükseges jeg szamat egy egyseggel
     */
    public void decrIce(){ ice--; }

    /**
     * iron getter
     * @return iron
     */
    public int getIron(){ return iron; }

    /**
     * coal getter
     * @return coal
     */
    public int getCoal(){ return coal; }

    /**
     * uran getter
     * @return coal
     */
    public int getUran(){ return uran; }

    /**
     * ice getter
     * @return ice
     */
    public int getIce(){ return ice; }

}
