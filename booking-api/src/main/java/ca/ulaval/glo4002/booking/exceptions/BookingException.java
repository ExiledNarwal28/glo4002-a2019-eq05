package ca.ulaval.glo4002.booking.exceptions;

import ca.ulaval.glo4002.booking.dto.ErrorDto;
import org.springframework.http.HttpStatus;

public abstract class BookingException extends RuntimeException {

    protected String description;
    protected HttpStatus status;

    protected BookingException(String message) {
        super(message);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ErrorDto toErrorDto() {
        return new ErrorDto(getMessage(), description);
    }
}
