package model;


import exceptions.NotEnoughSpaceException;
import view.Drawable;
import vectors.Vec2;
import view.EllipticalOrbitDraw;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Azonos napkorüli palyan keringo aszteroidakat nyilvantarto osztaly
 */
public class EllipticalOrbit implements Steppable, Serializable {
    /**
     * Asteroidakat tarolja
     */
    private ArrayList<Asteroid> asteroids = new ArrayList<>();

    /** Observer objektum */
    transient private EllipticalOrbitDraw myDraw = null;

    /** Prioritas */
    private static int priority = 3;

    /** masik palyatol valo tavolsag */
    private static int ORBIT_SPACING = 120;

    /** Pozicio */
    private Vec2 position = new Vec2(0,0);

    /** Maximalis aszteroidak */
    private int maxAsteroids ;

    /** Visszaadja a masik palyatol valo tavolsagat */
    public static int getOrbitSpacing() {
        return ORBIT_SPACING;
    }

    /**
     * ellipszis sugara es a keringesi sebessege
     */
    private double radius;
    private double vElo = 2;

    /** Azonosito */
    public static int ID = 0;

    /**
     * Tele van-e az ov?
     * @return - Visszaadja, hogy van-e meg hely az ovon
     */
    public boolean isFull(){
        return maxAsteroids == asteroids.size();
    }

    /** Az ellipszis aranyai */
    private static final double ellipseWidthToHeightRatio = 1.4;

    /** Visszaadja az elipszis aranyait */
    public static double getEllipseWidthToHeightRatio() {
        return ellipseWidthToHeightRatio;
    }

    /** Visszaadja a poziciot */
    public Vec2 getPosition(){
        return position;
    }

    /** Azonosito */
    private int id;

    /** Visszaadja az azonositot */
    public String getId() {
        return "elo"+id;
    }

    /** Az aszteroidak kozotti tavolsag szogben */
    private double step;


    /**
     * sorsol eg ID-t maganak, inicializalja a drawjat es a stepjet az ovon belül
     * ami a az Asteroidok tavolsaga
     */
    public EllipticalOrbit(){
        id = ++ID;
        radius = (id)*ORBIT_SPACING;
        Game.getGame().getAsteroidBelt().addOrbit(this);
        Game.getGame().addSteppable(this);
        myDraw = new EllipticalOrbitDraw(this);
        step = 72.0/id;

        maxAsteroids = (int)(360/step);

    }

    /**
     * visszaadja az ovon levo kovetkezo helyed egy asteroidanak egy szog formajaban
     * exceptiont dob, ha nincs tobb hely
     * @return a szog
     * @throws NotEnoughSpaceException nincs eleg hely az ovon
     */
    public int getNextPlace() throws NotEnoughSpaceException {
        if(maxAsteroids == asteroids.size()){
            throw new NotEnoughSpaceException("TELE AZ oV",null);
        }

        return (int) (asteroids.size()*(step+1));

    }

    /** Visszaadja a prioritast */
    public int getPriority(){
        return priority;
    }


    @Override
    public Drawable getDrawable() {
        if(myDraw == null)
            myDraw = new EllipticalOrbitDraw(this);
        return myDraw;
    }

    /**
     * Meghivja a listaban az osszes aszteroidara a napvihart
     * @param begin napvihar kezdete
     * @param angle napvihar szelese
     */
    public void sunstormComing(double begin, double angle){
        for (Asteroid asteroid : asteroids) {
            asteroid.sunstormComing(begin, angle);
        }
    }

    /**
     * az aszteroidait mozgatja a   model.Sun korül a velo parameterrel
     */
    public void step(){
        for (Asteroid asteroid : asteroids) {
            asteroid.increaseAngle(vElo);
        }
    }

    /**
     * hozzaadja a parameterkent kapott aszteroidat a listahoz
     * @param ast az oj ast
     */
    public void AsteroidAdd(Asteroid ast){
        asteroids.add(ast);
        ast.setOrbit(this);
    }

    /**
     * eltavolit egy asteroidot a listjajabol
     * @param ast
     */
    void AsteroidRemove(Asteroid ast){
        for(int i=0;i< asteroids.size();i++){
            if(ast.getId().equals(asteroids.get(i).getId())){
                asteroids.remove(i);
                return;
            }
        }
    }

    /** Beallitja a sugarat */
    void setRadius(double rad){
        radius=rad;
    }

    /** Visszaadja a sugarat */
    public double getRadius(){
        return radius;
    }

    /** Beallitja a sebesseget */
    void setVelo(Double vel){
        vElo=vel;
    }

    /** Visszaadja a sebesseget */
    double getVelo(){
        return vElo;
    }

    /** Visszaadja az aszteroida listat */
    public ArrayList<Asteroid> getAsteroids(){
        return asteroids;
    }

    /** String-be foglalja az Elo. adatait */
    @Override
    public String toString(){

        StringBuilder sb = new StringBuilder("elo"+id);
        for(Asteroid ast: asteroids){
            sb.append("\n    ").append(ast.toString());
        }
        return sb.toString();
    }

}
