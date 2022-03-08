package view;

import controls.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Jatekmenet panele
 */
public class GamePanel extends JPanel {

    /** hatter */
    transient private BufferedImage backGround;

    /**
     * konstruktor, hatteret tolti be
     */
    public GamePanel(){
        try {
            backGround = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\background.png"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * panel kirajzolasa
     * @param g - grafika
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backGround,0,0,null);

        ArrayList<Drawable> tempDrawable = new ArrayList<>(Controller.getController().getGameScene().getDrawables());
        for (Drawable d : tempDrawable) {
            d.drawToGamePanel(g);
        }

    }
}
