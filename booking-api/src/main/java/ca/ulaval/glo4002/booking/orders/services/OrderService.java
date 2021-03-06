package ca.ulaval.glo4002.booking.orders.services;

import ca.ulaval.glo4002.booking.orders.domain.OrderNumber;
import ca.ulaval.glo4002.booking.orders.domain.Order;
import ca.ulaval.glo4002.booking.orders.domain.OrderFactory;
import ca.ulaval.glo4002.booking.orders.domain.OrderRepository;
import ca.ulaval.glo4002.booking.orders.rest.OrderRequest;
import ca.ulaval.glo4002.booking.orders.rest.OrderResponse;
import ca.ulaval.glo4002.booking.orders.rest.mappers.OrderMapper;
import ca.ulaval.glo4002.booking.oxygen.inventory.services.OxygenInventoryService;
import ca.ulaval.glo4002.booking.shuttles.trips.services.TripService;

import javax.inject.Inject;

public class OrderService {

	private final OrderRepository repository;
	private final OrderFactory factory;
	private final OrderMapper mapper;
	private final TripService tripService;
	private final OxygenInventoryService oxygenInventoryService;

	@Inject
	public OrderService(OrderRepository repository, OrderFactory factory, OrderMapper mapper, TripService tripService, OxygenInventoryService oxygenInventoryService) {
		this.repository = repository;
		this.factory = factory;
		this.mapper = mapper;
		this.tripService = tripService;
		this.oxygenInventoryService = oxygenInventoryService;
	}

	public String order(OrderRequest orderRequest) {
		Order order = mapper.fromRequest(orderRequest);
		order = factory.create(order, orderRequest.getVendorCode());

		repository.addOrder(order);
		tripService.orderForPasses(order.getPasses());
		oxygenInventoryService.orderForPasses(order.getPasses(), order.getOrderDate());
		OrderNumber orderNumber = order.getOrderNumber();

		return orderNumber.toString();
	}

	public OrderResponse getByOrderNumber(String requestedOrderNumber) {
		OrderNumber orderNumber = new OrderNumber(requestedOrderNumber);

		Order order = repository.getByOrderNumber(orderNumber);

		return mapper.toResponse(order);
	}
}
