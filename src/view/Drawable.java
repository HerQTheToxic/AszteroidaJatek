package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * A kirajzolast kezelo ososztaly
 */
public abstract class Drawable  {
    /** Az objektum rajzolási pozicioja */
    protected int posZ;

    /** Az objektum kepei */
    transient protected BufferedImage displayedImage;
    transient protected Image originalImage;
    transient protected BufferedImage miniMapImage;

    /**
     * Kirajzolja az objektumot a terkepre
     * @param miniMapPanelG - a minimap panel grafikaja
     */
    public abstract void drawToMiniMap(Graphics miniMapPanelG);

    /**
     * Kirajzolja az objektumot a palyara
     * @param gamePanelG - a jatek panel grafikaja
     */
    public abstract void drawToGamePanel(Graphics gamePanelG);

    /**
     * Kezeli az objektumra kattintast
     * @param x - a kattintas X koordinataja
     * @param y - a kattintas Y koordinataja
     * @return - true, ha a kattintas ra tortent
     */
    public abstract boolean clicked(int x, int y);

    /** Frissiti azobjektum megjeleno kepet */
    public abstract void updateImage();

    /** Inicializalja a terkep kepet */
    public abstract void initMiniMapImg();

}
