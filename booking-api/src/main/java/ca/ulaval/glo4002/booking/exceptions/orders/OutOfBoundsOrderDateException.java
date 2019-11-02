package ca.ulaval.glo4002.booking.exceptions.orders;

import ca.ulaval.glo4002.booking.exceptions.BookingException;
import ca.ulaval.glo4002.booking.factories.OrderFactory;
import org.springframework.http.HttpStatus;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

// TODO : ACP : Rename to InvalidOrderDateException
public class OutOfBoundsOrderDateException extends BookingException {

    private static final DateTimeFormatter MESSAGE_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMMM d y").withLocale(Locale.ENGLISH);
    public static final String MESSAGE = "INVALID_ORDER_DATE";
    public static final String DESCRIPTION = "Order date should be between" +
            OrderFactory.START_DATE_TIME.format(MESSAGE_DATE_TIME_FORMATTER) +
            " and " +
            OrderFactory.END_DATE_TIME.format(MESSAGE_DATE_TIME_FORMATTER);

    public OutOfBoundsOrderDateException() {
        super(MESSAGE);

        description = DESCRIPTION;
        status = HttpStatus.BAD_REQUEST;
    }
}
