package ca.ulaval.glo4002.booking.mappers;

import ca.ulaval.glo4002.booking.domain.money.Money;
import ca.ulaval.glo4002.booking.domain.orders.OrderNumber;
import ca.ulaval.glo4002.booking.domain.passes.PassList;
import ca.ulaval.glo4002.booking.domain.orders.Order;
import ca.ulaval.glo4002.booking.dto.OrderWithPassesAsPassesDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderMapperTest {

    private OrderMapper subject;
    private PassListMapper passListMapper;
    private OrderNumber orderNumber;
    private Order order;

    @BeforeEach
    void setUpSubject() {
        passListMapper = mock(PassListMapper.class);
        subject = new OrderMapper(passListMapper);
    }

    @BeforeEach
    void setUpOrder() {
        orderNumber = mock(OrderNumber.class);
        order = mock(Order.class);
        when(orderNumber.toString()).thenReturn("expectedOrderNumber");
        when(order.getOrderNumber()).thenReturn(orderNumber);
        when(order.getPrice()).thenReturn(new Money(new BigDecimal(500)));
        when(order.getPassList()).thenReturn(mock(PassList.class));
    }

    @Test
    void toDto_shouldBuildDtoWithCorrectOrderNumber() {
        OrderWithPassesAsPassesDto orderDto = subject.toDto(order);

        assertEquals(orderNumber.toString(), orderDto.getOrderNumber());
    }

    @Test
    void toDto_shouldBuildDtoWithCorrectOrderPrice() {
        OrderWithPassesAsPassesDto orderDto = subject.toDto(order);

        assertEquals(order.getPrice().getValue().doubleValue(), orderDto.getOrderPrice());
    }

    @Test
    void toDto_shouldCallPassListParser() {
        subject.toDto(order);

        verify(passListMapper).toDto(any());
    }
}