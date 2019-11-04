package ca.ulaval.glo4002.booking.repositories;

import ca.ulaval.glo4002.booking.domain.Number;
import ca.ulaval.glo4002.booking.domain.orders.Order;
import ca.ulaval.glo4002.booking.domain.orders.OrderNumber;
import ca.ulaval.glo4002.booking.domain.passes.PassBundle;
import ca.ulaval.glo4002.booking.exceptions.OrderNotFoundException;
import ca.ulaval.glo4002.booking.factories.OrderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InMemoryOrderRepositoryTest {

    private OrderRepository repository;

    @BeforeEach
    void setUpRepository() {
        repository = new InMemoryOrderRepository();
    }

    @Test
    void getOrderNumber_shouldThrowOrderNotFoundException_whenThereIsNoOrder() {
        OrderNumber aNonExistentOrderNumber = new OrderNumber(new Number(1L), "VENDOR");

        assertThrows(OrderNotFoundException.class, () -> repository.getByOrderNumber(aNonExistentOrderNumber));
    }

    @Test
    void getOrderNumber_shouldThrowOrderNotFoundException_whenOrderDoesNotExist() {
        OrderNumber aNonExistentOrderNumber = new OrderNumber(new Number(1L), "VENDOR");
        OrderNumber aOrderNumber = new OrderNumber(new Number(2L), "VENDOR");
        LocalDateTime aOrderDate = OrderFactory.START_DATE_TIME.plusDays(1);
        PassBundle aPassBundle = mock(PassBundle.class);
        Order aOrder = new Order(aOrderNumber, aOrderDate, aPassBundle);
        repository.addOrder(aOrder);

        assertThrows(OrderNotFoundException.class, () -> repository.getByOrderNumber(aNonExistentOrderNumber));
    }

    @Test
    void getByOrderNumber_shouldReturnOrder() {
        OrderNumber aOrderNumber = new OrderNumber(new Number(1L), "VENDOR");
        LocalDateTime aOrderDate = OrderFactory.START_DATE_TIME.plusDays(1);
        PassBundle aPassBundle = mock(PassBundle.class);
        Order aOrder = new Order(aOrderNumber, aOrderDate, aPassBundle);
        repository.addOrder(aOrder);

        Optional<Order> foundOrder = repository.getByOrderNumber(aOrderNumber);

        assertTrue(foundOrder.isPresent());
        assertEquals(aOrderNumber, foundOrder.get().getOrderNumber());
    }

    @Test
    void getByOrderNumber_shouldReturnOrders_whenThereAreMultipleOrders() {
        OrderNumber aOrderNumber = new OrderNumber(new Number(1L), "VENDOR");
        OrderNumber anotherOrderNumber = new OrderNumber(new Number(2L), "VENDOR");
        LocalDateTime aOrderDate = OrderFactory.START_DATE_TIME.plusDays(1);
        PassBundle aPassBundle = mock(PassBundle.class);
        Order aOrder = new Order(aOrderNumber, aOrderDate, aPassBundle);
        Order anotherOrder = new Order(anotherOrderNumber, aOrderDate, aPassBundle);
        repository.addOrder(aOrder);
        repository.addOrder(anotherOrder);

        Optional<Order> foundOrder = repository.getByOrderNumber(aOrderNumber);
        Optional<Order> otherFoundOrder = repository.getByOrderNumber(anotherOrderNumber);

        assertTrue(foundOrder.isPresent());
        assertTrue(otherFoundOrder.isPresent());
        assertEquals(aOrderNumber, foundOrder.get().getOrderNumber());
        assertEquals(anotherOrderNumber, otherFoundOrder.get().getOrderNumber());
    }
}