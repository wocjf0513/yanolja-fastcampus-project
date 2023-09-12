package kdt_y_be_toy_project1.itinerary.exception.service;

public abstract class ServiceException extends RuntimeException{
    protected ServiceException(String message){
        super(message);
    }
}
