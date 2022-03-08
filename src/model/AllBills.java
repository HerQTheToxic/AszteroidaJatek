package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Tartalmazza a jatekban az osszes Billt,
 * valamint tudja checkolni, hogy van e eleg nyersanyag egy listaban egy   model.Bill-re
 */
public class AllBills implements Serializable {

    /**
     * robothoz kello alapanyagok egy billben
     */
    private final static Bill robotBill = new Bill(1,0,1,1);
    /**
     * teleporthoz kello alapanyagok egy billben
     */
    private final static Bill tpBill = new Bill(0,1,2,1);
    /**
     * nyereshez kello alapanyagok egy billben
     */
    private final static Bill winBill = new Bill(3,3,3,3);

    /**
     * Visszaadja a robotBillt
     * @return - a robotBill, mely tarolja egy robot keszitesehez szükseges nyersanyagok szamat
     */
    public static Bill getRobotBill(){
        return new Bill(robotBill);
    }

    /**
     * Visszaadja a teleportBillt
     * @return - teleportBill, mely tarolja egy teleportkapu-par keszitesehez szükseges nyersanyagok szamat
     */
    public static Bill getTpBill(){ return new Bill(tpBill); }

    /**
     * Visszaadja a winBIllt
     * @return - winBill , mely tarolja, hogy mennyi nyersanyag szükseges a jatek megnyeresehhez
     */
    public static Bill getWinBill(){
        return new Bill(winBill);
    }

    /**
     * Megnezi van e eleg a materials parameterlistaban ahhoz,
     * hogy le tudja valaki craftolni a b   model.Bill parametertbe kerülo objektumot
     * @param bill - a kifizetendo   model.Bill
     * @param materials - a rendelkezesre alo nyersanyag
     * @return - true, ha van eleg nyersanyag a Billhez, false, ha nem.
     */
    public static boolean hasEnough(Bill bill, ArrayList<Material> materials){
        Bill b = bill;
        for (Material material : materials) {
            material.updateBill(b);
        }
        return b.isPaid();
    }


}
