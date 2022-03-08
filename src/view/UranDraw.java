package view;

import model.Uran;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Az uran kirjazolasaert felelos osztaly
 */
public class UranDraw extends MaterialDraw {
    /**
     * az observed model.Uran object
     */
    private final Uran myUran;

    /**
     * az elso image-e az urannak, 0 expocizio
     */
    transient static private BufferedImage image_0 = null;

    /**
     * az masodik image-e az urannak , 1 expozico
     */
    transient static private BufferedImage image_1 = null;

    /**
     * a harmadik image-e az urannak, 2 expozicio
     */
    transient static private BufferedImage image_2 = null;

    /**
     * az eppen kijelzett image
     */
    transient BufferedImage myImage;
    static{
        try { /** Fenykepek betoltese */
            image_0 = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\uran_0.png"));
            image_1 = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\uran_1.png"));
            image_2 = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\uran_2.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * konstruktor
     * @param _uran az observed uran
     */
    public UranDraw(Uran _uran){
        super(_uran);
        myUran = _uran;
        myImage = image_0;
    }

    /**
     * A parameterül kapott gomb icon-jat beallitja a megfelelo kepre
     * @param button - a beallitando gomb
     */
    public void drawOnButton(JButton button){
        if(myUran.getExpCount() == 0){
            myImage = image_0;
        }
        if(myUran.getExpCount() == 1){
            myImage = image_1;
        }
        else{
            myImage = image_2;
        }
        Icon icon = new ImageIcon(myImage);
        button.setIcon(icon);
    }

}
