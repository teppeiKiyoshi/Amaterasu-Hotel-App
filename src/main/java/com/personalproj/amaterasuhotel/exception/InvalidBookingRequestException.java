package com.personalproj.amaterasuhotel.exception;

public class InvalidBookingRequestException extends RuntimeException {

    public InvalidBookingRequestException(String message){
        super(message);
    }
}
