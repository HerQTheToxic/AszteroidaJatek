package view;

import controls.Controller;
import model.Settler;
import vectors.Vec2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * A telepesek kirajzolasaert felelos osztaly
 */
public class SettlerDraw extends Drawable {
    /** A kirajzolando telepes */
    private Settler mySettler;

    /** A fenykep lekepezesenek merteke (256x256-rol 32x32) */
    private final double initialDownScale = 42.0/256;

    /** Konstruktor */
    public SettlerDraw(Settler settler) {
        try {
            mySettler = settler;

            originalImage = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\settler_"+Math.min(4,mySettler.getNo())+".png"));
            displayedImage = Camera.getCamera().getZoomedImage(originalImage, initialDownScale);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }


    /**
     * Kirajzolja a robotot a terkepre
     * @param miniMapPanelG - a map panel grafikaja
     */
    @Override
    public void drawToMiniMap(Graphics miniMapPanelG) {
        int imgW = AsteroidDraw.getMiniMapImageSize();
        if(imgW != -1) {
            Vec2 transformed = Controller.getController().getGameScene().getMapPanel().modelToMiniMap(mySettler.getAsteroid().getPosition());
            int beingIndex = mySettler.getAsteroid().getBeings().indexOf(mySettler);
            double maxRad = imgW/3.0;
            double theta = beingIndex*30;
            int settlerX = (int) (transformed.getX() + Math.cos(theta) * (maxRad * ((double) hashCode() / Integer.MAX_VALUE)));
            int settlerY = (int) (transformed.getY() + Math.sin(theta) * (maxRad * ((double) hashCode() / Integer.MAX_VALUE)));
            miniMapPanelG.drawImage(miniMapImage, (int) settlerX - miniMapImage.getWidth() / 2, (int) settlerY - miniMapImage.getHeight() / 2, null);
        }
    }

    /**
     * Kirajzolja a settler a palyara
     * @param gamePanelG - a jatek panel grafikaja
     */
    @Override
    public void drawToGamePanel(Graphics gamePanelG) {

        Vec2 transformed = Camera.getCamera().getScreenCoords(mySettler.getAsteroid().getPosition());
        int beingIndex = mySettler.getAsteroid().getBeings().indexOf(mySettler);
        double theta = beingIndex*30;
        double maxRad = AsteroidDraw.getAsteroidImageWidth()/3.0;

        int settlerX = (int)(transformed.getX()+Math.cos(theta)*(maxRad*((double)hashCode()/Integer.MAX_VALUE)));
        int settlerY = (int)(transformed.getY()+Math.sin(theta)*(maxRad*((double)hashCode()/Integer.MAX_VALUE)));
        gamePanelG.drawImage(displayedImage, settlerX - displayedImage.getWidth() / 2, settlerY - displayedImage.getHeight() / 2, null);
        if(mySettler == Controller.getController().getActualSettler()){
            gamePanelG.setColor(Color.red);
            gamePanelG.drawOval(settlerX-(int)(displayedImage.getWidth()*1.2*0.5),settlerY-(int)(displayedImage.getWidth()*1.2*0.5),(int)(displayedImage.getWidth()*1.2),(int)(displayedImage.getWidth()*1.2));
        }

    }

    /**
     * Kezeli a telepesre kattintast
     * @param x - a kattintas X koordinataja
     * @param y - a kattintas Y koordinataja
     * @return - mindig false, a telepesre kattintasnal semmi sem tortenik
     */
    @Override
    public boolean clicked(int x, int y) {
        return false;
    }

    /** Frissiti a telepes megjeleno kepet */
    @Override
    public void updateImage() {
        displayedImage = Camera.getCamera().getZoomedImage(originalImage, initialDownScale);
    }

    /** Inicializalja a terkep kepet */
    @Override
    public void initMiniMapImg() {
        miniMapImage = Controller.getController().getGameScene().getMapPanel().getMiniMapSizedImage(originalImage,initialDownScale);
    }
}
