package view;

import controls.Controller;
import vectors.Vec2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * terkep osztalya
 */
public class MapPanel extends JPanel {

    /** hatter */
    transient BufferedImage mapBackground;
    /** helyzet es meret */
    static int width;
    static int height;
    static int posX;
    static int posY;

    /**
     * lekezi a szelesseget
     * @return - szelesseg
     */
    public static int getMapPanelWidth() {
        return width;
    }

    /**
     *  lekeri a magassagot
     * @return - magassag
     */
    public static int getMapPanelHeight() {
        return height;
    }

    /**
     * Konstruktor, hatter, panel init a megfelelo helyre
     * @param width_ - szelesseg
     * @param height_ - magassag
     * @param posX_ - kezdo koord_X
     * @param posY_ - kezdo koork_Y
     */
    public MapPanel(int width_, int height_,int posX_,int posY_) {

        try {
            BufferedImage mapBackgroundFullSize = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\background.png"));
            Image tmp = mapBackgroundFullSize.getScaledInstance(width_,height_, Image.SCALE_SMOOTH);

            BufferedImage scaledImg = new BufferedImage(width_,height_, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = scaledImg.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            mapBackground = scaledImg;

            posX = posX_;
            posY = posY_;
            width = width_;
            height = height_;

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * visszaadja a minimapre tevodo imaget
     * @param img - kep
     * @param initialDownScale - eredeti scale
     * @return - kep
     */
    public BufferedImage getMiniMapSizedImage(Image img, double initialDownScale){
        double ratio = ((double)width)/ Controller.getWindowWidth();
        int newWidth = (int)(img.getWidth(null)*ratio*initialDownScale);
        int newHeight = (int)(img.getHeight(null)*ratio*initialDownScale);

        Image tmp = img.getScaledInstance(newWidth,newHeight, Image.SCALE_SMOOTH);
        BufferedImage scaledImg = new BufferedImage(newWidth,newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaledImg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        return scaledImg;
    }

    /**
     *  modell koordinatakat atviszi a minimap koordinataira
     * @param modelCoords - eredeti koordinata
     * @return - lekepezett koordinata
     */
    public Vec2 modelToMiniMap(Vec2 modelCoords){
        Vec2 translateToMiniMapCenter = new Vec2(width/2.0, height/2.0);
        Vec2 translated = modelCoords.multiply(width/(double) Controller.getWindowWidth());
        translated = translated.add(translateToMiniMapCenter);

        return translated;
    }

    /**
     * minimap kirajzolasa
     * @param g - graphics component
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        /** BACKGROUND IMAGE */
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        g.drawImage(mapBackground,0,0,null);

        /** ZOOMFRAME */
        double mapToScreenRatioX = (double)width/ Controller.getWindowWidth();
        double mapToScreenRatioY = (double)height/ Controller.getWindowHeight();

        Vec2 panCenter = Camera.getCamera().getTotalPan();
        double miniMapCamCenterX = panCenter.getX()*mapToScreenRatioX;
        double miniMapCamCenterY = panCenter.getY()*mapToScreenRatioY;
        double lambda = Camera.getCamera().getLambda();
        double minZoom = Camera.getCamera().getMinZoom();
        double miniMapCamWidth = width/(lambda/minZoom);
        double miniMapCamHeight = height/(lambda/minZoom);

        g.setColor(Color.RED);
        g.drawRect((int)(miniMapCamCenterX-miniMapCamWidth/2),(int)(miniMapCamCenterY-miniMapCamHeight/2),(int)miniMapCamWidth,(int)miniMapCamHeight);

        ArrayList<Drawable> tmp = new ArrayList<>(Controller.getController().getGameScene().getDrawables());

        for(Drawable d : tmp){
            d.drawToMiniMap(g);
        }

        /** BORDER */
        g.setColor(Color.CYAN);
        g.drawRect(0,0,width,height);

    }
}