package ca.ulaval.glo4002.booking.orders.domain;

import ca.ulaval.glo4002.booking.festival.domain.FestivalConfiguration;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.booking.orders.rest.OrderRequest;
import ca.ulaval.glo4002.booking.orders.rest.mappers.OrderDateMapper;
import ca.ulaval.glo4002.booking.passes.domain.PassBundleFactory;
import ca.ulaval.glo4002.booking.passes.rest.PassBundleRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrderFactoryTest {

	private OrderFactory factory;
	private OrderDateMapper orderDateMapper;
	private PassBundleFactory passBundleFactory;

	@BeforeEach
	void setUpFactory() {
		OrderIdentifierGenerator numberGenerator = new OrderIdentifierGenerator();
		orderDateMapper = mock(OrderDateMapper.class);
		passBundleFactory = mock(PassBundleFactory.class);

		factory = new OrderFactory(numberGenerator, orderDateMapper, passBundleFactory);
	}

	@Test
	void create_shouldCreateRequestWithCorrectVendorCode() {
		ZonedDateTime anOrderDate = generateOrderDate();
		PassBundleRequest passBundleRequest = mock(PassBundleRequest.class);
		OrderRequest orderRequest = new OrderRequest(anOrderDate.toString(), "VENDOR", passBundleRequest);

		Order order = factory.create(orderRequest);

		assertEquals(orderRequest.getVendorCode(), order.getVendorCode());
	}

	@Test
	void create_shouldCreateOrderDate() {
		ZonedDateTime anOrderDate = generateOrderDate();
		PassBundleRequest passBundleRequest = mock(PassBundleRequest.class);
		OrderRequest orderRequest = new OrderRequest(anOrderDate.toString(), "VENDOR", passBundleRequest);

		factory.create(orderRequest);

		verify(orderDateMapper).fromString(eq(orderRequest.getOrderDate()));
	}

	@Test
	void create_shouldCreatePassBundle() {
		ZonedDateTime anOrderDate = generateOrderDate();
		PassBundleRequest passBundleRequest = mock(PassBundleRequest.class);
		OrderRequest orderRequest = new OrderRequest(anOrderDate.toString(), "VENDOR", passBundleRequest);

		factory.create(orderRequest);

		verify(passBundleFactory).create(eq(passBundleRequest));
	}

	@Test
	void create_shouldThrowInvalidFormatException_whenThereIsNoPass() {
		ZonedDateTime anOrderDate = ZonedDateTime.of(FestivalConfiguration.getDefaultStartOrderDate().getValue().plusDays(1), ZoneId.systemDefault());
		OrderRequest orderRequest = new OrderRequest(anOrderDate.toString(), "TEAM", null);

		assertThrows(InvalidFormatException.class, () -> factory.create(orderRequest));
	}

	private ZonedDateTime generateOrderDate() {
		return ZonedDateTime.of(FestivalConfiguration.getDefaultStartOrderDate().getValue().plusDays(1), ZoneId.systemDefault());
	}
}