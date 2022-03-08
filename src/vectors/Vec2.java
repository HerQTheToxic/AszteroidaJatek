package vectors;

import java.io.Serializable;

/**
 * 2 dimenzios vektort reprezental
 */
public class Vec2 implements Serializable {

    /**
     * x komponens
     */
    private double x;

    /**
     * y komponens
     */
    private double y;


    /**
     * konstruktor
     * @param x x komponens
     * @param y y komponens
     */
    public Vec2(double x, double y){
        this.x = x;
        this.y = y;
    }


    /**
     * x komponens getter
     * @return x
     */
    public double getX(){
        return x;
    }

    /**
     * y komponens getter
     * @return y
     */
    public double getY(){
        return y;
    }

    /**
     * hozzaad egy vec2-t a vectorhoz komponensenkpent
     * @param rhs jobboldali vektor
     * @return
     */
    public Vec2 add(Vec2 rhs){
        return new Vec2(x+rhs.getX(),y+rhs.getY());

    }

    /**
     * saklar szorzas
     * @param lambda a lambadval szorozzuk
     * @return a nagyobbitott vektor
     */
    public Vec2 multiply(double lambda){
        return new Vec2(x*lambda,y*lambda);
    }

    /**
     * vektor hossza
     * @return a hossz
     */
    public double getLength(){
        return Math.sqrt(x*x+y*y);

    }


    @Override
    public String toString(){
        return String.format("[%f, %f]", x,y);
    }
}
