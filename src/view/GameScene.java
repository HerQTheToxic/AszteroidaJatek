package view;

import controls.Controller;
import controls.TimerReminder;
import model.Game;
import model.Settler;
import model.Steppable;
import vectors.Vec2;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;

/**
 * A jatek megvalositasaert felelos osztaly. Egy ilyen osztaly egy jatek "session"
 * Ebbol ki lehet lepni, lehet menteni,  lehet feladni a jatekot.
 * Ha vesztettek/ nyertek a telepesek akkor visszavisz a fomenübe
 */

public class GameScene implements MouseListener, MouseWheelListener, KeyListener , MouseMotionListener {
    private final ArrayList<Drawable> drawables = new ArrayList<Drawable>(); /** osszes  kirajzolando objektum */
    private final JPanel gamePanel; /** a panel amiore rajzolodik maga a jatek */
    private final MapPanel mapPanel; /** minmimap bal alul */
    private final EquipmentPanel equipmentPanel; /** jobb oldalt kijelzo settler info */
    private final EscapeMenuPanel escapeMenuPanel;/** escape billentyu / gomb hatasara elojovo panel */
    private final JLayeredPane layeredPane; /** osszes panelt tarolo layered pane */
    private final StatusBarPanel statusBarPanel;/** bal felül kor es jatek statusza */
    private EndGamePanel endGamePanel;/** jatek veget kijelzo panel*/
    private boolean dragging = false;/** paneleshez szükseges, ha eppen van mousedrag */
    private int elapsedTime = 0;/** osszes eltelt ido*/
    private int roundTime = 0;/** mostani korben eltelt ido */
    private Thread drawThread;/** rajzolasert felelos szal */
    private final int FPS = 120;/** mostani fps*/

    private static Timer timer = null;/**idozito a korre*/


    private Vec2 pressedCoord;/** eger lenyomasa drag elejen */
    private Vec2 endOfPrevDrag = null;/** drag vege* /


    /**
     * a mostani jatek konstruktora. beallitja a paneleket a helyükre, elkeri a modellben levo objektumoktol
     * a drawable observerüket, es hozzaadja magahoz egy listaba
     * elindit egy rajzolo threadet ami kirajzoplja a jatek allasat fix idonkent
     */

    public GameScene(){
        Controller.getController().setGameScene(this);

        /** alap layered pane */
        layeredPane = new JLayeredPane();
        layeredPane.setFocusable(true);
        layeredPane.requestFocusInWindow();
        layeredPane.addKeyListener(this);
        Controller.getController().add(layeredPane);
        Controller.getController().setContentPane(layeredPane);

        /**a game panel*/
        gamePanel = new GamePanel();
        gamePanel.setSize(new Dimension(Controller.getWindowWidth(), Controller.getWindowHeight()));
        gamePanel.setBackground(Color.CYAN);
        gamePanel.setVisible(true);
        gamePanel.addMouseWheelListener(this);
        gamePanel.addMouseListener(this);
        gamePanel.addMouseMotionListener(this);

        /**settler info panel*/
        equipmentPanel = new EquipmentPanel();
        equipmentPanel.setBounds(Controller.getWindowWidth()- Controller.getWindowHeight()/4, Controller.getWindowHeight()/8, Controller.getWindowHeight()/4, Controller.getWindowHeight()*3/4);


        /**minimap*/
        mapPanel = new MapPanel(Controller.getWindowHeight()/4*16/9, Controller.getWindowHeight()/4,0, Controller.getWindowHeight()*3/4);
        mapPanel.setBounds(0, Controller.getWindowHeight()*3/4, Controller.getWindowHeight()/4*16/9, Controller.getWindowHeight()/4);
        mapPanel.setVisible(true);

        /**bal fenti statusz*/
        statusBarPanel = new StatusBarPanel();
        statusBarPanel.setBounds(0,0,(int)(Controller.getWindowWidth()/4.5), Controller.getWindowHeight()/10);
        statusBarPanel.setBackground(Color.YELLOW);

        /**escape menü*/
        escapeMenuPanel = new EscapeMenuPanel();


        /**panelek hozzaadasa*/
        layeredPane.setFocusable(true);
        layeredPane.add(equipmentPanel,2);
        layeredPane.add(mapPanel,2);
        layeredPane.add(gamePanel,3);
        layeredPane.add(escapeMenuPanel,1);
        layeredPane.add(statusBarPanel, 2);


        layeredPane.setVisible(true);
        layeredPane.setSize(new Dimension(300,300));



        /**modell kioolvasasa drawable elkerese*/
        Game g = Game.getGame();

        for(Steppable s : g.getSteppables()) {
            if (s.getDrawable() != null) {
                drawables.add(s.getDrawable());
            }
       }
       for(Settler s : Controller.getController().getSettlers()){
            drawables.add(s.getDrawable());
        }


       /**minimap image elkerese */
        for(Drawable d : drawables){
            d.initMiniMapImg();
        }


        /**ido timer*/
        if(timer == null) {
            timer = new Timer();
            TimerReminder tr = new TimerReminder();
            timer.schedule(tr, 1000, 1000);
        }
        else{
            TimerReminder.reset();
        }


        equipmentPanel.displayEq(Controller.getController().getActualSettler());
        layeredPane.requestFocusInWindow();



        /**thread inditasa*/
        drawThread= new Thread(() -> {

            while(true) {
                long start = System.currentTimeMillis();

                redraw();
                long finish = System.currentTimeMillis();
                try {
                    long t = 1000 / FPS - (finish - start) > 0 ? 1000 / 30 - (finish - start) : 0;
                    Thread.sleep(t);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        drawThread.start();

    }

    /**drawable getter*/
    public ArrayList<Drawable> getDrawables() {
        return drawables;

    }

    /**elapsed time getter*/
    public int getTimeElapsed(){
        return elapsedTime;
    }

    /**statusabar getter*/
    public StatusBarPanel getStatusBarPanel(){
        return statusBarPanel;
    }

    /**gamepanelg getter*/
    public JPanel getGamePanel(){
        return gamePanel;
    }

    /**mappanel getter*/
    public MapPanel getMapPanel() {
        return mapPanel;
    }


    /**settlerinfo/equipment panel getter*/
    public EquipmentPanel getEquipmentPanel(){
        return equipmentPanel;
    }

    /**roundetime setter*/
    public void setRoundTime(int time){
        roundTime = time;
    }

    /**elohivja/ eltüntetni az escape panelt, minden mas panelt eltüntet vagy megjelenit helyette*/
    public void interactWithEscapePanel(){
        if(escapeMenuPanel.isVisible()){
            escapeMenuPanel.setVisible(false);
            gamePanel.setVisible(true);
            mapPanel.setVisible(true);
            equipmentPanel.setVisible(true);
            statusBarPanel.setVisible(true);
            Controller.getController().gameResume();
        }
        else {
            gamePanel.setVisible(false);
            mapPanel.setVisible(false);
            equipmentPanel.setVisible(false);
            statusBarPanel.setVisible(false);
            escapeMenuPanel.setVisible(true);
            Controller.getController().gamePause();
        }
    }


    /**megnezi kire clickeltek*/
    public void clicked(int x, int y){
        SortDrawablesByZCoord();
        for(Drawable d: drawables){
            if(d.clicked(x,y))
                break;
        }


    }

    /**tovabbitja a clicked fgv-nek a az eger kattintast */
    @Override
    public void mouseClicked(MouseEvent e) {
        clicked(e.getX(), e.getY());
    }


    /**letarolja honnan indult ha a mousepressed-et draggel folytatna a user*/
    @Override
    public void mousePressed(MouseEvent e) {
        pressedCoord = new Vec2(e.getX(),e.getY());
        dragging = true;

    }

    /**drawable remover*/
    public void removeDrawable(Drawable d){
        drawables.remove(d);
    }

    /**ha felengedi a user az egeret vege a draggnek*/
    @Override
    public void mouseReleased(MouseEvent e) {
        endOfPrevDrag = null;
        dragging = false;
    }

    /**nem kell*/
    @Override
    public void mouseEntered(MouseEvent e) {
        //nothing
    }

    /**nem kell*/
    @Override
    public void mouseExited(MouseEvent e) {

    }


    /**rendezi a drawables-t hogy jo legyen az egymasra rajzolas*/
    private void SortDrawablesByZCoord(){
        Collections.sort(drawables, new Comparator<Drawable>() {
            @Override
            public int compare(Drawable d1, Drawable d2) {
                return d1.posZ - d2.posZ;
            }
        });
    }

    /**ezt a redrwa fgv-t hivja meg a rajzolo szal a kirajzolasnal. kirajzolja rendezes utan az osszes
     * drawablet a listabol es frissiti a settler infot, valamint a kepernyot*/
     private void redraw(){


        SortDrawablesByZCoord();

        ArrayList<Drawable> tmp = new ArrayList<>(drawables);

        for (Drawable d : tmp) {
            d.updateImage();
        }
        equipmentPanel.displayEq(Controller.getController().getActualSettler());
        layeredPane.repaint();
    }


    /**
     * zoomolas regisztralasahoz kell a függveny. szol a cameranak hogy zoomoltak
     * @param e mouse wheel esemeny, ebben van a roration erteke
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int leptek = -e.getWheelRotation(); //pont forditva van -->minusz

        Camera.getCamera().multiplyZoom(1 + ((double)leptek / 10));
    }

    /**
     * vege a jateknak, megjeleniti az endgame kepernyot a score-ral
     */
    public void endGame(){
        //timer.cancel();
        //timer.purge();
        Controller.getController().gamePause();

        escapeMenuPanel.setVisible(false);
        gamePanel.setVisible(false);
        equipmentPanel.setVisible(false);
        mapPanel.setVisible(false);
        endGamePanel = new EndGamePanel();
        layeredPane.add(endGamePanel);
        layeredPane.moveToFront(endGamePanel);
        endGamePanel.setVisible(true);
    }


    /**
     * mouse dragged event, a camera panhez kell es szol neki, hogy milyen kis
     * vektorral shifteltek a pant
     * @param e eger koordinatak kellenek ebbol a mouseEventbol
     */
    @Override
    public void mouseDragged(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();

        Vec2 translate;
        if(endOfPrevDrag == null) {
            translate = new Vec2(x - pressedCoord.getX(), y - pressedCoord.getY());
        }
        else{
            translate = new Vec2(x-endOfPrevDrag.getX(),y-endOfPrevDrag.getY());
        }
        if(dragging) {

            translate = translate.multiply(-1);
            Camera.getCamera().addPanMovement(translate);
        }

        endOfPrevDrag = new Vec2(x,y);

    }

    /**nem kell*/
    @Override
    public void mouseMoved(MouseEvent e) {
    }


    /**nem kell*/
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**ha a gamepanel focusban van elohuzza az esc billentyuvel az escape
     * panelt. ez akar helyettesitheto a statusbaron levo "esc" gombbal
     * @param e key event amiben a kod van
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            interactWithEscapePanel();
    }

    /**nem kell*/
    @Override
    public void keyReleased(KeyEvent e) {

    }

}
