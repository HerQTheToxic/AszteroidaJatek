package view;

import controls.Controller;
import model.Material;
import model.Settler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A telepes felszereleset kezelo panel
 */
public class EquipmentPanel extends JPanel {
    /** Az eppen megjleneo felszereles tulajdonosa */
    public Settler actSettler;

    /** A telepesnel levo nyersanyagokat megjeneito gombok */
    private final ArrayList<JButton> buttons = new ArrayList<>();

    /** A craftolasokat es a passzolast kezelo gombok */
    private final JButton craftTeleport = new JButton();
    private final JButton placeTeleport = new JButton();
    private final JButton craftRobot = new JButton();
    private final JButton pass = new JButton();

    /** A teleport craftolas es lehelyezes gomb külonbozo valtozatait megjelenito kepek  */
    final transient private static Image[] craftTeleportImages = new Image[4];
    final transient private static Image[] placeTeleportImages = new Image[4];

    /** A robot craftolas es a passzolas gomb kepei */
    transient private BufferedImage craftRobotImage = null;
    transient private BufferedImage passImage = null;

    /** Konstruktor */
    public EquipmentPanel(){
        setLayout(new GridLayout(7,2));
        setBackground(Color.LIGHT_GRAY);
        loadImages();
        initButtons();
    }

    /**
     * Betolti a kepeket
     */
    private void loadImages(){
        try {
            for(int i = 0; i < 4; i++){
                craftTeleportImages[i] = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\craft_tpgate_" + i + ".png"));
                placeTeleportImages[i] = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\place_tpgate_" + i + ".png"));
            }

            craftRobotImage = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\craft_robot.png"));
            passImage = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\pass.png"));

        } catch (IOException ex) {
            System.err.println("dir" + System.getProperty("user.dir"));
            ex.printStackTrace();
        }
    }

    /**
     * Frissiti a panel-t a parameterül kapott telepes felszerelesere
     * @param s - a soron kovetkezo telepes
     */
    public void displayEq(Settler s){
        actSettler = s;
        ArrayList<Material> materials = s.getMaterials();
        int tpgates = s.getGates().size();
        for(JButton b : buttons){
            b.setIcon(null);
            if(b.getActionListeners().length > 0)
                b.removeActionListener(b.getActionListeners()[0]);
        }

        placeTeleport.setIcon(new ImageIcon(placeTeleportImages[tpgates]));
        craftTeleport.setIcon(new ImageIcon(craftTeleportImages[3-tpgates]));

        for(Material m : materials){
            JButton nextBtn = buttons.get(materials.indexOf(m));
            m.getMyDraw().drawOnButton(nextBtn);
            nextBtn.addActionListener( e -> materialPressed(m) );
        }
    }

    /**
     * Inicializalja a panelt es rajta a gombokat
     */
    private void initButtons(){
        craftTeleport.setIcon(new ImageIcon(craftTeleportImages[0]));
        craftTeleport.setBackground(Color.DARK_GRAY);
        craftTeleport.addActionListener(e -> craftTeleportPressed());
        add(craftTeleport);

        placeTeleport.setIcon(new ImageIcon(placeTeleportImages[0]));
        placeTeleport.addActionListener(e -> placeTeleportPressed());
        placeTeleport.setBackground(Color.DARK_GRAY);
        add(placeTeleport);

        craftRobot.setIcon(new ImageIcon(craftRobotImage));
        craftRobot.addActionListener(e -> craftRobotPressed());
        craftRobot.setBackground(Color.DARK_GRAY);
        add(craftRobot);

        pass.setIcon(new ImageIcon(passImage));
        pass.addActionListener(e -> passPressed());
        pass.setBackground(Color.DARK_GRAY);
        add(pass);

        for (int i = 0; i < 10; i++) {
            JButton button = new JButton();
            button.setBackground(Color.DARK_GRAY);
            buttons.add(button);
            add(button);
        }
    }

    /** A gombok lenyomasat kezelo függvenyek */
    private void craftTeleportPressed(){ actSettler.createTeleport(); }
    private void placeTeleportPressed(){ actSettler.placeTeleport(); }
    private void craftRobotPressed() { actSettler. createRobot(); }
    private void passPressed(){ Controller.getController().goNext(); }
    private void materialPressed(Material mat){ actSettler.storeMaterial(mat); }

}
