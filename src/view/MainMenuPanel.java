package view;

import controls.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Fo panel a jatek elindulasakor
 */
public class MainMenuPanel extends JPanel {

    /**
     * hatter
     */
    transient private BufferedImage backGround;

    /**
     * gombok ikonjai
     */
    Icon startIcon = new ImageIcon(System.getProperty("user.dir")+"\\textures\\button_start.png");
    Icon helpIcon = new ImageIcon(System.getProperty("user.dir")+"\\textures\\button_help.png");
    Icon exitIcon = new ImageIcon(System.getProperty("user.dir")+"\\textures\\button_exit.png");
    Icon settingsIcon = new ImageIcon(System.getProperty("user.dir")+"\\textures\\button_settings.png");

    /**
     * mainmenu gombjai
     */
    JButton startButton = new JButton(startIcon);
    JButton helpButton = new JButton(helpIcon);
    JButton exitButton = new JButton(exitIcon);
    JButton settingsButton = new JButton(settingsIcon);

    /** konstruktor */
    public MainMenuPanel() {

        /** hatter betoltese */
        try {
            backGround = ImageIO.read(new File(System.getProperty("user.dir") +"\\textures\\menu_bg.png"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        initComponents();
        this.setSize(Controller.getWindowWidth(), Controller.getWindowHeight());
    }

    /**
     * MainMenu init
     */
    private void initComponents() {

        /** layout beallitasa*/
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        /** gombok beallitasa */
        setStartButton();
        setHelpButton();
        setExitButton();
        setSettingsButton();
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        helpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        /** start gomb hozzaadas */
        add(Box.createVerticalStrut(500));
        startButton.setBorder(BorderFactory.createEmptyBorder());
        this.add(startButton);
        add(Box.createVerticalStrut(20));

        /** help bomb hozzaadas */
        helpButton.setBorder(BorderFactory.createEmptyBorder());
        this.add(helpButton);
        add(Box.createVerticalStrut(20));

        /** settings gomb hozzaadas */
        settingsButton.setBorder(BorderFactory.createEmptyBorder());
        this.add(settingsButton);
        add(Box.createVerticalStrut(20));

        /** exit gomb hozzaadas */
        exitButton.setBorder(BorderFactory.createEmptyBorder());
        this.add(exitButton);
    }

    /**
     * start button beallitas
     */
    private void setStartButton() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });
    }

    /**
     * help button beallitas
     */
    private void setHelpButton() {
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                help();
            }
        });
    }

    /**
     * exit button beallitas
     */
    private void setExitButton() {
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });

    }

    /**
     * settings button beallitas
     */
    private void setSettingsButton() {
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settings();
            }
        });
    }

    /**
     * hatter kirajzolasa
     *
     * @param g - grafika
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backGround, 0, 0, null);
    }

    /**
     * jatek elindul
     */
    public void start() {
        Controller.getController().gameStart();
    }

    /**
     * help panel lekezelese
     */
    public void help() {
        Controller.getController().openHelp();
    }

    /**
     * settings panel lekezelese
     */
    public void settings() {
        Controller.getController().openSettings();
    }

    /**
     * kilepes a programbol
     */
    public void exit() {
        System.exit(0);
    }
}
