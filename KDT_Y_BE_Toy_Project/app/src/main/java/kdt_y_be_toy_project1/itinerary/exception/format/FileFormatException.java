package kdt_y_be_toy_project1.itinerary.exception.format;

public abstract class FileFormatException extends RuntimeException{
    protected FileFormatException(String message){
        super(message);
    }
}
