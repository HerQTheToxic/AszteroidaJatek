package view;

import controls.Controller;
import model.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Ha a jatek vegeter gyozelemmel vagy a jatekosok veszitenek, akkor ez az ablak jelenik meg
 */
public class EndGamePanel extends JPanel {

    /**
     * A panelen levo gombok es labelek beallitasa
     * A betutipus es kepuk
     */
    Font font1 = new Font("Courier New", Font.BOLD,40);
    Font font2 = new Font("Courier New", Font.BOLD,180);
    JLabel elertPontokSzoveg = new JLabel("Tulelt korok: ");
    JLabel szam = new JLabel("");
    JLabel toltelek = new JLabel("            ");

    /** A panel hatterkepe */
    transient private BufferedImage backGround;

    /** OK gomb es a kepe */
    Icon okIcon = new ImageIcon(System.getProperty("user.dir")+"\\textures\\button_exit.png");
    JButton okButton= new JButton(okIcon);


    /**
     * Beallitja a hatterkepet, atmeretezi es meghivja az initComponents-et a Panelere
     */
    public EndGamePanel(){
        super();
        try {
            if(Game.getGame().getWin()){
                backGround = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\GameWin.png"));
            }
            else{
                backGround = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\GameOver.png"));
            }

            int w = backGround.getWidth();
            int h = backGround.getHeight();
            BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            AffineTransform at = new AffineTransform();

            int magassag= Controller.getWindowWidth();
            double scale=magassag/1920.0;
            at.scale(scale,scale);
            AffineTransformOp scaleOp =
                    new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            backGround = scaleOp.filter(backGround, after);

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        initComponents();
        this.setSize(Controller.getWindowWidth(), Controller.getWindowHeight());
    }


    /**
     * A gombokat kozepre teszi a Panelen es beallitja a texturajukat es az OK button-hoz rendeli a feladatat
     */
    private void initComponents(){


        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        elertPontokSzoveg.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        szam.setAlignmentX(Component.CENTER_ALIGNMENT);
        toltelek.setAlignmentX(Component.CENTER_ALIGNMENT);


        int allitas=(int)(Controller.getWindowHeight()*0.4);
        add(Box.createVerticalStrut(allitas));
        elertPontokSzoveg.setFont(font1);
        this.add(elertPontokSzoveg);
        szam.setText(String.valueOf(Game.getGame().getScore()));
        szam.setFont(font1);
        this.add(szam);
        toltelek.setFont(font2);
        this.add(toltelek);
        okButton.setBorder(BorderFactory.createEmptyBorder());
        this.add(okButton);
        this.setVisible(false);
        setOkButton();

    }

    /**
     * ok Button beallitasa
     */

    private void setOkButton(){

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ok();
            }
        });
    }

    /**
     * Ha meghivjak, akkor kilep a programbol
     */
    public void Ok(){
        Controller.getController().backToMenu();
    }

    /**
     *Paint függveny felulirasa, ezert fog megjelenni a hatter
     */
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(backGround,0,0,null);

    }

}
