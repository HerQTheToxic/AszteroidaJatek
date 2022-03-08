package exceptions;

;

public class TooManyAsteroidsException extends Exception {

    /**
     * Exception -t tob a kapott szoveggel
     * @param msg - a kapott szoveg
     * @param t - null
     */
    public TooManyAsteroidsException(String msg, Throwable t) {
        super(msg, t);
    }

}
