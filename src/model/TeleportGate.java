package model;

import view.Drawable;
import view.TeleportGateDraw;

import java.io.Serializable;
import java.util.Random;

/**
 * A teleportkapuk kezelesert felelos osztaly
 */
public class TeleportGate implements Neighbour, Steppable, Serializable {
    /**
     * Hozza tartozo par
     */
    private TeleportGate myPair;
    /**
     * Meg van-e bolondulva a kapu
     */
    boolean isCrazy = false;
    /**
     * melyik aszteroidan van
     */
    private Asteroid myAst;
    private int timeTillNextMove = -1;
    /**
     * tarolja az ot kirajzolo objektumot
     */
    private transient TeleportGateDraw myDraw = null;
    /**
     * melyik szetler hozta letre?
     */
    private Settler creatorSettler;
    /**
     * steppeltetesi prioritas
     */
    private static int priority = 5;
    /**
     * id
     */
    /**
     * azonositoja
     */
    public static int ID = 0;

    private int id;

    /**
     * getterek, setter-ek
     * @return
     */

    public int getPriority(){
        return priority;
    }

    @Override
    public Drawable getDrawable() {
        if(myDraw == null)
            myDraw = new TeleportGateDraw(this);
        return myDraw;
    }

    public void setTimeTillNextMove(int time){
        timeTillNextMove = time;
    }

    public boolean isCrazy(){
        return isCrazy;
    }


    /**
     * konstruktor
     * @param creator
     */
    public TeleportGate(Settler creator){
        myDraw = new TeleportGateDraw(this);

        id = ++ID;
        creatorSettler = creator;
    }

    /**
     * konstruktor
     * @param a
     */
    public TeleportGate(Asteroid a){
        myDraw = new TeleportGateDraw(this);
        id = ++ID;
        a.addTeleportGate(this);
        setMyAst(a);
    }

    /**
     * getter-ek
     * @return
     */
    public Settler getCreatorSettler(){
        return creatorSettler;
    }

    public String getId() {
        return "tpg"+id;
    }
    /**
     * megkergül az teleportGate , az elso megkergült elmozdulas idopontjat kisorsolja
     */
    public void goCrazy(){
        isCrazy = true;
        timeTillNextMove = 1 + new Random().nextInt(10);
    }

    /**
     * beallitja hogy melyik aszteroidan van a teleportgate
     * @param myAst az uj asteroidaja
     */
    public void setMyAst(Asteroid myAst) {
        this.myAst = myAst;
    }
    public Asteroid getMyAst() {
        return myAst;
    }


    /**
     * egy leny hasznalja a teleportot es igy a parja aszteroidajara kerül
     * @param b a leny aki hasznalja a teleportot
     */
    public void moveTo(Being b){
        Asteroid goalAst = myPair.getPairedAsteroid();
        if(goalAst != null)
            goalAst.moveTo(b);
    }

    /**
     * visszaadja az Asteroidot ahol van
     * @return az   model.Asteroid
     */
    public Asteroid getPairedAsteroid(){
        return myAst;
    }

    /**
     * beallitja a telportParjat
     * @param tp a parja
     * @param tp a parja
     */
    public void setPair(TeleportGate tp){
        myPair = tp;
    }

    /**
     * visszaadja azt a teleportkaput amivel ossze van kotve.
     * @return a parja
     */
    public TeleportGate getPair(){
        return myPair;
    }

    /**
     * A teleportkapu megsemmisül es a parjat is megsemmisiti
     */
    public void destroyTP(){
        myAst.removeTeleportGate(this);
        Asteroid myPairsAsteroid = myPair.getPairedAsteroid();
        if(myPairsAsteroid == null) {
            myPair.getCreatorSettler().removeFirstTeleportGate();
        }
        else{
            myPair.getPairedAsteroid().removeTeleportGate(myPair);
            Game.getGame().removeSteppable(myPair);
        }
        Game.getGame().removeSteppable(this);


    }

    /**
     * lep a kapu. Ha megkergült es veletlen elmozdulasra eljott az ido, elmozdul
     */
    public void step(){
        if(isCrazy){
            timeTillNextMove--;
            if(timeTillNextMove == 0) {
                move();
                timeTillNextMove = 1+ new Random().nextInt(10);
            }
        }
    }

    /**
     * lekezeli a megkergült elmozdulast. Veletlen szomszedra megy.
     */
    private void move(){
            Asteroid myNextAsteroid = myAst.getRandomNeighbourAsteroid();
            myAst.removeTeleportGate(this);
            myAst = myNextAsteroid;
            myNextAsteroid.addTeleportGate(this);
    }


    @Override
    public String toString(){
        String crazy = isCrazy ? "true" : "false";
        return String.format("tpg%d[%s, %s]",id,myPair.getId(),crazy);
    }


}
