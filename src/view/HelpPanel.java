package view;

import controls.Controller;

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
 * Menubol meghivhato help panel. A fontosabb dolgokat irja le a jatekosok szamara az iranyitasrol
 */

public class HelpPanel extends JPanel {

    /**
     * A Panelhez kello gombok, textek es kepek
     */
    transient private BufferedImage backGround;
    Icon backButton = new ImageIcon(System.getProperty("user.dir")+"\\textures\\button_exit.png");
    JButton settingsBackButton=new JButton(backButton);
    JTextArea szoveg=new JTextArea();
    Font font1 = new Font("Courier New", Font.BOLD,22);
    JScrollPane scrollPane = new JScrollPane(szoveg);


    /**
     * A hatter beallitasa, ablak meretezese es a panel felepiteset beallito fuggveny meghivasa
     */
    public HelpPanel(){
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

    }

    /**
     * Panelre felhelyezi a szukseges dolgokat es beallitja a helyzetuket es a tartalmukat
     */
    private void initComponents(){
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    int allitas=(int)(Controller.getWindowHeight()*0.43296);
    add(Box.createVerticalStrut(allitas));


    scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
    settingsBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);


    szoveg.setEditable(false);
    szoveg.setText("Beallitasok:\n\n" +
            "A beallitasokban az OK gomb lenyomasaval tudod  \n" +
            "elmenteni a beallitasaidat.Max 5 Settlert tamogatunk\n"+
            "a texturak miatt. Ha Fajlbol akarsz\n" +
            "allast betolteni, a program beolvassa a\n " +
            "myObjects.txt file-t amiben automatikusan menti\n" +
            "a jatekot mentes soran. Ehhez nyomd meg a \n" +
            "LoadFile gombot \n\n" +
            "Jatekmenet:\n\n" +
            "Bal Font latod, hogy mennyi idod van lepni es\n hanyadik" +
            "kor van eppen. Az egerrel arreb lehet huzni\n a" +
            "kepet, ezzel lehet ide-oda menni es a terkepen\n" +
            "bal alul latod, hogy eppen hol vagy. Eger gorgovel\n" +
            "tudsz zoomolni. Ha ranyomsz egy szomszedos\n" +
            "aszteroidara (sarga feny jelzi, hogy melyik \nszomszedos)" +
            "akkor a telepesed atlep oda es vege a\n korodnek" +
            "Ha jobboldalt az inventorydban ranyomsz egy\n" +
            "nyersanyagra es üreges es atfurt az aszteroidad,\n" +
            "akkor a nyersanyagot le tudod helyezni. A craft\n" +
            "gombokkal tudsz robotot vagy teleportkaput\n" +
            "kesziteni. Ha nyilas teleportkapu ikonra nyomsz ra\naz" +
            "inventorydban, akkor azt le tudod helyezni ott,\n" +
            "ahol eppen vagy, ha van nalad, amit az ikon jelez.\n" +
            "Hasznalni a teleportkaput a bennelevo kis karikaba \n" +
            "kattintassal lehet." +
            "Ha ranyomsz az aszteroidara, \n" +
            "amin eppen vagy, akkor vekonyitod a kerget, vagy\n" +
            "kibanyaszod a nyersanyagat, ha mar atfurt\n" +
            "Nyertek ha egy aszteroidan van nalatok 3-3-3-3 \n" +
            "mindegyik nyersanyagbol, vagy vesztetek ha az osszes\n"+
            "model.Settler meghal\n\n"+
            "Remeljük tetszeni fog a jatek, üdvozlettel:\n" +
            "vilagiszoftprojlabteam");
    szoveg.setFont(font1);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setMaximumSize(new Dimension(700,300));
    settingsBackButton.setBorder(BorderFactory.createEmptyBorder());
    this.add(scrollPane);
    settingsBackButton.setFont(font1);
    this.add(settingsBackButton);
    SettingsBackButton();
}

    /**
     * Override-olja a paint komponentet, ezert tudjuk kirajzolni a hatteret
     * @param g graphics amin van a hatterkep
     */

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(backGround,0,0,null);

    }


    /**
     * Back button beallitasa, kilep a panelbol, vissza a menube
     */

    private void SettingsBackButton(){
        settingsBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getController().backToMenu();
            }
        });

    }

}
