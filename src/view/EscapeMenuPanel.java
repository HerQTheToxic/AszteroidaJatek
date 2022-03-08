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
import java.io.*;

/**
 * Escape lenyomasara jelenik meg ez a panel
 */
public class EscapeMenuPanel extends JPanel{
    /** A gombok iconjai */
    transient Icon resumeIcon = new ImageIcon(System.getProperty("user.dir")+"\\textures\\button_resume.png");
    transient Icon surrenderIcon = new ImageIcon(System.getProperty("user.dir")+"\\textures\\button_surrender.png");
    transient Icon saveIcon = new ImageIcon(System.getProperty("user.dir") + "\\textures\\\\button_save.png");
    transient Icon exitAndSaveIcon = new ImageIcon(System.getProperty("user.dir")+"\\textures\\button_saveandexit.png");

    /** A gombok */
    JButton resumeButton=      new JButton(resumeIcon);
    JButton surrenderButton=   new JButton(surrenderIcon);
    JButton saveButton=        new JButton(saveIcon);
    JButton exitAndSaveButton= new JButton(exitAndSaveIcon);

    /** A felirat betutipusa */
    Font font1 = new Font("Courier New", Font.BOLD,35);

    /** A hatterkep */
    transient private BufferedImage backGround;

    /**
     * Hatter beolvasasa es a gombok felhelyezesehez kello fuggveny meghivasa
     */
    public EscapeMenuPanel(){
        super();
        try {
            backGround = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\Menu2.png"));
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
        this.setSize(new Dimension(Controller.getWindowWidth(), Controller.getWindowHeight()));
    }

    /**
     * gombok es textek felhelyezese
     * gombok mukodesenek beallitasa
     */

    private void initComponents() {
        setResumeButton();
        setSurrenderButton();
        setSaveButton();
        setExitAndSaveButton();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        resumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        surrenderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitAndSaveButton.setAlignmentX(Component.CENTER_ALIGNMENT);


        int allitas=(int)(Controller.getWindowHeight()*0.45);
        add(Box.createVerticalStrut(allitas));

        saveButton.setFont(font1);
        saveButton.setBorder(BorderFactory.createEmptyBorder());
        this.add(saveButton);


        resumeButton.setFont(font1);
        resumeButton.setBorder(BorderFactory.createEmptyBorder());
        this.add(resumeButton);

        exitAndSaveButton.setFont(font1);
        exitAndSaveButton.setBorder(BorderFactory.createEmptyBorder());
        this.add(exitAndSaveButton);


        surrenderButton.setFont(font1);
        surrenderButton.setBorder(BorderFactory.createEmptyBorder());
        this.add(surrenderButton);


        setVisible(false);

    }
    /** Beallitja a resume gomb-ot */
    private void setResumeButton(){
        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Resume();
            }
        });

    }
    /** Beallitja a surrender gomb-ot */
    private void setSurrenderButton(){
        surrenderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Surrender();
            }
        });

    }
    /** Beallitja a Save gomb-ot */
    private void setSaveButton(){
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Save();
            }
        });

    }
    /** Beallitja az ExitAndSave gomb-ot */
    private void setExitAndSaveButton(){
        exitAndSaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExitEndSave();
            }
        });

    }

    /**
     * resume gomb lenyomasara visszalep a menube
     */
    private void Resume(){

        this.setVisible(false);
        Controller.getController().gameResume();
        Controller.getController().getGameScene().getStatusBarPanel().setVisible(true);
        Controller.getController().getGameScene().getGamePanel().setVisible(true);
        Controller.getController().getGameScene().getMapPanel().setVisible(true);
        Controller.getController().getGameScene().getEquipmentPanel().setVisible(true);

    }

    /**
     * Feladaskor megjelenik az endgamepanel
     */
    private void Surrender(){

        Controller.getController().getGameScene().endGame();

    }

    /**
     * Save hatasara menti fileba a jatekot
     */
    private void Save()  {

        try{
            Game jatek= Game.getGame();
            FileOutputStream f = new FileOutputStream(new File("myObjects.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(jatek);
            o.close();
            f.close();

        }
        catch(IOException  e){
            e.printStackTrace();
        }
        finally {
            Resume();
        }

    }

    /**
     * Menti a jatekot es kilep a programbol
     */
    private void ExitEndSave(){
        try{
            Game jatek= Game.getGame();
            FileOutputStream f = new FileOutputStream(new File("myObjects.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(jatek);
            o.close();
            f.close();

        }
        catch(IOException  e){
            e.printStackTrace();
            System.out.println("hiba");
        }
        finally {
            Controller.getController().backToMenu();
        }
    }

    /**
     * Panel hatterenek beallitasa
     * @param g
     */

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(backGround,0,0,null);

    }

}
