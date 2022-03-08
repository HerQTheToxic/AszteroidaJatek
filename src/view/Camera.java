package view;

import controls.Controller;
import vectors.Vec2;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * view.Camera class , so that the user can zoom and pan in the world
 */
public class Camera   {

    /**
     * camera Singleton object
     */
    private static final Camera camera = new Camera();

    /**
     * camera getter
      * @return camera
     */
    public static Camera getCamera(){
        return camera;
    }

    private Vec2 pan = new Vec2(0,0);


    /**
     * zoom erteke
     */
    private double lambda = 1.5;

    /**
     * max zoom erteke
     */
    private final double MAX_ZOOM = 5;

    /**
     * MAX_ZOOM getter
     * @return
     */
    public double getMaxZoom() {
        return MAX_ZOOM;
    }

    /**
     *  MIN_ZOOM getter
     * @return MIN_ZOOM
     */
    public double getMinZoom() {
        return MIN_ZOOM;
    }

    /**
     * MIN_ZOOM erteke
     */
    private final double MIN_ZOOM = 1;

    /**
     * always translating the model from (0,0) to the center of the screen
     */
    private final Vec2 CENTER_OF_THE_WORLD = new Vec2(Controller.getWindowWidth()/2.0, Controller.getWindowHeight()/2.0);

    /**
     * lambda getter
     * @return lambda
     */
    public double getLambda() {
        return lambda;
    }

    /**
     * default konstuktor
     */
    private Camera(){

    }

    /**
     * gets an image and returns a scaled version of it.
     * @param img the image to be scaled
     * @param initialDownScale the initialdownscale of the original image source
     * @return the scaled image
     */
    public BufferedImage getZoomedImage(Image img, double initialDownScale){
        int newWidth = (int)(img.getWidth(null)*lambda*initialDownScale);
        int newHeight = (int)(img.getHeight(null)*lambda*initialDownScale);

        Image tmp = img.getScaledInstance(newWidth,newHeight, Image.SCALE_SMOOTH);
        BufferedImage scaledImg = new BufferedImage(newWidth,newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaledImg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        return scaledImg;
    }

    /**
     * gets a model coord  and returns the screen equivalent of it. MODEL --> SCREEN transformation
     * @param modelCoords the original model coords
     * @return the screen coords
     */
    public Vec2 getScreenCoords(Vec2 modelCoords){
        Vec2 translatedToCenter = modelCoords.add(CENTER_OF_THE_WORLD);
        Vec2 totalPan = getTotalPan();
        Vec2 dirFromTotalPan = new Vec2(translatedToCenter.getX()-totalPan.getX(),translatedToCenter.getY()-totalPan.getY());
        return CENTER_OF_THE_WORLD.add(dirFromTotalPan.multiply(lambda));
    }


    /**
     * shifts the pan of the camera. Checks if the pan is not too big, so that the camera can stay "close" to the world,
     * cannot pan to infinity
     * @param dir the direction which is to be added to the current pan value
     */
    public void addPanMovement(Vec2 dir){
        Vec2 newPan = pan.add(dir);
        if(panIsWithinBorders(newPan)){
            pan = pan.add(dir);

        }
    }

    /**
     * checks if a pan is valid, wont let the camera to pan to infinity
     * @param newPan the new pan which is should be checked if it is valid
     * @return validity. true if valid, false otherwise
     */
    private boolean panIsWithinBorders(Vec2 newPan){
        Vec2 newTotalPan = CENTER_OF_THE_WORLD.add(newPan);
        return newTotalPan.getX() <= Controller.getWindowWidth() && newTotalPan.getX() >= 0 && newTotalPan.getY() <= Controller.getWindowHeight() && newTotalPan.getY() >= 0;
    }


    /**
     * increases the zoom of the camera py the parameter.
     * wont let the camera to zoom to infinity or to 0.
     * the multiplied value should be between the MIN_ZOOM and the MAX_ZOOM values
     * @param mult
     */
    public void multiplyZoom(double mult){
        double newZoom = mult*lambda;
        if(newZoom <= MIN_ZOOM){
            lambda = MIN_ZOOM;
        }
        else if(newZoom >= MAX_ZOOM){
            lambda = MAX_ZOOM;
        }
        else{
            lambda = newZoom;
        }
    }


    /**
     * returns the total pan of the camera. that is:
     * the pan of the camera plus the translation to the center of the screen
     * @return the value above
     */
    public Vec2 getTotalPan(){
        return pan.add(CENTER_OF_THE_WORLD);
    }

}
