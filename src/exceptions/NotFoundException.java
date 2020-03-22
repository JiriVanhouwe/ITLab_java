package exceptions;

public class NotFoundException extends Exception {
    private static final String MESSAGE = "Class not found by the seeker";
    
    public NotFoundException(){
        super(MESSAGE);
    }
}
