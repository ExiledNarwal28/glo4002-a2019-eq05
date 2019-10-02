package ca.ulaval.glo4002.booking.exceptions.passes;

import ca.ulaval.glo4002.booking.constants.ExceptionConstants;
import ca.ulaval.glo4002.booking.exceptions.ControllerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PassOptionNotFoundException extends ControllerException {

    public PassOptionNotFoundException(String name) {
        super(ExceptionConstants.Pass.OPTION_NOT_FOUND_ERROR);

        error = ExceptionConstants.Pass.OPTION_NOT_FOUND_ERROR;
        description = ExceptionConstants.Pass.OPTION_NOT_FOUND_DESCRIPTION.replace("{name}", name);
        httpStatus = HttpStatus.NOT_FOUND;
    }
}