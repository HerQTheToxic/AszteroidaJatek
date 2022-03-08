package model;

import controls.Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Ez az osztaly felel a jatek egeszenek kezeleseert
 */
public class Game implements Serializable {
    /**
     * Singleton mükedes megvalositasa
     */
    private static Game game = new Game();
    /**
     * tarolva van a jatek, a sun, a pontszam, aszteroidaov, steppable-ok listaja, settlerek listaja, es hogy nyerte-e vaggy vesztettek-e a jatekosok
     */
    private Sun sun;
    private int score = 0;
    private AsteroidBelt myOnlyBelt;
    private ArrayList<Steppable> steppables ;
    private int livingSettlers = -1;
    private ArrayList<Settler> settlers = new ArrayList<Settler>();
    private boolean win=false;

    /**
     *setterek, getterek
     * @param over
     */

    public void setWin(boolean over){
        win=over;
    }
    public boolean getWin(){
        return win;
    }

    private Game(){}
    public static Game getGame(){
        return game;
    }

    public void setAsteroidBelt(AsteroidBelt ab){
        myOnlyBelt = ab;
    }

    /**
     * Visszaadja a jatekban tarolt aszteroida ovet
     * @return - az aszteroida ov
     */
    public AsteroidBelt getAsteroidBelt(){
        return myOnlyBelt;
    }

    /**
     * hozzad egy steppable-t a tarolt listahoz
     * @param s
     */

    public void addSteppable(Steppable s){
        steppables.add(s);
    }

    /**
     * setter
     * @param g
     */

    public static void setGame(Game g){
        game = g;
    }

    /**
     * meghalt egy settler ha vege szol a controllernek
     * @param s aki meghalt
     */
    public void settlerDied(Settler s){
        livingSettlers--;
        Controller.getController().settlerDied(s);
        if(livingSettlers <= 0){
            gameOver(false);
        }
    }


    /**
     * getterek
     * @return
     */
    public ArrayList<Steppable> getSteppables(){
        return steppables;
    }

    public ArrayList<Settler> getSettlers(){
        return settlers;
    }

    /**
     * hozzaad egy settlert a listahoz
     * @param s
     */


    public void addSettler(Settler s){
        settlers.add(s);
    }

    public int getLivingSettlers(){
        return livingSettlers;
    }

    /**
     * A jatek veget kezelo függveny
     * @param settlersWon - igaz = gyoztek a jatekosok, hamis = veszitettek
     */
    public void gameOver(boolean settlersWon){
        if(settlersWon){
            win=true;
        }
        Controller.getController().getGameScene().endGame();
    }




    /**
     * telepesek (indulo) szama
     * @param n settlerek uj szama
     */
    public void setSettlerCount(int n){
        livingSettlers = n;
    }

    public  void addSettler(){
        livingSettlers++;
    }
    public int getScore(){return score;}

    /**
     *
     * @return a nap objektum a vilagban
     */
    public Sun getSun(){
        return sun;
    }

    /**
     * leptetei az osszes steppablet a jatekban, egy kort szimbolizal
     */
    public void step(){


        sortSteppablesByPriority();

        ArrayList<Steppable> tmp = new ArrayList<>(steppables);

        for(Steppable s : tmp){
            s.step();
        }

        score++;
    }

    /**
     * kivesz egy steppable-t a tarolt listabol
     * @param s
     */
    public void removeSteppable(Steppable s){
        steppables.remove(s);
        Controller.getController().getGameScene().removeDrawable(s.getDrawable());
    }

    /**
     * priority szerint rendetzi a listat,
     */
    public void sortSteppablesByPriority(){
        Collections.sort(steppables, new Comparator<Steppable>(){
            @Override
            public int compare(Steppable t1, Steppable t2){
                return t1.getPriority() - t2.getPriority();
            }
        });
    }

    /**
     * reseteli a vilagot mintha most kezdodne a jatek
     */
    public void reset(){
        Asteroid.ID = 0;
        TeleportGate.ID = 0;
        Alien.ID = 0;
        EllipticalOrbit.ID = 0;
        Robot.ID = 0;
        Settler.ID = 0;
        Ice.ID = 0;
        Iron.ID = 0;
        Uran.ID = 0;
        Coal.ID = 0;

        steppables = new ArrayList<>();

        sun = new Sun();
        myOnlyBelt = new AsteroidBelt();

        livingSettlers = 0;
        score = 0;



    }


}
