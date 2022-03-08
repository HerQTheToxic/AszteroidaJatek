package view;


import controls.Controller;
import model.Alien;
import vectors.Vec2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Az urleny kirajzolasaert felelos osztaly
 */
public class AlienDraw extends Drawable {
    /** A kirajzolando urleny */
    private Alien myAlien;

    /** A fenykep lekepezesenek merteke (256x256-rol 32x32) */
    private final double initialDownScale = 42.0/256;

    /** Konstruktor */
    public AlienDraw(Alien ali){
        try {
            myAlien = ali;
            originalImage = ImageIO.read(new File(System.getProperty("user.dir") + "\\textures\\ufo.png"));
            displayedImage = Camera.getCamera().getZoomedImage(originalImage,initialDownScale);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Beallitja a kirajzolando urlenyt
     * @param myAlien - a kirajzolando urlenyt
     */
    public void setMyAlien(Alien myAlien) {
        this.myAlien = myAlien;
    }

    /**
     * Kirajzolhatja az urlenyt a terkepre
     * @param miniMapPanelG - a minimap panel grafikaja
     */
    @Override
    public void drawToMiniMap(Graphics miniMapPanelG) {
        //ebben a verzióban nem rajzolja
    }

    /**
     * Kirajzolja az urlenyt a palyara
     * @param gamePanelG - a jatek panel grafikaja
     */
    @Override
    public void drawToGamePanel(Graphics gamePanelG) {
        Vec2 transformed = Camera.getCamera().getScreenCoords(myAlien.getAsteroid().getPosition());
        int beingIndex = myAlien.getAsteroid().getBeings().indexOf(myAlien);
        double theta = beingIndex*30;
        double maxRad = AsteroidDraw.getAsteroidImageWidth()/3.0;
        int alienX = (int)(transformed.getX()+Math.cos(theta)*(maxRad*((double)hashCode()/Integer.MAX_VALUE)));
        int alienY = (int)(transformed.getY()+Math.sin(theta)*(maxRad*((double)hashCode()/Integer.MAX_VALUE)));
        gamePanelG.drawImage(displayedImage, alienX - displayedImage.getWidth() / 2, alienY - displayedImage.getHeight() / 2, null);

    }

    /**
     * Kezeli az urlenyre kattintast
     * @param x - a kattintas X koordinataja
     * @param y - a kattintas Y koordinataja
     * @return - mindig false, az urlenyre kattintasnal semmi sem tortenik
     */
    @Override
    public boolean clicked(int x, int y) {
        return false;
    }

    /** Frissiti az urleny megjeleno kepet */
    @Override
    public void updateImage() {
        displayedImage = Camera.getCamera().getZoomedImage(originalImage,initialDownScale);
    }

    /** Inicializalja a terkep kepet */
    @Override
    public void initMiniMapImg() {
        miniMapImage = Controller.getController().getGameScene().getMapPanel().getMiniMapSizedImage(originalImage,initialDownScale);
    }

}
