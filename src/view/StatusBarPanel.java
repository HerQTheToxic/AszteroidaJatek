package view;

import controls.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *  A jatek korenek adatait kiiro panel
 */
public class StatusBarPanel extends JPanel {

    /** betutipus */
    Font font1=new Font("Courier New", Font.BOLD,27);

    /** ESC button */
    JButton escButton = new JButton("esc");

    /** text labelek */
    JLabel timeTextLabel=new JLabel(String.valueOf(Controller.getController().getSettingsPanel().getTime()));
    JLabel roundTextLabel=new JLabel(String.valueOf(Controller.getController().getRound()));
    JLabel settlerTextLabel=new JLabel(Controller.getController().getActualSettler().getId());

    /** szoveg labelek */
    JLabel timeLabel=new JLabel(" Time remaining:");
    JLabel roundLabel=new JLabel("Round:");
    JLabel settlerLabel=new JLabel("Settler:");

    /** ket fo panel */
    JPanel labelPanel;
    JPanel textPanel;

    /** konstruktor */
    public StatusBarPanel(){
        super();
        initComponents();
    }

    /**
     * Statusbar komponensek inicializalasa
     */
    private void initComponents() {

        /** statusbarpanel layoutja */
        setLayout(new BorderLayout());

        /** labelek fontjanak beallitasa */
        timeTextLabel.setFont(font1);
        roundTextLabel.setFont(font1);
        settlerTextLabel.setFont(font1);
        timeLabel.setFont(font1);
        roundLabel.setFont(font1);
        settlerLabel.setFont(font1);

        /** textek panele */
        textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        /** textpanel init */
        textPanel.add(timeTextLabel);
        textPanel.add(roundTextLabel);
        textPanel.add(settlerTextLabel);
        timeTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        roundTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        settlerTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        /** labelek panele */
        labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

        /** labelpanel init */
        labelPanel.add(timeLabel);
        labelPanel.add(roundLabel);
        labelPanel.add(settlerLabel);
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        roundLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        settlerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        setBackButton();
        JPanel escPanel = new JPanel();
        escPanel.add(escButton);

        this.add(labelPanel, BorderLayout.WEST);
        this.add(textPanel, BorderLayout.CENTER);
        this.add(escPanel, BorderLayout.EAST);


        setVisible(true);
    }

    /**
     * beallitja a korben maradt idot
     * @param time - az ido
     */
    public void setTime(int time){
        timeTextLabel.setText(String.valueOf(time));

    }

    /**
     * lekeri a korben maradt idot
     * @return ay ido
     */
    public int getTime(){
        return Integer.parseInt(timeTextLabel.getText());
    }

    /**
     * beallitja a lement korok szamat
     */
    public void setRound(){
        roundTextLabel.setText(String.valueOf(Controller.getController().getRound()));
    }

    /**
     * beallitja az aktualis telepest aki a korben lep
     */
    public void setSettler(){
        settlerTextLabel.setText(String.valueOf(Controller.getController().getActualSettler().getId()));
    }

    private void setBackButton(){

        escButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Controller.getController().getGameScene().interactWithEscapePanel();
            }
        });
    }

}
