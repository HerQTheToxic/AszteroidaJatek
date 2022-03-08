package view;

import controls.Controller;
import model.*;
import vectors.Vec2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * OBSERVER OF AN ASTEROID
 * it displays the observed asteroid to the minimap and the gamepanel
 */
public class AsteroidDraw extends Drawable {
    private Asteroid myAsteroid;

    /** A külonbozo kepeket tarolo 4D tomb */
    transient private static Image[][][][] texs = new Image[3][8][2][3];

    /** A napvihart megjelenito kepek */
    transient private static Image fireRingImage;
    transient private static Image umbrellaImage;

    /** Az aszteroida külonbozo allapotait tarolo valtozok */
    private static final int FULL_ASTEROID = 0;
    private static final int SLIGHTLY_DRILLED_ASTEROID = 1;
    private static final int ALMOST_FULLY_DRILLED_ASTEROID = 2;
    private static final int DRILLED_AND_EMPTY = 3;
    private static final int DRILLED_AND_COAL = 4;
    private static final int DRILLED_AND_ICE = 5;
    private static final int DRILLED_AND_IRON = 6;
    private static final int DRILLED_AND_URAN = 7;

    /** Betolti a 4D tombot */
    static{
        try{
            for(int i = 0; i < 3; i++) {
                texs[i][0][0][0] = ImageIO.read(new File(System.getProperty("user.dir") + "\\textures\\ast_" + i + ".png"));
                texs[i][1][0][0] = ImageIO.read(new File(System.getProperty("user.dir") + "\\textures\\ast_" + i + "_drilled1.png"));
                texs[i][2][0][0] = ImageIO.read(new File(System.getProperty("user.dir") + "\\textures\\ast_" + i + "_drilled2.png"));
                texs[i][3][0][0] = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\ast_" + i + "_empty.png"));
                texs[i][4][0][0] = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\ast_" + i + "_coal.png"));
                texs[i][5][0][0] = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\ast_" + i + "_ice.png"));
                texs[i][6][0][0] = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\ast_" + i + "_iron.png"));

                texs[i][7][0][0] = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\ast_" + i + "_uran_0.png"));
                texs[i][7][0][1] = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\ast_" + i + "_uran_1.png"));
                texs[i][7][0][2] = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\ast_" + i + "_uran_2.png"));

                texs[i][0][1][0] = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\ast_nb_" + i + ".png"));
                texs[i][1][1][0] = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\ast_nb_" + i + "_drilled1.png"));
                texs[i][2][1][0] = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\ast_nb_" + i + "_drilled2.png"));
                texs[i][3][1][0] = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\ast_nb_" + i + "_empty.png"));
                texs[i][4][1][0] = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\ast_nb_" + i + "_coal.png"));
                texs[i][5][1][0] = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\ast_nb_" + i + "_ice.png"));
                texs[i][6][1][0] = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\ast_nb_" + i + "_iron.png"));
                texs[i][7][1][0] = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\ast_nb_" + i + "_uran_0.png"));
                texs[i][7][1][1] = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\ast_nb_" + i + "_uran_1.png"));
                texs[i][7][1][2] = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\ast_nb_" + i + "_uran_2.png"));
                fireRingImage = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\flame.png"));
                umbrellaImage = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\umbrella.png"));
            }
        }
        catch(IOException e) {
            System.out.println("hiba asteroid kepek beolvasasasnal");
            e.printStackTrace();
        }
    }

    /** Az aszteroida allapota es valtozata */
    private int myState = 0;
    private int variant = 0;

    /** Az aktualis allapot kepe */
    private Image state;

    /** eppen a gamepanelre valtoztatott merettel kirajzolt img pos */
    private Vec2 actImageOnGamePanelPos;

    /** Az eppen kirajzolt kep szelessege */
    private static int currentAsteroidImageWidth;

    /** Az eredeti kep lekepezese (512x512 image to 128x128) */
    private final double initialDownScale = 128.0/512;

    /**
     * currentAsteroidImageWidth getter
     * @return currentAsteroidImageWidth
     */
    public static int getAsteroidImageWidth(){
        return currentAsteroidImageWidth;
    }


    /**
     * miniMapImageSize getter
     * @return miniMapImageSize
     */
    public static int getMiniMapImageSize() {
        return miniMapImageSize;
    }

    /** displayed minimap image size, -1 is invalid value to start */
    private static int miniMapImageSize = -1;


    /**
     * constructor
     * @param a the observed asteroid
     */
    public AsteroidDraw(Asteroid a) {
        myAsteroid  = a;

        Random rand = new Random();
        variant = rand.nextInt(100) % 3;

        originalImage = texs[variant][0][0][0];
        state = null;

    }


    /**
     * drawing asteroid to minimap
     * @param miniMapPanelG the Graphics to draw on
     */
    @Override
    public void drawToMiniMap(Graphics miniMapPanelG) {
        Vec2 miniMapCoords = Controller.getController().getGameScene().getMapPanel().modelToMiniMap(myAsteroid.getPosition());
        miniMapPanelG.drawImage(miniMapImage,(int)miniMapCoords.getX()-miniMapImage.getWidth()/2,(int)miniMapCoords.getY()-miniMapImage.getHeight()/2,null);
    }


    /**
     * drawing asteroid to gamepanel
     * @param gamePanelG the gamepanel graphics
     */
    public void drawToGamePanel(Graphics gamePanelG) {
        Settler actSettler = Controller.getController().getActualSettler();
        if(actSettler != null) {
            Asteroid actAsteroid = actSettler.getAsteroid();
            int isN = actAsteroid.getNeighbours().contains(myAsteroid) ? 1 : 0;
            int prevState = myState;

            if (myAsteroid.getCrustThickness() > 5) {
                originalImage = texs[variant][0][isN][0];
                myState = FULL_ASTEROID;
            }
            else if (myAsteroid.getCrustThickness() > 3){
                originalImage = texs[variant][1][isN][0];
                myState = SLIGHTLY_DRILLED_ASTEROID;
            }
            else if (myAsteroid.getCrustThickness() > 0) {
                originalImage = texs[variant][2][isN][0];
                myState = ALMOST_FULLY_DRILLED_ASTEROID;
            }
            else if (myAsteroid.getCrustThickness() == 0 && myAsteroid.getCore() == null) {
                originalImage = texs[variant][3][isN][0];
                myState = DRILLED_AND_EMPTY;
            }
            else if (myAsteroid.getCore().isEqual(new Coal(null))) {
                originalImage = texs[variant][4][isN][0];
                myState = DRILLED_AND_COAL;
            }
            else if (myAsteroid.getCore().isEqual(new Ice(null))) {
                originalImage = texs[variant][5][isN][0];
                myState = DRILLED_AND_ICE;
            }
            else if (myAsteroid.getCore().isEqual(new Iron(null))) {
                originalImage = texs[variant][6][isN][0];
                myState = DRILLED_AND_IRON;
            }
            else if (myAsteroid.getCore().isEqual(new Uran(null))) {
                Uran u = (Uran)myAsteroid.getCore();
                int c = u.getExpCount();
               // System.out.println(c);
                if(c < 3) {
                    originalImage = texs[variant][7][isN][c];
                }
                else{
                    originalImage = texs[variant][7][isN][0];
                }
                myState = DRILLED_AND_URAN;
            }
            else {
                originalImage = texs[variant][0][isN][0];
                myState = 0;
            }

            if(prevState != myState){
//                    state = originalImage;
                updateImage();
            }
        }

        Vec2 viewCoords = Camera.getCamera().getScreenCoords(myAsteroid.getPosition());/**GET SCREEN COORDS from model*/

        actImageOnGamePanelPos = new Vec2((int)viewCoords.getX()-displayedImage.getWidth()/2.0,(int)viewCoords.getY()-displayedImage.getHeight()/2.0);

        gamePanelG.drawImage(displayedImage,(int)viewCoords.getX()-displayedImage.getWidth()/2,(int)viewCoords.getY()-displayedImage.getHeight()/2,null);

        if(Game.getGame().getSun().sunStormIsON() && myAsteroid.wasHitInLastSunStorm()) {
            Image img;
            if (myState == DRILLED_AND_EMPTY && !myAsteroid.getBeings().isEmpty()) {
                img = Camera.getCamera().getZoomedImage(umbrellaImage, initialDownScale);
            }
            else{
                img = Camera.getCamera().getZoomedImage(fireRingImage, initialDownScale);
            }
            gamePanelG.drawImage(img, (int) viewCoords.getX() - displayedImage.getWidth() / 2, (int) viewCoords.getY() - displayedImage.getHeight() / 2, null);
        }
        currentAsteroidImageWidth = displayedImage.getWidth();

    }

    /**
     * checks if the asteroid has been clicked. returned is it was(true) / or not (false)
     * @param x x of the mouseClick
     * @param y y of the mouseCLick
     * @return return
     */
    @Override
    public boolean clicked(int x, int y) {
        Settler actSettler = Controller.getController().getActualSettler();
        boolean clicked = x > actImageOnGamePanelPos.getX() && x < actImageOnGamePanelPos.getX() + displayedImage.getWidth()
                && y < actImageOnGamePanelPos.getY() + displayedImage.getHeight() && y > actImageOnGamePanelPos.getY();


        if(clicked) {
            if (myAsteroid.getBeings().contains(actSettler)) {//DRILL AND MINE
                if (myState <= 2) {
                    actSettler.drill(myAsteroid.isTooClose());
                    Controller.getController().goNext();
                    return true;
                } else if (myState >= 4 && myState <= 7) {
                    actSettler.mine();
                    Controller.getController().goNext();
                    return true;
                }


            }

            ArrayList<Neighbour> asts = new ArrayList<Neighbour>(myAsteroid.getNeighbours());
            asts.removeAll(myAsteroid.getTpGates());
            ArrayList<Asteroid> onlyAst = new ArrayList<>();
            for(Neighbour n : asts){ //avoiding concurrent modification exception
                onlyAst.add( (Asteroid) n);
            }

            for(Asteroid a : onlyAst){ //MOVE SETTLER
                if(a.getBeings().contains(actSettler)){
                    actSettler.move(myAsteroid);
                    Controller.getController().goNext();
                    return true;
                }
            }

        }
        return false;
    }


    /**
     * updating image, called is the camera has been zoomed
     */
    @Override
    public void updateImage() {
        displayedImage = Camera.getCamera().getZoomedImage(originalImage,initialDownScale);
        currentAsteroidImageWidth = displayedImage.getWidth();
    }

    /**
     * initializing minimap image. The minimap is only created after the game had been started.
     */
    @Override
    public void initMiniMapImg() {
        miniMapImage = Controller.getController().getGameScene().getMapPanel().getMiniMapSizedImage(originalImage,initialDownScale);
        miniMapImageSize = miniMapImage.getWidth();
    }


}
