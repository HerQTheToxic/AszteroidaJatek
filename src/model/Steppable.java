package model;

import view.Drawable;

import java.io.Serializable;

/**
 * Leptetheto objektumok a jatekban
 */

public interface Steppable extends Serializable {
    /**
     * Egy lepesbeli teendok lekezelese
     */
    void step();

    /**
     * visszaadja a steppable steppeltetesi prioritasat
     * @return a prioritas
     */
    int getPriority();

    Drawable getDrawable();

}
