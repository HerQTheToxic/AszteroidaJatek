package model;

import model.Being;

import java.io.Serializable;

/**
 * Munkas alaposztaly
 */
public abstract class Worker extends Being implements Serializable {

    /**
     * Munkas vekonyitja az aszteroidaja kerget egy egyseggel, ha van meg kerge
     */
    public void drill(boolean tooClose){
        if(myAst.getCrustThickness() < 1){
            return;
        }
        myAst.reduceCrust();
    }


}
