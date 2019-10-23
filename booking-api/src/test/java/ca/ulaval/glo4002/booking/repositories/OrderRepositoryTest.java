package ca.ulaval.glo4002.booking.repositories;

import ca.ulaval.glo4002.booking.dao.OrderDao;
import ca.ulaval.glo4002.booking.domain.Id;
import ca.ulaval.glo4002.booking.domain.orders.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderRepositoryTest {

    private static final Long A_ID = 1L;
    private OrderRepository subject;
    private OrderDao dao;

    @BeforeEach
    public void setUpSubject() {
        dao = mock(OrderDao.class);
        subject = new OrderRepository(dao);
    }

    @Test
    public void getByOrderNumber_shouldReturnCorrectOrder() {
        Id expectedOrderId = new Id(A_ID);
        Order expectedOrder = new Order(expectedOrderId);
        when(dao.get(expectedOrder.getId())).thenReturn(Optional.of(expectedOrder));

        Optional<Order> order = subject.getById(expectedOrder.getId());

        assertTrue(order.isPresent());
        assertEquals(expectedOrderId, order.get().getId());
    }

    @Test
    public void addOrder_shouldSaveOrder() {
        Order order = new Order(mock(Id.class));

        subject.addOrder(order);

        verify(dao).save(order);
    }
}