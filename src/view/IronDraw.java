package view;

import model.Iron;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A vas kirjazolasaert felelos osztaly
 */
public class IronDraw extends MaterialDraw {
    transient private static BufferedImage image = null;
    static{
        try { /** A fenykep betoltese */
            image = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\iron.png"));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * jeg rajzolasa
     * @param _iron - jeg
     */
    public IronDraw(Iron _iron){
        super(_iron);
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
