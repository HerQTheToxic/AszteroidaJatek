package view;

import controls.Controller;
import model.Settler;
import model.TeleportGate;
import vectors.Vec2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * A teleportkapuk kirajzolasaert felelos osztaly
 */
public class TeleportGateDraw extends Drawable {
    /** A kirajzolando teleportkapu */
    private final TeleportGate myTPGate;

    /** A teleportkapu lehetseges allapotaihoz tartozo kepek */
    transient private static BufferedImage normalImage;
    transient private static BufferedImage crazyImage;

    /** A telepoprtkapu pozicioja a kepernyon */
    private Vec2 myPositionOnScreen = null;

    private boolean knownCraziness = false; /**ha a tpg megmarhul akkor feljegyzem , hogy ne kelljen mindig ujra resizeolni a kepet */

    /** A fenykep lekepezesenek merteke */
    private final double initialDownScale = 80.0/512;

    static {
        try {
            normalImage = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\tpgate_normal_new.png"));
            crazyImage = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\tpgate_crazy_new.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Konstruktor */
    public TeleportGateDraw(TeleportGate tpg) {
        myTPGate = tpg;
        displayedImage = Camera.getCamera().getZoomedImage(normalImage,initialDownScale);
    }

    /**
     * Kirajzolhatja a teleportkaput a terkepre
     * @param miniMapPanelG - a minimap panel grafikaja
     */
    @Override
    public void drawToMiniMap(Graphics miniMapPanelG) {
        //ebben a verzióban nem rajzolja ki a minimapre
    }

    /**
     * Kirajzolja a teleportkaput a palyara
     * @param gamePanelG - a jatek panel grafikaja
     */
    @Override
    public void drawToGamePanel(Graphics gamePanelG) {
        if( ! knownCraziness && myTPGate.isCrazy()) {
            updateImage();
            knownCraziness = true;
        }
        Vec2 transformed = Camera.getCamera().getScreenCoords(myTPGate.getMyAst().getPosition());
        double asteroidR = Math.sqrt(AsteroidDraw.getAsteroidImageWidth())/2;
        int nTpG = myTPGate.getMyAst().getTpGates().size();
        int no = myTPGate.getMyAst().getTpGates().indexOf(myTPGate);
        double step = 360.0/nTpG;
        double theta = step*no;
        int xOffset = (int)(Math.cos(theta)*asteroidR);
        int yOffset = (int)(Math.sin(theta)*asteroidR);
        myPositionOnScreen = new Vec2((int)transformed.getX()+xOffset,(int)transformed.getY()+yOffset);
        gamePanelG.drawImage(displayedImage,(int)transformed.getX()+xOffset,(int)transformed.getY()+yOffset,null);
    }

    /**
     * Kezeli a teleportkapura kattintast
     * @param x - a kattintas X koordinataja
     * @param y - a kattintas Y koordinataja
     * @return - true, ha a kattintas ra tortent
     */
    public boolean clicked(int x, int y){
        if(myPositionOnScreen == null){
            return false;
        }
        Settler actSettler = Controller.getController().getActualSettler();
        boolean clicked = x > myPositionOnScreen.getX() && x < myPositionOnScreen.getX() + displayedImage.getWidth()
                && y < myPositionOnScreen.getY() + displayedImage.getHeight() && y > myPositionOnScreen.getY();

        if(clicked && myTPGate.getMyAst().getBeings().contains(actSettler)){
            actSettler.move(myTPGate);
        }
        return clicked;
    }

    /** Frissiti a teleportkapu megjeleno kepet */
    @Override
    public void updateImage() {
        if (myTPGate.isCrazy()) {
            displayedImage = Camera.getCamera().getZoomedImage(crazyImage, initialDownScale);
        } else {
            displayedImage = Camera.getCamera().getZoomedImage(normalImage, initialDownScale);
        }
    }

    /** Inicializalja a terkep kepet */
    @Override
    public void initMiniMapImg() {}
}
