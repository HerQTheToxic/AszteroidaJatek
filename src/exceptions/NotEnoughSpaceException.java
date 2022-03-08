package exceptions;

;

public class NotEnoughSpaceException extends Exception{

    public NotEnoughSpaceException(String msg, Throwable t){
        super(msg, t);
    }

}
