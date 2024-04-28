package dk.kea.dat3js.hogwarts5.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException() {
    }
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

}
