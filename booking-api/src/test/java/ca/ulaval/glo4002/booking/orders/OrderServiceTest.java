package ca.ulaval.glo4002.booking.orders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.ulaval.glo4002.booking.orders.*;
import ca.ulaval.glo4002.booking.oxygen.inventory.OxygenInventoryService;
import ca.ulaval.glo4002.booking.shuttles.trips.TripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.numbers.Number;
import ca.ulaval.glo4002.booking.profits.Money;
import ca.ulaval.glo4002.booking.passes.Pass;
import ca.ulaval.glo4002.booking.passes.bundles.PassBundle;
import ca.ulaval.glo4002.booking.passes.PassCategory;
import ca.ulaval.glo4002.booking.passes.bundles.PassBundleDto;
import ca.ulaval.glo4002.booking.passes.PassCategories;
import ca.ulaval.glo4002.booking.passes.PassOptions;
import ca.ulaval.glo4002.booking.passes.bundles.PassBundleMapper;

class OrderServiceTest {

	private OrderService service;
	private OrderRepository repository;
	private OrderFactory factory;
	private TripService tripService;
	private OxygenInventoryService oxygenInventoryService;

	@BeforeEach
	void setUpService() {
		OrderMapper mapper = new OrderMapper(new PassBundleMapper());
		repository = mock(OrderRepository.class);
		factory = mock(OrderFactory.class);
		tripService = mock(TripService.class);
		oxygenInventoryService = mock(OxygenInventoryService.class);

		service = new OrderService(repository, factory, mapper, tripService, oxygenInventoryService);
	}

	@Test
	void order_shouldAddOrder() {
		String aVendorCode = "aVendorCode";
		Order anOrder = mockOrder(aVendorCode, PassCategories.SUPERNOVA);
		OrderWithPassesAsEventDatesDto orderDto = mockOrderDto(aVendorCode);

		service.order(orderDto);

		verify(repository).addOrder(eq(anOrder));
	}

	@Test
	void order_shouldOrderTripsWithCorrectPassCategory() {
		String aVendorCode = "aVendorCode";
		OrderWithPassesAsEventDatesDto orderDto = mockOrderDto(aVendorCode);
		PassCategories expectedPassCategory = PassCategories.SUPERNOVA;
		mockOrder(aVendorCode, expectedPassCategory);

		service.order(orderDto);

		verify(tripService).orderForPasses(eq(expectedPassCategory), any());
	}

	@Test
	void order_shouldOrderTripsWithCorrectPasses() {
		String aVendorCode = "aVendorCode";
		OrderWithPassesAsEventDatesDto orderDto = mockOrderDto(aVendorCode);
		List<Pass> expectedPasses = new ArrayList<>();
		mockOrder(aVendorCode, PassCategories.SUPERNOVA, expectedPasses);

		service.order(orderDto);

		verify(tripService).orderForPasses(any(), eq(expectedPasses));
	}

	@Test
	void order_shouldOrderOxygenWithCorrectPassCategory() {
		String aVendorCode = "aVendorCode";
		OrderWithPassesAsEventDatesDto orderDto = mockOrderDto(aVendorCode);
		PassCategories expectedPassCategory = PassCategories.SUPERNOVA;
		mockOrder(aVendorCode, expectedPassCategory);

		service.order(orderDto);

		verify(oxygenInventoryService).orderForPasses(eq(expectedPassCategory), any());
	}

	@Test
	void order_shouldOrderOxygenWithCorrectPasses() {
		String aVendorCode = "aVendorCode";
		OrderWithPassesAsEventDatesDto orderDto = mockOrderDto(aVendorCode);
		List<Pass> expectedPasses = new ArrayList<>();
		mockOrder(aVendorCode, PassCategories.SUPERNOVA, expectedPasses);

		service.order(orderDto);

		verify(oxygenInventoryService).orderForPasses(any(), eq(expectedPasses));
	}

	@Test
	void getByOrderNumber_shouldGetOrder() {
		OrderNumber anOrderNumber = new OrderNumber(new Number(1L), "Vendor");
		Pass aPass = mock(Pass.class);
		when(aPass.getPassNumber()).thenReturn(new Number(1L));
		when(aPass.getPrice()).thenReturn(new Money(new BigDecimal(0.0)));
		PassBundle passBundle = new PassBundle(Collections.singletonList(aPass),
				new PassCategory(PassCategories.SUPERNOVA, null), PassOptions.PACKAGE);
		Order order = new Order(anOrderNumber, OrderFactory.START_DATE_TIME, passBundle);
		when(repository.getByOrderNumber(anOrderNumber)).thenReturn(order);

		OrderWithPassesAsPassesDto orderDto = service.getByOrderNumber(anOrderNumber.toString());

		assertEquals(order.getPrice().getValue().doubleValue(), orderDto.getOrderPrice());
	}

	private Order mockOrder(String vendorCode, PassCategories passCategory) {
		return mockOrder(vendorCode, passCategory, new ArrayList<>());
	}

	private Order mockOrder(String vendorCode, PassCategories passCategory, List<Pass> passes) {
		Order order = mock(Order.class);
		when(order.getOrderNumber()).thenReturn(new OrderNumber(new Number(1L), vendorCode));

		PassBundle passBundle = mock(PassBundle.class);
		when(passBundle.getCategory()).thenReturn(passCategory);
		when(passBundle.getPasses()).thenReturn(passes);
		when(order.getPassBundle()).thenReturn(passBundle);
		when(factory.build(any())).thenReturn(order);

		return order;
	}

	private OrderWithPassesAsEventDatesDto mockOrderDto(String vendorCode) {
		return new OrderWithPassesAsEventDatesDto("aOrderDate", vendorCode, mock(PassBundleDto.class));
	}
}