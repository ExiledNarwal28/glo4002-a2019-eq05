package ca.ulaval.glo4002.booking.services;

import ca.ulaval.glo4002.booking.domain.orders.Order;
import ca.ulaval.glo4002.booking.domain.orders.OrderNumber;
import ca.ulaval.glo4002.booking.dto.OrderWithPassesAsEventDatesDto;
import ca.ulaval.glo4002.booking.dto.OrderWithPassesAsPassesDto;
import ca.ulaval.glo4002.booking.enums.PassCategories;
import ca.ulaval.glo4002.booking.factories.OrderFactory;
import ca.ulaval.glo4002.booking.mappers.OrderMapper;
import ca.ulaval.glo4002.booking.repositories.OrderRepository;

import javax.inject.Inject;

public class OrderService {

    private final OrderRepository repository;
    private final OrderFactory factory;
    private final OrderMapper mapper;
    private final TripService tripService;

    @Inject
    public OrderService(OrderRepository repository, OrderFactory factory, OrderMapper mapper, TripService tripService) {
        this.repository = repository;
        this.factory = factory;
        this.mapper = mapper;
        this.tripService = tripService;
    }

    public String order(OrderWithPassesAsEventDatesDto orderDto) {
        Order order = factory.build(orderDto);

        repository.addOrder(order);
        tripService.orderAll(order.getPassBundle().getCategory(), order.getPasses());

        return order.getOrderNumber().toString();
    }

    public OrderWithPassesAsPassesDto getByOrderNumber(String requestedOrderNumber) {
        OrderNumber orderNumber = new OrderNumber(requestedOrderNumber);

        Order order = repository.getByOrderNumber(orderNumber).get(); // TODO : Is using get a good idea?

        return mapper.toDto(order);
    }
}
