package model;

import view.AlienDraw;
import view.Drawable;

import java.io.Serializable;

public class Alien extends Being implements Steppable, Serializable {

    /**
     * Alienek ID szamlaloja
     */
    public static int ID = 0;
    /**
     * az alien ID
     */
    private int id;

    /**
     * osztaly prioritasa
     */
    private static int priority = 7; //step priority

    /**
     * kirajzolast kezelo attributum
     */
    private transient AlienDraw myDraw = null;

    /**
     * konstruktor
     * @param a - aszteroida, amelyikre letesszük
     */
    public Alien(Asteroid a){
        id = ++ID;
        myDraw = new AlienDraw(this);

        Game.getGame().addSteppable(this);
        a.moveTo(this);
    }

    /**
     * visszaadja az ufo egyedi kodjat
     * @return
     */
    public String getId(){
        return "ali"+id;
    }

    /**
     * Kezeli, hogy a myAst felrobbanasakor mi tortenik az urlennyel
     */
    public void kaboom(){
        die();
    }

    /**
     * urleny halalat kezeli le
     */
    public void die(){
        myAst.removeBeing(this);
        Game.getGame().removeSteppable(this);
    }

    /**
     *   Lopas.
     */
    public void steal(){
        myAst.setCore(null);
    }

    /**
     * visszaadja az osztaly prioritasat
     * @return - prioritas
     */
    public int getPriority(){
        return priority;
    }

    /**
     * rajzolas attributum kezelese
     * @return observer
     */
    @Override
    public Drawable getDrawable() {
        if(myDraw == null)
            myDraw = new AlienDraw(this);
        return myDraw;
    }

    /**
     * Lepes fv implementalasa
     */
    public void step(){
        if(myAst.getCrustThickness() == 0 && myAst.getCore() != null){
            steal();
        }
        else{
           move(myAst.getRandomNeighbour());
        }
    }

    /**
     * kiirashoz, stringet csinal
     * @return - azonosito
     */
    public String toString(){
        return getId();
    }

}
