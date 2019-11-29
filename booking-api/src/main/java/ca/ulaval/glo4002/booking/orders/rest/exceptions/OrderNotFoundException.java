package ca.ulaval.glo4002.booking.orders.rest.exceptions;

import ca.ulaval.glo4002.booking.errors.BookingException;
import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends BookingException {

    public static final String MESSAGE = "ORDER_NOT_FOUND";
    public static final String DESCRIPTION = "Order with number {orderNumber} not found";

    public OrderNotFoundException(String orderNumber) {
        super(MESSAGE);

        description = DESCRIPTION.replace("{orderNumber}", orderNumber);
        status = HttpStatus.NOT_FOUND;
    }
}