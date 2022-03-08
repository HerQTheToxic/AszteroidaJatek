package model;

import controls.Controller;
import view.Drawable;
import view.RobotDraw;

import java.io.Serializable;
import java.util.Random;

/**
 * A robotok kezelesert felelos osztaly
 */
public class Robot extends Worker implements Steppable, Serializable {

    /**
     * azonosito
     */
    public static int ID = 0;
    private int id;
    /**
     * leptetesi prioritas
     */
    private static int priority = 6;
    /**
     * hozza tartozo megjelenito
     */
    private transient RobotDraw myDraw = null;

    /**
     * konstruktor
     * @param a
     */

    public Robot(Asteroid a){
        id = ++ID;
        myDraw = new RobotDraw(this);
        myDraw.setMyRobot(this);
        Game.getGame().addSteppable(this);
        Controller.getController().getGameScene().getDrawables().add(myDraw);
        a.moveTo(this);
    }

    /**
     * setterek, getterek
     * @return
     */
    public int getPriority(){
        return priority;
    }

    @Override
    public Drawable getDrawable() {
        if(myDraw == null)
            myDraw = new RobotDraw(this);
        return myDraw;
    }

    public String getId() {
        return "rob"+id;
    }
    /**
     * A robot felrobban. A   model.Worker kaboom() függvenyet ez az osztaly ugy valositja meg, hogy masik aszteroidara repül.
     */
    @Override
    public void kaboom(){
        Asteroid ast = myAst.getRandomNeighbourAsteroid();
        if(ast == null){
            die();
        }
        else
        {
           ast.moveTo(this);
        }
    }

    /**
     * Meghal a robot, egyetlen felelossege a függvenynek hogy levegye ot az aszteroidarol es ertesitse a jatekot errol
     */
    public void die() {
        myAst.removeBeing(this);
        Game.getGame().removeSteppable(this);
    }



    /**
     * Lepes fv. impl.
     */
    public void step() {
        Random rand = new Random();
        if(rand.nextInt(2) == 0){
            if(myAst.getCrustThickness() > 0)
                drill(myAst.getDistance() < Sun.MIN_SUN_DISTANCE);
            else
                move(myAst.getRandomNeighbour());
        }
        else{
            move(myAst.getRandomNeighbour());
        }
    }

    @Override
    public String toString(){
        return "rob"+id;
    }

}
