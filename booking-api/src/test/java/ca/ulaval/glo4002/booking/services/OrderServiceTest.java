package ca.ulaval.glo4002.booking.services;

import ca.ulaval.glo4002.booking.domain.Number;
import ca.ulaval.glo4002.booking.domain.orders.Order;
import ca.ulaval.glo4002.booking.domain.orders.OrderNumber;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassList;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;
import ca.ulaval.glo4002.booking.domain.passes.pricecalculationstrategy.NoDiscountPriceCalculationStrategy;
import ca.ulaval.glo4002.booking.dto.OrderWithPassesAsEventDatesDto;
import ca.ulaval.glo4002.booking.dto.OrderWithPassesAsPassesDto;
import ca.ulaval.glo4002.booking.dto.PassListDto;
import ca.ulaval.glo4002.booking.enums.PassCategories;
import ca.ulaval.glo4002.booking.enums.PassOptions;
import ca.ulaval.glo4002.booking.factories.OrderFactory;
import ca.ulaval.glo4002.booking.mappers.OrderMapper;
import ca.ulaval.glo4002.booking.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    private OrderService subject;
    private OrderRepository repository;
    private OrderFactory factory;

    @BeforeEach
    void setUpSubject() {
        repository = mock(OrderRepository.class);
        factory = mock(OrderFactory.class);
        OrderMapper mapper = mock(OrderMapper.class);

        subject = new OrderService(repository, factory, mapper);
    }

    @Test
    void order_shouldAddOrder() {
        String aVendorCode = "aVendorCode";
        OrderWithPassesAsEventDatesDto orderDto = new OrderWithPassesAsEventDatesDto(
                "aOrderDate",
                aVendorCode,
                mock(PassListDto.class)
        );
        Order order = mock(Order.class);
        when(order.getOrderNumber()).thenReturn(new OrderNumber(new Number(1L), aVendorCode));
        when(factory.buildWithDto(any())).thenReturn(order);

        subject.order(orderDto);

        verify(repository).addOrder(any());
    }

    @Test
    void getByOrderNumber_shouldGetOrder() {
        OrderNumber aOrderNumber = new OrderNumber(new Number(1L), "Vendor");
        PassList passList = new PassList(
                Collections.singletonList(mock(Pass.class)),
                new PassCategory(PassCategories.SUPERNOVA.toString()),
                new PassOption(PassOptions.PACKAGE.toString()),
                new NoDiscountPriceCalculationStrategy()
        );
        Order order = new Order(aOrderNumber, OrderFactory.START_DATE_TIME, passList);
        when(repository.getByOrderNumber(aOrderNumber)).thenReturn(Optional.of(order));

        OrderWithPassesAsPassesDto orderDto = subject.getByOrderNumber(aOrderNumber.toString());

        assertEquals(order.getPrice().getValue().doubleValue(), orderDto.getOrderPrice());
    }

    @Test
    void getByOrderNumber_shouldGetOrder_whenOrderIsAdded() {
        // TODO
    }
}