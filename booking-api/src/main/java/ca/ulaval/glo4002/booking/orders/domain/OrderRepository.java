package ca.ulaval.glo4002.booking.orders.domain;

import java.util.List;

public interface OrderRepository {

    Order getByOrderNumber(OrderNumber orderNumber);

    void addOrder(Order order);

    List<Order> findAll();
}
