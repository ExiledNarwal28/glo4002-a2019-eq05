package ca.ulaval.glo4002.booking.repositories;

import ca.ulaval.glo4002.booking.dao.OrderDao;
import ca.ulaval.glo4002.booking.domain.Id;
import ca.ulaval.glo4002.booking.domain.orders.Order;

import java.util.Optional;

public class OrderRepository {

    private OrderDao dao;

    public OrderRepository(OrderDao dao) {
        this.dao = dao;
    }

    public Optional<Order> getById(Id id) {
        return dao.get(id);
    }

    public void addOrder(Order order) {
        dao.save(order);
    }
}
