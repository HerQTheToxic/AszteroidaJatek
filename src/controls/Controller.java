package controls;


import exceptions.NotEnoughSpaceException;
import exceptions.NotSupportedResulution;
import exceptions.TooManyAsteroidsException;
import model.*;
import view.*;

import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *  Vezerli a jatek grafikus megjeleniteset
 */
public class Controller extends JFrame {

    private static Controller controller;

    /** kepernyo felbontasa*/
    private static int WINDOW_WIDTH = 1920;
    private static int WINDOW_HEIGHT = 1080;

    /** kirajzolo obj */
    GameScene gameScene = null;

    /** korok szama */
    private int round = 0;

    /** aktualis telepes nyilvantartasa*/
    private Settler actualSettler = null;

    /** aktualis telepes szama*/
    private int actualSettlerIndex = 0;

    /** fo panelek a jatekban */
    SettingsPanel settingsPanel = new SettingsPanel();
    HelpPanel helpPanel = new HelpPanel();
    EquipmentPanel eqPanel = new EquipmentPanel();
    MainMenuPanel mainMenuPanel = new MainMenuPanel();
    GamePanel gamePanel = new GamePanel();

    /** telepesek a jatekban */
    ArrayList<Settler> settlers = new ArrayList<>();

    /**
     * osztaly konstruktora, jatek felepul
     */
    public Controller(){

        /** ablak meretezese */
        try {
            Toolkit tk = Toolkit.getDefaultToolkit();
            int xSize = ((int) tk.getScreenSize().getWidth());
            int ySize = ((int) tk.getScreenSize().getHeight());
            WINDOW_HEIGHT = ySize;
            WINDOW_WIDTH = xSize;
            setPreferredSize(new Dimension(xSize,ySize));
            if (ySize > 1080 || xSize > 1920) {
                throw new NotSupportedResulution("Nem 1920x1080-as felbontas",null);
            }
        }
        catch(NotSupportedResulution ex){
            ex.printStackTrace();
            System.err.println(ex.getMessage());
        }

        /** ablak inicializalasa */
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setLayout(null);
        setVisible(true);

        /** panelek lathatosaganak beallitasa */
        helpPanel.setSize(getWindowWidth(),getWindowHeight());
        settingsPanel.setVisible(false);
        helpPanel.setVisible(false);
        eqPanel.setVisible(false);
        gamePanel.setVisible(false);

        /** panelek hozzaadasa */
        add(settingsPanel);
        add(helpPanel);
        add(mainMenuPanel);

        /** letsgo */
        pack();
    }

    /**
     * jatekot inicializalja
     */
    public void initWorld(){

        /** settlerek szama */
        int settlersCnt = settingsPanel.getNSettler();

        if(!settingsPanel.isLoaded()) {
            settlers.clear();
            Game.getGame().reset();

            Game.getGame().reset();
            int elos = settingsPanel.getNElo();

            int asteroids = settingsPanel.getNAsteroid();

            /** elokat letrehozni */
            for (int i = 0; i < elos; i++) {
                /** ellipticcalorbitot hozzaad a jatekhoz */
                EllipticalOrbit eo = new EllipticalOrbit();
            }

            ArrayList<Asteroid> asteroidList = new ArrayList<>();
            int eoCnt = 0;

            ArrayList<EllipticalOrbit> eloList = Game.getGame().getAsteroidBelt().getOrbits();

            /** asteroidokat az elokra */
            for (int i = 0; i < asteroids; i++) {
                try {
                    if(!eloList.get(eoCnt).isFull()) {
                        Asteroid ast = new Asteroid(eloList.get(eoCnt), null);
                        /** aszteroida hozzaad */
                        asteroidList.add(ast);
                    }
                    else if(eloList.get(eoCnt).isFull() && eoCnt == eloList.size()-1){
                        throw new TooManyAsteroidsException("Too Much Asteroids, Cannot Place Them On The EllipticalOrbits",null);
                    }
                    else{
                        i--;
                        eoCnt++;
                    }
                } catch (TooManyAsteroidsException | NotEnoughSpaceException ex) {
                    ex.printStackTrace();
                    System.err.println("Hiba a modell feltoltesenel " + ex.getMessage());
                }
            }

            Random rand = new Random();

            for(int i = 0; i < settlersCnt; i++){
                int whereEO = rand.nextInt(eloList.size());
                EllipticalOrbit eo = Game.getGame().getAsteroidBelt().getOrbits().get(whereEO);

                if(rand.nextInt(4) == 0){
                    Alien ali = new Alien(asteroidList.get(rand.nextInt(asteroidList.size())));
                }

                int whereAst = rand.nextInt(eo.getAsteroids().size());
                Asteroid ast = eo.getAsteroids().get(whereAst);
                /** telepesek letrehozasa */
                Settler set = new Settler(ast,i);

            }
        }
        /** jatek lep */
        Game.getGame().getAsteroidBelt().step();
    }

    /**
     * jatek inditasa, ha uj game = uj gameScene
     */
    public void gameStart(){


        /** fomenu */
        mainMenuPanel.setVisible(false);

        /** jatek parameterezese */
        initWorld();
        actualSettler = settlers.get(0);
        gameScene = new GameScene();

    }

    /**
     * program belepopontja
     * @param args program argumentumok
     */
    public static void main(String[] args){
        controller = new Controller();
    }

    /**
     * a jatek visszater a fomenube
     */
    public void backToMenu(){
        setContentPane(mainMenuPanel);
        mainMenuPanel.setVisible(true);
    }

    /**
     * telepesek lekerese
     * @return - telepesek
     */
    public ArrayList<Settler> getSettlers() {
        return settlers;
    }

    /**
     * rajzolo osztaly lekerese
     * @return - rajzolo osztaly
     */
    public GameScene getGameScene(){
        return controller.gameScene;
    }

    /**
     * rajzolo osztaly beallitasa
     * @param v - rajzolo osztaly
     */
    public void setGameScene(GameScene v){
        gameScene = v;
    }

    /**
     * korok lekerese
     * @return - korok szama
     */
    public int getRound(){
        return round;
    }

    /**
     * uj kor
     */
    public void setRound(){
        Game.getGame().step();
        round++;
        gameScene.getStatusBarPanel().setRound();
    }

    /**
     * settingspanel lekerese
     * @return - settingspanel
     */
    public SettingsPanel getSettingsPanel(){
        return controller.settingsPanel;
    }

    /**
     * aktualis telepes sorszamat adja vissza
     * @return - aktualis telepes sorszama
     */
    public int getActualSettlerIndex() {
        return actualSettlerIndex;
    }

    /**
     * aktualis telepest keri le
     * @return - aktualis telepes
     */
    public Settler getActualSettler() {
        return actualSettler;
    }

    /**
     * aktualis telepest allitja be
     * @param actualSettler - aktualis telepes
     */
    public void setActualSettler(Settler actualSettler) {
        this.actualSettler = actualSettler;
    }

    /**
     * lekeri a coktrollert
     * @return - controller
     */
    public static Controller getController(){
        return controller;
    }

    /**
     * lekeri az ablak magassagat
     * @return - magassag pixelben
     */
    public static int getWindowWidth(){
        return WINDOW_WIDTH;
    }

    /**
     * lekeri az ablak szelesseget
     * @return - szelesseg pixelben
     */
    public static int getWindowHeight(){
        return WINDOW_HEIGHT;
    }

    /**
     * lekeri h hany telepes van a jatekban
     * @return - telepesek szama
     */
    public int getSettlerCount(){
        return settlers.size();
    }

    /**
     * beallitja az aktualis telepes szamat
     */
    public void setActualsettlerIndex() {
        actualSettlerIndex = ++actualSettlerIndex % getSettlerCount();
        if(actualSettlerIndex == 0)
            setRound();

    }

    /**
     * telepes meghalasat kezeli le
     * @param s a settler aki meghalt
     */
    public void settlerDied(Settler s){
        /** kiszedi a telepesek tombbol */
        settlers.remove(s);
        /** ha meghal az utso vege a jateknak */
        if(settlers.size() == 0){
            Game.getGame().setWin(false);
            gameScene.endGame();
        }
        /** rajzolo tombbol is kiszedni */
        gameScene.getDrawables().remove(s.getDrawable());

        /** ha az eppen soron levo telepes halt meg jon a kovi*/
        if(s.equals(actualSettler)){
            goNext();
        }
    }

    /**
     * kovetkezo telepes jon
     */
    public void goNext(){
        /** telepes beallitasa */
        controller.setActualsettlerIndex();
        controller.setActualSettler(settlers.get(getActualSettlerIndex()));
        gameScene.getStatusBarPanel().setSettler();
        /** szamlalo beallitasa */


        controller.getGameScene().setRoundTime(0);
    }

    /**
     * aktualis telepes lekerese
     * @return - aktualis telepes
     */
    public int getSettler(){
        return settlers.indexOf(actualSettler);
    }

    /**
     * folytatodik a kor
     */
    public void gameResume(){
        TimerReminder.resume();
    }

    /**
     * jatek korenek leallitasa
     */
    public void gamePause(){
        TimerReminder.stop();
    }

    /**
     * settings panel megnyitasa
     */
    public void openSettings(){
        setContentPane(settingsPanel);
        helpPanel.setVisible(false);
        mainMenuPanel.setVisible(false);
        settingsPanel.setVisible(true);

    }

    /**
     * help panel beallitasa
     */
    public void openHelp(){
        setContentPane(helpPanel);
        settingsPanel.setVisible(false);
        mainMenuPanel.setVisible(false);
        helpPanel.setVisible(true);
    }

    /**
     * vissza a menube
     */
    public void backToTheMenu(){
        setContentPane(mainMenuPanel);
        gameScene = null;
        helpPanel.setVisible(false);
        settingsPanel.setVisible(false);
        mainMenuPanel.setVisible(true);
    }

    /**
     * telepes hozzaadasa a tombhoz
     * @param s - telepes
     */
    public void addSettler(Settler s){
        controller.settlers.add(s);
    }

}