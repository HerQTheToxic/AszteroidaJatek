package view;

import model.Material;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * A nyersanyagok gombokra rajzolasaert felelos absztrakt osztaly
 */
public abstract class MaterialDraw {
    /** A nyersanyag kepe */
    private final BufferedImage image = null;

    /** Konstruktor */
    public MaterialDraw(Material _mat){}

    /**
     * A parameterül kapott gomb icon-jat beallitja a megfelelo material kepre
     * @param button - a beallitando gomb
     */
    abstract public void drawOnButton(JButton button);

}
