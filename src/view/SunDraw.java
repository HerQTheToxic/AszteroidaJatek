package view;

import controls.Controller;
import model.Sun;
import vectors.Vec2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A nap kirajzolasaert felelos osztaly
 */
public class SunDraw extends Drawable {
    /** A kirajzolando nap */
    private Sun mySun;

    /** A fenykep lekepezesenek merteke (256x256-rol 32x32) */
    private final double initialDownScale = 128.0/256; //256x256 img to 64x64

    /** A minimap koordinatai */
    private Vec2 miniMapCoords;

    /** Kostruktor */
    public SunDraw(Sun sun){
        try {
            mySun = sun;
            originalImage = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\sun.png"));
            displayedImage = new BufferedImage(originalImage.getWidth(null),originalImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics g = displayedImage.createGraphics();
            g.drawImage(originalImage,0,0,null);
            g.dispose();
            displayedImage = Camera.getCamera().getZoomedImage(originalImage,initialDownScale);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Kirajzolja a napot a terkepre
     * @param miniMapPanelG - a map panel grafikaja
     */
    @Override
    public void drawToMiniMap(Graphics miniMapPanelG) {
        miniMapPanelG.drawImage(miniMapImage,(int)miniMapCoords.getX()-miniMapImage.getWidth()/2,(int)miniMapCoords.getY()-miniMapImage.getHeight()/2,null);
    }

    /**
     * Kirajzolja a napot a palyara
     * @param gamePanelG - a jatek panel grafikaja
     */
    @Override
    public void drawToGamePanel(Graphics gamePanelG) {
        Vec2 transformed = Camera.getCamera().getScreenCoords(mySun.getPosition());
        gamePanelG.drawImage(displayedImage,(int)transformed.getX()-displayedImage.getWidth()/2,(int)transformed.getY()-displayedImage.getHeight()/2,null);

        int radius = Sun.MIN_SUN_DISTANCE;

        Vec2 viewCoords = Camera.getCamera().getScreenCoords(mySun.getPosition());
        gamePanelG.setColor(Color.yellow);
        int rectSide = (int)(radius*2* Camera.getCamera().getLambda());

        gamePanelG.drawOval((int)viewCoords.getX()-rectSide/2,(int)viewCoords.getY()-rectSide/2,rectSide,rectSide);
    }

    /**
     * Kezeli a napra kattintast
     * @param x - a kattintas X koordinataja
     * @param y - a kattintas Y koordinataja
     * @return - mindig false, a napra kattintasnal semmi sem tortenik
     */
    @Override
    public boolean clicked(int x, int y) {
        return false;
    }

    /** Frissiti a nap megjeleno kepet */
    @Override
    public void updateImage() {
        displayedImage = Camera.getCamera().getZoomedImage(originalImage,initialDownScale);
    }

    /** Inicializalja a terkep kepet */
    @Override
    public void initMiniMapImg() {
        miniMapImage = Controller.getController().getGameScene().getMapPanel().getMiniMapSizedImage(originalImage,initialDownScale);
        miniMapCoords = Controller.getController().getGameScene().getMapPanel().modelToMiniMap(mySun.getPosition());
    }

}
