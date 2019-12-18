package ca.ulaval.glo4002.booking.orders.domain;

import ca.ulaval.glo4002.booking.orders.domain.OrderNumber;
import ca.ulaval.glo4002.booking.orders.domain.Order;

import java.util.List;

public interface OrderRepository {

    Order getByOrderNumber(OrderNumber orderNumber);

    void addOrder(Order order);

    List<Order> findAll();
}