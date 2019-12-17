package ca.ulaval.glo4002.booking.orders.domain;

import ca.ulaval.glo4002.booking.festival.domain.FestivalConfiguration;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.booking.orders.rest.exceptions.InvalidOrderDateException;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

// TODO : This should be a mapper
public class OrderDateFactory {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    private final FestivalConfiguration festivalConfiguration;

    @Inject
    public OrderDateFactory(FestivalConfiguration festivalConfiguration) {
        this.festivalConfiguration = festivalConfiguration;
    }

    public OrderDate create(String orderDate) {
        OrderDate parsedOrderDate = parse(orderDate);

        validateOrderDate(parsedOrderDate);

        return parsedOrderDate;
    }

    // Why in world are those two separate concepts
    public OrderDate parse(String orderDate) {
        LocalDateTime orderDateValue;

        try {
            orderDateValue = ZonedDateTime.parse(orderDate, DATE_TIME_FORMATTER).toLocalDateTime();
        } catch (Exception exception) {
            throw new InvalidFormatException();
        }

        return new OrderDate(orderDateValue);
    }

    private void validateOrderDate(OrderDate orderDate) {
        OrderDate startOrderDate = festivalConfiguration.getStartOrderDate();
        OrderDate endOrderDate = festivalConfiguration.getEndOrderDate();

        if (!orderDate.isBetweenOrEquals(startOrderDate, endOrderDate)) {
            throw new InvalidOrderDateException();
        }
    }
}
