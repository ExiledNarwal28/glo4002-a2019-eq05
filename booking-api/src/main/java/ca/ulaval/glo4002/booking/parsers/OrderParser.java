package ca.ulaval.glo4002.booking.parsers;

import ca.ulaval.glo4002.booking.domain.orders.Order;
import ca.ulaval.glo4002.booking.domain.orders.OrderDate;
import ca.ulaval.glo4002.booking.domain.passes.PassList;
import ca.ulaval.glo4002.booking.dto.OrderWithPassesAsEventDatesDto;
import ca.ulaval.glo4002.booking.dto.OrderWithPassesAsPassesDto;
import ca.ulaval.glo4002.booking.exceptions.orders.InvalidOrderFormatException;

import java.util.ArrayList;

// TODO : ACP : Should DTOs handle their creation with an Order?

public class OrderParser {

    public PassesParser passesParser;

    public OrderParser(PassesParser passesParser) {
        this.passesParser = passesParser;
    }

    // TODO : Add passes to DTO
    public OrderWithPassesAsPassesDto toDto(Order order) {
        return new OrderWithPassesAsPassesDto(
                order.getPrice().getValue().doubleValue(),
                new ArrayList<>()
        );
    }

    // TODO : Add passes to Order
    public Order parseDto(OrderWithPassesAsEventDatesDto orderDto) {
        if (orderDto.getPasses() == null) {
            throw new InvalidOrderFormatException();
        }

        OrderDate orderDate = new OrderDate(orderDto.getOrderDate());
        PassList passList = passesParser.parsePasses(orderDto.getPasses());

        return new Order(orderDto.getVendorCode(), orderDate, passList);
    }
}
