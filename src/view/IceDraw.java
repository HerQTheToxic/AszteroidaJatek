package view;

import model.Ice;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A jeg kirjazolasaert felelos osztaly
 */
public class IceDraw extends MaterialDraw {
    transient private static BufferedImage image = null;
    static{
        try { /** A fenykep betoltese */
            image = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\ice.png"));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /** Konstruktor */
    public IceDraw(Ice _ice) {
        super(_ice);
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
