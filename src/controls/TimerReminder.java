package controls;

import java.util.TimerTask;

/**
 * Timer schedule, minden korben uj
 */
public class TimerReminder extends TimerTask{

    /** egy kor alatt eltelo ido (sec) */
    private static final int secondsPerRound= Controller.getController().getSettingsPanel().getTime();

    /** a korben eltelt ido (sec) */
    static int counter = Controller.getController().getGameScene().getTimeElapsed();

    /** megy-e eppen a jatek */
    public static boolean stopped = false;

    /**
     * konstruktor, inicializalja a szamlalot
     */
    public TimerReminder(){
        counter = secondsPerRound;
        stopped = false;
    }

    /**
     * Timer meghivodasakor lekezeli a korben eltelt idot, ha ez az ido letelik uj korbe lep
     */
    @Override
    public void run() {
        if (!stopped) {
            counter--;

            if (counter == 0) {
                Controller.getController().goNext();
                counter = secondsPerRound;
            }

            Controller.getController().getGameScene().getStatusBarPanel().setTime(counter);
        }
    }

    /**
     * megallitja a szamlalot
     */
    public static  void stop(){
        stopped = true;
    }


    public static void reset(){
        counter = secondsPerRound;
        stopped = false;
    }

    /**
     * elinditja a szamlalot
     */
    public static  void resume(){
        stopped = false;
    }

}
