package model;


import view.Drawable;

import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Math.*;

/**
 * A jatekban letezo napkorüli palyak gyüjtemenye
 */
public class AsteroidBelt implements Steppable, Serializable {
    /** Az aszteroida palyak listaja */
    private ArrayList<EllipticalOrbit> orbits;

    /** Prioritas */
    private static int priority = 2;

    /** Konstruktor */
    public AsteroidBelt() {
        orbits = new ArrayList<>();
        Game.getGame().addSteppable(this);
    }

    /** Palya hozzaadasza */
    public void addOrbit(EllipticalOrbit eo) {
        orbits.add(eo);
    }

    /** Visszaadja a palyakat */
    public ArrayList<EllipticalOrbit> getOrbits() {
        return orbits;
    }

    /** Visszaadja a prioritast */
    public int getPriority(){
        return priority;
    }

    /** Visszaadja az observer objektumat, null-t mert nincs neki */
    @Override
    public Drawable getDrawable() {
        return null;
    }

    /**
     * @param begin a napvihar kezdete fokban
     * @param angle a napvihar szelessege
     */
    public void sunStormComing(int begin, int angle) {
        for (EllipticalOrbit orbit : orbits) {
            orbit.sunstormComing(begin, angle);
        }
    }

    /** Lepteti az osszes rajta levo steppable-t */
    public void step(){
        ArrayList<Asteroid> allAsts = new ArrayList<Asteroid>();

        for(int i = 0; i < orbits.size(); i++) {
            ArrayList<Asteroid> astsOfEO = orbits.get(i).getAsteroids();
            for (Asteroid currAst : astsOfEO) {
                double phase = currAst.getPhase() * PI / 180;

                currAst.setDistance(sqrt(pow(orbits.get(i).getRadius()* EllipticalOrbit.getEllipseWidthToHeightRatio() * cos(phase), 2) + pow(orbits.get(i).getRadius() * sin(phase), 2)));
                allAsts.add(currAst);
            }
        }
        calcNeighbours(allAsts);
    }

    /** Kiszamolja a szoszedsagokat */
    private void calcNeighbours(ArrayList<Asteroid> Asteroids) {
        for (int i = 0; i < Asteroids.size(); i++) {
            ArrayList<Neighbour> newNe = new ArrayList<>();
            Asteroid a = Asteroids.get(i);
            for (int j = 0; j < Asteroids.size(); j++) {
                Asteroid b = Asteroids.get(j);

                if (i != j) {
                    double aPosRad = a.getPhase() * PI / 180;
                    double bPosRad = b.getPhase() * PI / 180;
                    double x1 = cos(aPosRad) * (a.getDistance());
                    double y1 = sin(aPosRad) * (a.getDistance());
                    double x2 = cos(bPosRad) * (b.getDistance());
                    double y2 = sin(bPosRad) * (b.getDistance());
                    double d = sqrt(pow(x1 - x2, 2) + pow(y1 - y2, 2));

                    if (d <= Sun.MIN_SUN_DISTANCE) {
                        newNe.add(b);
                    }
                }
            }

            a.setNeighbours(newNe);
        }
    }

}
