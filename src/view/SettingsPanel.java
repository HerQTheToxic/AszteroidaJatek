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
 * A jatekos ezt tudja meghivni a menubol es itt tudja kezelni a beallitasokat
 */
public class SettingsPanel extends JPanel {

    /** Tarolja, hogy van-e betoltott fajl */
    private boolean loaded = false;

    /**
     * be van e toltve file
     * @return - be van e toltve file
     */
    public boolean isLoaded(){
        return loaded;
    }

    /**
     * textfieldek
     */
    Font font1 = new Font("Courier New", Font.BOLD,35);
    JTextField nSettler = new JTextField(9);
    JTextField timer = new JTextField(9);
    JTextField nAsteroid = new JTextField(8);
    JTextField nElo = new JTextField(6);
    JTextField loadFile = new JTextField(11);

    /**
     * Labelek
     */

    JLabel lnSettler = new JLabel("Settlerek szama: ");
    JLabel lTimer = new JLabel("Lepesre szant ido: ");
    JLabel lnAsteroid = new JLabel("Aszteroidak szama:  ");
    JLabel lnElo=new JLabel("Elliptical orbitok szama: ");
    JLabel lloadfile=new JLabel("Betoltento fajl: ");


    /**
     * getterek
     * @return
     */
    public int getTime(){
        return time;
    }
    public int getNSettler(){
        return settlerCount;
    }
    public int getNAsteroid(){
        return asteroidCount;
    }
    public int getNElo(){
        return eloCount;
    }
    public String getFileName(){
        return fName;
    }

    /** hatterkep */
    transient private BufferedImage backGround;

    /** Ido */
    private int time=20;

    /** Objektumok szama */
    private int settlerCount=5;
    private int asteroidCount=20;
    private int eloCount=3;

    /** Fajlnev */
    String fName;

    /** Icon-ok */
    Icon okIcon = new ImageIcon(System.getProperty("user.dir")+"\\textures\\button_ok.png");
    Icon backIcon = new ImageIcon(System.getProperty("user.dir")+"\\textures\\button_back.png");
    Icon FileIcon = new ImageIcon(System.getProperty("user.dir")+"\\textures\\button_loadfile.png");

    /** Gombok */
    JButton okButton= new JButton(okIcon);
    JButton back= new JButton(backIcon);
    JButton FileButton= new JButton(FileIcon);

    /**
     * konstruktor, osszerakja a settingspanel hatteret, komponenseit
     */
    public SettingsPanel(){
        super();
        /** hatter */
        try {
            backGround = ImageIO.read(new File(System.getProperty("user.dir") + "\\textures\\Menu2.png"));

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
            ex.printStackTrace();
        }
        /** komponensek ossze */
        initComponents();
        this.setSize(Controller.getWindowWidth(), Controller.getWindowHeight());
    }

    /**
     * gombok es textek felhelyezese
     */
    private void initComponents(){

        JPanel seged=new JPanel(new FlowLayout());

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /** mindent kozepre */
        lnSettler.setAlignmentX(Component.CENTER_ALIGNMENT);
        lnAsteroid.setAlignmentX(Component.CENTER_ALIGNMENT);
        lnElo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lTimer.setAlignmentX(Component.CENTER_ALIGNMENT);
        lloadfile.setAlignmentX(Component.CENTER_ALIGNMENT);

        /** gap */
        int allitas=(int)(Controller.getWindowHeight()*0.45);
        add(Box.createVerticalStrut(allitas));

        /** settlers label */
        lnSettler.setFont(font1);
        lnSettler.setBorder(BorderFactory.createEmptyBorder());
        this.add(lnSettler);

        /** settlers count */
        nSettler.setFont(font1);
        nSettler.setText(String.valueOf(settlerCount));
        nSettler.setBorder(BorderFactory.createEmptyBorder());
        nSettler.setMaximumSize(new Dimension(400,50));
        this.add(nSettler);

        /** asteroids label */
        lnAsteroid.setFont(font1);
        lnAsteroid.setBorder(BorderFactory.createEmptyBorder());
        this.add(lnAsteroid);


        /** asteroids count */
        nAsteroid.setFont(font1);
        nAsteroid.setText(String.valueOf(asteroidCount));
        nAsteroid.setBorder(BorderFactory.createEmptyBorder());
        nAsteroid.setMaximumSize(new Dimension(400,50));
        this.add(nAsteroid);

        /** elos label */
        lnElo.setFont(font1);
        lnElo.setBorder(BorderFactory.createEmptyBorder());
        this.add(lnElo);

        /** elos count */
        nElo.setFont(font1);
        nElo.setText(String.valueOf(eloCount));
        nElo.setBorder(BorderFactory.createEmptyBorder());
        nElo.setMaximumSize(new Dimension(400,50));
        this.add(nElo);

        /** timePerRoubd label */
        lTimer.setFont(font1);
        lTimer.setBorder(BorderFactory.createEmptyBorder());
        this.add(lTimer);

        /** timePerRound count */
        timer.setFont(font1);
        timer.setText(String.valueOf(time));
        timer.setBorder(BorderFactory.createEmptyBorder());
        timer.setMaximumSize(new Dimension(400,50));
        this.add(timer);

        /** loadFile label */
        lloadfile.setFont(font1);
        lloadfile.setBorder(BorderFactory.createEmptyBorder());
        this.add(lloadfile);

        /** loadFile text */
        loadFile.setFont(font1);
        loadFile.setText("myObjects.txt");
        loadFile.setBorder(BorderFactory.createEmptyBorder());
        loadFile.setMaximumSize(new Dimension(400,50));
        this.add(loadFile);

        /** buttons */
        okButton.setFont(font1);
        back.setFont(font1);
        FileButton.setFont(font1);
        okButton.setBorder(BorderFactory.createEmptyBorder());
        back.setBorder(BorderFactory.createEmptyBorder());
        FileButton.setBorder(BorderFactory.createEmptyBorder());

        /** panel init */
        seged.setMaximumSize(new Dimension(820,110));
        seged.setBackground(Color.BLACK);
        seged.add(okButton);
        seged.add(back);
        seged.add(FileButton);
        this.add(seged);

        setOkButton();
        setBackButton();
        setFileButton();
    }

    /**
     * ok gomg init
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
     * back gomb init
     */
    private void setBackButton(){

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Back();
            }
        });
    }

    /**
     * file gomb init
     */
    private void setFileButton(){

        FileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File();
            }
        });
    }

    /**
     * rajzolas
     * @param g - graphics param
     */
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(backGround,0,0,null);

    }

    /**
     * ennek a hatasara menti el a beirt adatokat
     */

    public void Ok(){
        if(Integer.parseInt(nSettler.getText())!=0){
            settlerCount=Integer.parseInt(nSettler.getText());
        }
        if(Integer.parseInt(timer.getText())!=0){
            time=Integer.parseInt(timer.getText());
        }
        if(Integer.parseInt(nAsteroid.getText())!=0){
            asteroidCount=Integer.parseInt(nAsteroid.getText());
        }
        if(Integer.parseInt(nElo.getText())!=0){
            eloCount=Integer.parseInt(nElo.getText());
        }

        Game.getGame().reset();
        for(int i = 0; i < asteroidCount; i++) {
            //model.Game.getGame().addSteppable(new model.Asteroid());
        }
        Controller.getController().backToTheMenu();
    }

    /**
     * adatok mentese nelkel kilep a beallitasokbol
     */
    public void Back(){
        Controller.getController().backToTheMenu();
    }

    /**
     * fileba megnyitas
     */
    public void File(){
        if(loadFile.getText()!=null){
            fName=loadFile.getText();
        }
        try {
            Controller.getController().getSettlers().clear();
            Game.getGame().reset();

            FileInputStream fi = new FileInputStream(new File("myObjects.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            Game seged=(Game) oi.readObject();
            Game.setGame(seged);
            Controller.getController().getSettlers().clear();
            Controller.getController().getSettlers().addAll(Game.getGame().getSettlers());

            loaded = true;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Controller.getController().backToTheMenu();
    }

}
