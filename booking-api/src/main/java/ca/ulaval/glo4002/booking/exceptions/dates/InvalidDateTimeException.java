package ca.ulaval.glo4002.booking.exceptions.dates;

import ca.ulaval.glo4002.booking.constants.ExceptionConstants;
import ca.ulaval.glo4002.booking.exceptions.FestivalException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDateTimeException extends FestivalException {

    public InvalidDateTimeException() {
        super(ExceptionConstants.INVALID_DATE_TIME_ERROR);

        error = ExceptionConstants.INVALID_DATE_TIME_ERROR;
        description = ExceptionConstants.INVALID_DATE_TIME_DESCRIPTION;
        httpStatus = HttpStatus.BAD_REQUEST;
    }
}