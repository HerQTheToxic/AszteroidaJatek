package model;

import vectors.Vec2;
import view.Drawable;
import view.SunDraw;

import java.io.Serializable;

/**
 * A nap, a palya kozeppontja
 */
public class Sun implements Steppable, Serializable {
    private int timer;
    private static int priority = 1;
    /**
     * ot kirajzolo objektum
     */
    private transient SunDraw myDraw = null;

    /**
     * naptavolsag
     */

    public static final int MIN_SUN_DISTANCE = 333;
    /**
     * helyzete
     */

    private Vec2 position = new Vec2(0,0);

    /**
     * konstruktor
     */
    public Sun(){
        myDraw = new SunDraw(this);
        Game.getGame().addSteppable(this);
        timer = getRandomNumber(5,10);
    }

    /**
     * random szam generalasahoz kell csak a step függvenynel
     * @param min minimumertek
     * @param max maximumertek
     * @return random number
     */
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    /**
     * settter, getter
     * @return
     */
    public int getPriority(){
        return priority;
    }

    public Vec2 getPosition() {
        return position;
    }
    @Override
    public Drawable getDrawable() {
        if(myDraw == null)
            myDraw = new SunDraw(this);
        return myDraw;

    }

    /**
     * lepes hatasara ellenorzi, hogy itt van-e a napvihar, ha igen, akkor meghivja az aszteroida ovre a napvihart es utana kisorsolja, hogy mikor lesz megint vihar
     */
    private boolean sunStormIsOn = false; /**ebben a korben van e napvihar*/
    public void step(){
        timer--;
        if(timer == 0){
            int begin = getRandomNumber(0, 180);
            int angle = getRandomNumber(30, 180);
            sunStormIsOn = true;
            Game.getGame().getAsteroidBelt().sunStormComing(begin,angle);
            return;
        }
        if(timer < 0){
            timer = getRandomNumber(3,10);
            sunStormIsOn = false;
        }

    }

    /**
     * meghivja a sunStrom-t
     * @return
     */

    public boolean sunStormIsON(){
        return sunStormIsOn;
    }
    /**
     * setter, getter
     * @return
     */

    public int getTimer(){
        return timer;
    }
    public void setTimer(int time){
        timer = time;
    }
}
