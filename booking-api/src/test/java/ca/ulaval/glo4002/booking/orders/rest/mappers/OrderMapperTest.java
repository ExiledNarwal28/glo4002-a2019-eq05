package ca.ulaval.glo4002.booking.orders.rest.mappers;

import ca.ulaval.glo4002.booking.festival.domain.FestivalConfiguration;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.booking.orders.domain.Order;
import ca.ulaval.glo4002.booking.orders.domain.OrderDate;
import ca.ulaval.glo4002.booking.orders.domain.OrderDateFactory;
import ca.ulaval.glo4002.booking.orders.domain.OrderRefactored;
import ca.ulaval.glo4002.booking.orders.rest.OrderRefactoredRequest;
import ca.ulaval.glo4002.booking.orders.rest.OrderResponse;
import ca.ulaval.glo4002.booking.passes.domain.PassBundle;
import ca.ulaval.glo4002.booking.passes.domain.PassRefactored;
import ca.ulaval.glo4002.booking.passes.rest.PassRefactoredRequest;
import ca.ulaval.glo4002.booking.passes.rest.mappers.PassBundleMapper;
import ca.ulaval.glo4002.booking.passes.rest.mappers.PassRefactoredMapper;
import ca.ulaval.glo4002.booking.profits.domain.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrderMapperTest {

	private OrderMapper orderMapper;
	private PassBundleMapper passBundleMapper;
	private OrderDateFactory orderDateFactory;
	private PassRefactoredMapper passMapper;
	private Order order;

	@BeforeEach
	void setUpMapper() {
		passBundleMapper = mock(PassBundleMapper.class);
		orderDateFactory = mock(OrderDateFactory.class);
		passMapper = mock(PassRefactoredMapper.class);

		orderMapper = new OrderMapper(passBundleMapper, orderDateFactory, passMapper);
	}

	@BeforeEach
	void setUpOrder() {
		order = mock(Order.class);
		when(order.getPrice()).thenReturn(new Money(BigDecimal.valueOf(500)));
		when(order.getPassBundle()).thenReturn(mock(PassBundle.class));
	}

	@Test
	void fromRequest_shouldThrowInvalidFormatException_whenThereIsNoPass() {
		ZonedDateTime anOrderDate = generateOrderDate();
		OrderRefactoredRequest orderRequest = new OrderRefactoredRequest(anOrderDate.toString(), "VENDOR", null);

		assertThrows(InvalidFormatException.class, () -> orderMapper.fromRequest(orderRequest));
	}

	@Test
	void fromRequest_shouldSetOrderDate() {
		ZonedDateTime anOrderDate = generateOrderDate();
		OrderDate expectedOrderDate = mock(OrderDate.class);
		when(orderDateFactory.create(eq(anOrderDate.toString()))).thenReturn(expectedOrderDate);
		PassRefactoredRequest passRequest = mock(PassRefactoredRequest.class);
		OrderRefactoredRequest orderRequest = new OrderRefactoredRequest(anOrderDate.toString(), "VENDOR", passRequest);

		OrderRefactored order = orderMapper.fromRequest(orderRequest);

		assertEquals(expectedOrderDate, order.getOrderDate());
	}

	@Test
	void fromRequest_shouldSetPass() {
		ZonedDateTime anOrderDate = generateOrderDate();
		PassRefactoredRequest passRequest = mock(PassRefactoredRequest.class);
		PassRefactored expectedPass = mock(PassRefactored.class);
		when(passMapper.fromRequest(eq(passRequest))).thenReturn(expectedPass);
		OrderRefactoredRequest orderRequest = new OrderRefactoredRequest(anOrderDate.toString(), "VENDOR", passRequest);

		OrderRefactored order = orderMapper.fromRequest(orderRequest);

		assertEquals(expectedPass, order.getPass());
	}

	@Test
	public void toResponse_shouldBuildResponseOrderPriceWithTwoDigits_whenOrderPriceHasMoreThanTwoDigits() {
		BigDecimal orderPrice = BigDecimal.valueOf(123.123);
		Money price = new Money(orderPrice);
		when(order.getPrice()).thenReturn(price);
		String expectedOrderPrice = "123.12";

		OrderResponse orderResponse = orderMapper.toResponse(order);

		assertEquals(expectedOrderPrice, String.valueOf(orderResponse.getOrderPrice()));
	}

	@Test
	public void toResponse_shouldBuildResponseOrderPriceWithTwoDigits_whenOrderPriceHasLesThanTwoDigits() {
		BigDecimal orderPrice = BigDecimal.valueOf(123.1);
		Money price = new Money(orderPrice);
		when(order.getPrice()).thenReturn(price);
		String expectedOrderPrice = "123.1";

		OrderResponse orderResponse = orderMapper.toResponse(order);

		assertEquals(expectedOrderPrice, String.valueOf(orderResponse.getOrderPrice()));
	}

	@Test
	void toResponse_shouldCreatePassBundle() {
		orderMapper.toResponse(order);

		verify(passBundleMapper).toResponse(any());
	}

	private ZonedDateTime generateOrderDate() {
		return ZonedDateTime.of(FestivalConfiguration.getDefaultStartOrderDate().getValue().plusDays(1), ZoneId.systemDefault());
	}
}