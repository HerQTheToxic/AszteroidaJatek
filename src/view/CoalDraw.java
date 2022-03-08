package view;

import model.Coal;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A szen kirjazolasaert felelos osztaly
 */
public class CoalDraw extends MaterialDraw {
    /** A szen kepe */
    transient private static BufferedImage image = null;

    static {
        try { /** A fenykep betoltese */
            image = ImageIO.read(new File(System.getProperty("user.dir") + "\\textures\\coal.png"));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /** Konstruktor */
    public CoalDraw(Coal _coal){
        super(_coal);
    }

    /**
     * A parameterül kapott gomb icon-jat beallitja a tarolt kepre
     * @param button - a beallitando gomb
     */
    public void drawOnButton(JButton button){
        Icon icon = new ImageIcon(image);
        button.setIcon(icon);
    }

}
