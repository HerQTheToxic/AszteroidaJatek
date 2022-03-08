package view;

import controls.Controller;
import model.Robot;
import vectors.Vec2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * A robotok kirajzolasaert felelos osztaly
 */
public class RobotDraw extends Drawable {
    /** A kirajzolando robot */
    private model.Robot myRobot;

    /** A fenykep lekepezesenek merteke (256x256-rol 32x32) */
    private final double initialDownScale = 42.0/256;

    /** Konstruktor */
    public RobotDraw(model.Robot robot) {
        try {
            myRobot = robot;
            originalImage = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\robot.png"));
            displayedImage = Camera.getCamera().getZoomedImage(originalImage, initialDownScale);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Beallitja a kirajzolando robotot
     * @param myRobot - a kirajzolando robot
     */
    public void setMyRobot(Robot myRobot) { this.myRobot = myRobot; }


    /**
     * Kirajzolhatja a robotot a terkepre
     * @param miniMapPanelG - a map panel grafikaja
     */
    @Override
    public void drawToMiniMap(Graphics miniMapPanelG) {
        //nem rajzolunkmost robotot a minitérképre
    }

    /**
     * Kirajzolja a robotot a palyara
     * @param gamePanelG - a jatek panel grafikaja
     */
    @Override
    public void drawToGamePanel(Graphics gamePanelG) {

        Vec2 transformed = Camera.getCamera().getScreenCoords(myRobot.getAsteroid().getPosition());
        int beingIndex = myRobot.getAsteroid().getBeings().indexOf(myRobot);
        double theta = beingIndex*30;

        double maxRad = AsteroidDraw.getAsteroidImageWidth()/3.0;

        int robotX = (int)(transformed.getX()+Math.cos(theta)*(maxRad*((double)hashCode()/Integer.MAX_VALUE)));
        int robotY = (int)(transformed.getY()+Math.sin(theta)*(maxRad*((double)hashCode()/Integer.MAX_VALUE)));
        gamePanelG.drawImage(displayedImage, robotX - displayedImage.getWidth() / 2, robotY - displayedImage.getHeight() / 2, null);
    }


    /**
     * Kezeli a robotra kattintast
     * @param x - a kattintas X koordinataja
     * @param y - a kattintas Y koordinataja
     * @return - mindig false, a robotra kattintasnal semmi sem tortenik
     */
    @Override
    public boolean clicked(int x, int y) {
        return false;
    }

    /** Frissiti a robot megjeleno kepet */
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
