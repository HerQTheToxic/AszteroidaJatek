package model;

import java.io.Serializable;

/**
 * Az aszteroida es a teleport kozos interfesze
 * szomszedsagokat tarol
 */


public interface Neighbour extends Serializable {
    void moveTo(Being b);
}
