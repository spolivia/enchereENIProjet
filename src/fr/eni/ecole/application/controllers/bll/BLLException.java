package fr.eni.ecole.application.controllers.bll;

public class BLLException extends Exception {

    public BLLException() {
        super();
    }
    public BLLException(String message) {
        super(message);
    }
    
    public BLLException(String message, Throwable exception) {
        super(message, exception);
    }
}
