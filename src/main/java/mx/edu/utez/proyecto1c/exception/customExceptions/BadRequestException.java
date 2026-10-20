package mx.edu.utez.proyecto1c.exception.customExceptions;

public class BadRequestException extends  RuntimeException {

    public BadRequestException(String message){
        super(message);
    }

}
