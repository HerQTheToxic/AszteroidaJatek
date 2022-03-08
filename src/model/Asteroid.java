package model;

import exceptions.NotEnoughSpaceException;
import vectors.Vec2;
import view.AsteroidDraw;
import view.Drawable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.*;

/**
 * Egy mezot jelkepez, ahova munkasok lephetnek
 */
public class Asteroid implements Neighbour, Steppable, Serializable {

    /** observer a kirajzolashoz */
    private transient AsteroidDraw myDraw;

    /** tavolsag a szomszedok kozott */
    public static final int MAX_NEIGHBOUR_DISTANCE = 50;

    /**  random objektum */
    Random rand = new Random();

    /** helyzet */
    private double pos;

    /** maganyag */
    private Material core;

    /** keregvastagsag */
    private int crustThickness;

    /** tavolsag a naptol */
    private double distance;

    /** melyik ellipticalorbiton van az aszteroida */
    private EllipticalOrbit myOrbit;

    /** osztaly prioritasa */
    private static int priority = 4;

    /** asteroidok id-je */
    public static int ID = 0;

    /** asteroid sorszama */
    private int id ;

    /** elozo korben volt e sunstorm az aszteroidan */
    private boolean wasHitInLastSunStorm = false;

    /** rajta levo beingek */
    private ArrayList<Being> beings = new ArrayList<>();

    /** aszteroida szomszedai */
    private ArrayList<Neighbour> neighbours = new ArrayList<>();

    /** rajta levo teleportgatek */
    private ArrayList<TeleportGate> teleportGates = new ArrayList<>();


    /**
     *Konstruktor
     * @param eo az elo amire spwanol
     * @param mat a material ami az anyaga. null ha veletlen legyen
     * @throws NotEnoughSpaceException dobja ha nincs eleg hely az elon amire menne
     */
    public Asteroid(EllipticalOrbit eo, Material mat) throws NotEnoughSpaceException {
        id = ++ID;
        myOrbit = eo;
        distance = sqrt(pow(eo.getRadius()* EllipticalOrbit.getEllipseWidthToHeightRatio() * cos(pos), 2) + pow(eo.getRadius() * sin(pos), 2));

        int eloN = (int)myOrbit.getRadius()/(EllipticalOrbit.getOrbitSpacing());


        setCrustThickness(rand.nextInt(5));

        Material rndmat = getRandomMat();
        setCore(mat == null ? rndmat : mat);

        if(mat != null)
            mat.setAsteroid(this);


        pos = eo.getNextPlace();


        myDraw = new AsteroidDraw(this);


        eo.AsteroidAdd(this);
        Game.getGame().addSteppable(this);
    }


    /**
     * napkozelseg vizsgalatata
     * @return
     */
    public boolean isTooClose(){
        return distance <= Sun.MIN_SUN_DISTANCE;
    }

    /**
     * sorsol egy veletlen magot
     * @return
     */
    private Material getRandomMat(){

        double chanceUran = 0.25;
        double chanceCoal = 0.5;
        double chanceIron = 0.75;
        double chanceIce = 1;
        double chanceEmpty = 0.8;

        int x = rand.nextInt(5);

        if(x == 0){
            return new Uran(this);
        }
        else if(x == 1){
            return new Coal(this);
        }
        else if(x == 2){
            return new Iron(this);
        }
        else if(x == 3){
            return new Ice(this);
        }
        else {//(x> chanceIce)
            return null;
        }



    }

    /**
     * visszaadja asz aszteroida azonositojat
     * @return - id
     */
    public String getId() { return "ast"+id; }

    /**
     * visszaadja a fazisszoget
     * @return - fazisszog
     */
    public double getPhase() { return pos; }

    /**
     *visszaadja a poziciot
     * @return - pozicio
     */
    public Vec2 getPosition(){
        double ratio = EllipticalOrbit.getEllipseWidthToHeightRatio();

        double x = Math.cos(pos*Math.PI/180)*myOrbit.getRadius()*ratio;
        double y = Math.sin(pos*Math.PI/180)*myOrbit.getRadius();
        return new Vec2(x,y);
    }

    /**
     * beallitja a poziciot
     * @param p - pozicio
     */
    public void setPos(double p){pos = p;}

    /** Mozgatja az aszteroidat */
    public void increaseAngle(double incr) { pos += incr; }

    /** Mag getter es setter */
    public Material getCore() { return core; }
    public void setCore(Material m) { core = m; }

    /**
     * kiüriti az aszteroidat
     * @return - material
     */
    public Material emptiesAsteroid() {
        Material temp = core;
        core = null;
        return temp;
    }

    /** Keregvastagsag setter es getter */
    public int getCrustThickness() { return crustThickness; }

    /**
     * beallitja a vastagsagot
     * @param t - vastagsag
     */
    public void setCrustThickness(int t) {
        if(t >= 0){
            crustThickness = t;
        }
        else{
            System.out.println("0, vagy annal nagyobb szamot adj meg!");
        }
    }

    /** Csokkenti a aszteroida kerget egy egyseggel */
    public void reduceCrust() { if(crustThickness > 0) crustThickness--; }

    /** Naptavolsag getter es setter */
    public double getDistance() { return distance; }
    public void setDistance(double dist) { distance = dist; }

    /** Keringesi palya getter es setter */
    public void setOrbit( EllipticalOrbit o){ myOrbit = o; }
    public EllipticalOrbit getOrtbit() { return myOrbit; }

    /** Prioritas getter es setter */
    public int getPriority(){ return priority; }

    /**
     * visszaadja az observert
     * @return - observer
     */
    @Override
    public Drawable getDrawable() {
        if(myDraw == null)
            myDraw = new AsteroidDraw(this);
        return myDraw;
    }


    /** eloleny lista getter, hozzaado es elvevo fgv */
    public ArrayList<Being> getBeings(){ return beings; }
    public void addBeing(Being b){ beings.add(b); }
    public void removeBeing(Being b){ beings.remove(b); }

    /** Szomszed lista getter es setter */
    public ArrayList<Neighbour> getNeighbours(){ return neighbours; }
    public void setNeighbours(ArrayList<Neighbour> n){ neighbours = n; }
    public Neighbour getRandomNeighbour(){
        return neighbours.get(rand.nextInt(neighbours.size()));
    }

    /**
     * visszaad egy random szomszed aszteroidat
     * @return - szteroida
     */
    public Asteroid getRandomNeighbourAsteroid(){
        ArrayList<Neighbour> all = new ArrayList<>(neighbours);
        all.removeAll(teleportGates);
        return (Asteroid)all.get(rand.nextInt(all.size()));

    }

    /** Teleport kapu lista getter, hozzaado es elvevo fgv */
    public ArrayList<TeleportGate> getTpGates() { return teleportGates; }
    public void addTeleportGate(TeleportGate tp){
        teleportGates.add(tp);
        neighbours.add(tp);
    }

    /**
     * elvesz megarol egy teleportgatet
     * @param tp a teleporgate
     */
    public void removeTeleportGate(TeleportGate tp){
        teleportGates.remove(tp);
        neighbours.remove(tp);
    }

    /** Aszteroida leptetes */
    public void step() {
        checkWin();
        if(crustThickness == 0 && distance < Sun.MIN_SUN_DISTANCE && core!=null)
            core.exposure();
    }

    /** Aszteroidara lepes */
    public void moveTo(Being b){
        if(b.getAsteroid() != null)
            b.getAsteroid().removeBeing(b);
        addBeing(b);
        b.setMyAst(this);
    }

    /** Napvihar */

    public boolean wasHitInLastSunStorm(){
        return wasHitInLastSunStorm;
    }


    /**
     * elerte a napvihar az ovet. megnezi hogy benne van e a viharban, ha igen cselekedik aszerint
     * @param begin napvihar szog kezdete
     * @param angle szog vege
     */
    public void sunstormComing(double begin, double angle){
        /**
         * anglediff = (facingAngle - angleOfTarget + 180 + 360) % 360 - 180
         *
         * if (anglediff <= 45 && anglediff>=-45)
         */

        double angleDiff = (begin+angle/2-pos+180+360)%360-180;


        if (angleDiff <= angle/2 && angleDiff >= -angle/2) {
            ArrayList<Being> deathList = new ArrayList<>();
            if(!(crustThickness == 0 && core == null)){
                deathList.addAll(beings);
            }
            for(Being b : deathList) {
                b.die();
            }
            for(TeleportGate tpg  : teleportGates){
                tpg.goCrazy();
            }
            wasHitInLastSunStorm = true;
        }
        else{
            wasHitInLastSunStorm = false;
        }
    }

    /**
     * az asteroid felrobban es ezt kozli a rajta levo dolgokkal
     */
    public void asteroidKaboom(){
        ArrayList<Being> deathList = new ArrayList<>(beings);
        for(Being b : deathList) {
            b.kaboom();
        }

        controls.Controller.getController().getGameScene().getDrawables().remove(myDraw);
        Game.getGame().removeSteppable(this);
        myOrbit.AsteroidRemove(this);
        ArrayList<TeleportGate> tmp = new ArrayList<>(teleportGates);

        for(TeleportGate tpg : tmp){
            tpg.destroyTP();
        }

        for(Neighbour n : neighbours){
            Asteroid a = (Asteroid) n;
            a.neighbours.remove(this);
        }


    }

    /** Nyeresi feltetel ellenorzes */
    public void checkWin(){
        ArrayList<Material> allMaterials = new ArrayList<>();
        for(Being b : beings){
            b.getMaterials();
            allMaterials.addAll(b.getMaterials());
        }
        if(AllBills.hasEnough(AllBills.getWinBill(), allMaterials))
            Game.getGame().gameOver(true);
    }

    /**
     * beallitja az observeret
     * @param newMyDraw
     */
    public void setMyDraw(AsteroidDraw newMyDraw){
        myDraw=newMyDraw;
    }

    /**
     * visszaadja az observeret
     * @param newMyDraw
     * @return
     */
    public AsteroidDraw getMyDraw(AsteroidDraw newMyDraw){
        return myDraw;
    }

    /** Proto teszthez szükseges adat listazo fgv. */
    @Override
    public String toString(){
        String coreID = core == null? "emp" : core.toString();

        String myInfo =  String.format("ast%d[%d, %s, %d]",id,crustThickness,coreID,Math.round(pos));
        StringBuilder sb = new StringBuilder(myInfo);
        for(TeleportGate tpg : teleportGates){
            sb.append("\n        ").append(tpg.toString());
        }
        for(Being b : beings){
            sb.append("\n        ").append(b.toString());
        }
        return sb.toString();
    }
}