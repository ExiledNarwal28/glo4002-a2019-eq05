package ca.ulaval.glo4002.booking.exceptions.shuttles;

import ca.ulaval.glo4002.booking.constants.ExceptionConstants;
import ca.ulaval.glo4002.booking.exceptions.FestivalException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShuttleCategoryNotFoundException extends FestivalException {

	public ShuttleCategoryNotFoundException() {
		super(ExceptionConstants.Shuttle.CATEGORY_NOT_FOUND_ERROR);

		error = ExceptionConstants.Shuttle.CATEGORY_NOT_FOUND_ERROR;
		description = ExceptionConstants.Shuttle.CATEGORY_NOT_FOUND_DESCRIPTION;
		httpStatus = HttpStatus.NOT_FOUND;
	}
}