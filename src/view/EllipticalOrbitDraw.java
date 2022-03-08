package view;

import controls.Controller;
import model.EllipticalOrbit;
import vectors.Vec2;

import java.awt.*;

/**
 * Az aszetoird palya kirajzolasaert felelos osztaly
 */
public class EllipticalOrbitDraw extends Drawable {
    /** A kirajzolando aszetoird palya */
    private final EllipticalOrbit myEllipticalOrbit;

    /** A terkep koordinatai */
    private Vec2 miniMapCoords;

    /** A terkep tovabbi meretei */
    double miniMapDiameter;
    double miniMapRectWidth;
    double miniMapRectHeight;

    /** Konstruktor */
    public EllipticalOrbitDraw(EllipticalOrbit eo){
        myEllipticalOrbit = eo;
    }

    /**
     * Kirajzolja az aszetoird palya a terkepre
     * @param miniMapPanelG - a minimap panel grafikaja
     */
    @Override
    public void drawToMiniMap(Graphics miniMapPanelG) {
        miniMapPanelG.setColor(Color.lightGray);
        miniMapPanelG.drawOval((int)(miniMapCoords.getX()-miniMapRectWidth/2),(int)(miniMapCoords.getY()-miniMapRectHeight/2),(int)miniMapRectWidth,(int)miniMapRectHeight);
    }

    /**
     * Kirajzolja az aszetoird palya a palyara
     * @param gamePanelG - a jatek panel grafikaja
     */
    @Override
    public void drawToGamePanel(Graphics gamePanelG) {
        double radius = myEllipticalOrbit.getRadius();
        double ratio = EllipticalOrbit.getEllipseWidthToHeightRatio();

        Vec2 viewCoords = Camera.getCamera().getScreenCoords(myEllipticalOrbit.getPosition());

        int rectWidth = (int)(radius*2*ratio* Camera.getCamera().getLambda());
        int rectHeight = (int)(radius*2* Camera.getCamera().getLambda());


        gamePanelG.setColor(Color.GRAY);
        gamePanelG.drawOval((int)viewCoords.getX()-rectWidth/2,(int)viewCoords.getY()-rectHeight/2,rectWidth,rectHeight);
    }

    /**
     * Kezeli az aszetoird palyara kattintast
     * @param x - a kattintas X koordinataja
     * @param y - a kattintas Y koordinataja
     * @return - mindig false, az aszetoird palyara kattintasnal semmi sem tortenik
     */
    @Override
    public boolean clicked(int x, int y) {
        return false;
    }

    /** Frissiti az aszetoird palya megjeleno kepet */
    @Override
    public void updateImage() { }

    /** Inicializalja a terkep kepet */
    @Override
    public void initMiniMapImg() {
        miniMapCoords = Controller.getController().getGameScene().getMapPanel().modelToMiniMap(myEllipticalOrbit.getPosition());
        miniMapDiameter = myEllipticalOrbit.getRadius()*2;
        miniMapRectWidth = miniMapDiameter* EllipticalOrbit.getEllipseWidthToHeightRatio() / Controller.getWindowWidth()*MapPanel.getMapPanelWidth();
        miniMapRectHeight = miniMapDiameter / Controller.getWindowHeight()*MapPanel.getMapPanelHeight();
    }
}
