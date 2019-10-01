package ca.ulaval.glo4002.booking.endToEnd;

import ca.ulaval.glo4002.booking.dto.OrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

// TODO : Solve test class (Pass, Shuttle and OxygenTank will need to be completed)
public class OrderEndToEndTest {

    private OrderEndToEndContext context;

    @BeforeEach
    public void setUp() {
        context = new OrderEndToEndContext();
    }

    @Test
    public void getOrderController_shouldReturnHttpErrorPageNotFound_whenOrderNumberIsNonExistent() {
        context.setUp();

        ResponseEntity response = context.orderController.getOrderById(context.anOrderId);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatusCodeValue());
    }

    @Test
    public void getOrderController_shouldReturnHttpErrorPageNotFound_whenOrderNumberIsInvalid() {
        context.setUp();

        ResponseEntity response = context.orderController.getOrderById(OrderEndToEndContext.AN_INVALID_ORDER_ID);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatusCodeValue());
    }

    @Test
    public void getOrderController_shouldReturnCorrectOrderDto_whenOrderNumberIsExistent() {
        context.setUp().withAnOrder();

        ResponseEntity<OrderDto> response = (ResponseEntity<OrderDto>) context.orderController.getOrderById(context.anOrderId);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatusCodeValue());
        assertEquals(context.anOrderId, response.getBody().orderNumber);
    }

    @Test
    public void getOrderController_shouldReturnCorrectOrderDto_whenManyOrderNumberAreExistent() {
        context.setUp().withAnOrder().withAnotherOrder();

        ResponseEntity<OrderDto> aResponse = (ResponseEntity<OrderDto>) context.orderController.getOrderById(context.anOrderId);
        ResponseEntity<OrderDto> anotherResponse = (ResponseEntity<OrderDto>) context.orderController.getOrderById(context.anotherOrderId);

        assertEquals(Response.Status.OK.getStatusCode(), aResponse.getStatusCodeValue());
        assertEquals(Response.Status.OK.getStatusCode(), anotherResponse.getStatusCodeValue());
        assertEquals(context.anOrderId, aResponse.getBody().orderNumber);
        assertEquals(context.anotherOrderId, anotherResponse.getBody().orderNumber);
    }

    @Test
    public void postOrderController_shouldReturnHttpBadRequest_whenOrderIsInvalid() {
        OrderDto orderDto = context.orderParser.toDto(context.orderParser.parseEntity(context.anOrder));
        orderDto.orderNumber = OrderEndToEndContext.AN_INVALID_ORDER_ID;
        context.setUp();

        ResponseEntity<OrderDto> response = (ResponseEntity<OrderDto>) context.orderController.addOrder(orderDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatusCodeValue());
    }

    @Test
    public void postOrderController_shouldReturnHttpBadRequest_whenOrderAlreadyExists() {
        OrderDto orderDto = context.orderParser.toDto(context.orderParser.parseEntity(context.anOrder));
        context.setUp().withAnOrder();
        orderDto.orderNumber = context.anOrderId;

        ResponseEntity<OrderDto> response = (ResponseEntity<OrderDto>) context.orderController.addOrder(orderDto);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatusCodeValue());
    }

    @Test
    public void postOrderController_shouldReturnHttpCreated_whenOrderIsValid() {
        OrderDto orderDto = context.orderParser.toDto(context.orderParser.parseEntity(context.anOrder));
        context.setUp();

        ResponseEntity<OrderDto> response = (ResponseEntity<OrderDto>) context.orderController.addOrder(orderDto);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    public void postOrderController_shouldReturnCreated_whenManyOrdersAreValid() {
        OrderDto anOrderDto = context.orderParser.toDto(context.orderParser.parseEntity(context.anOrder));
        OrderDto anotherOrderDto = context.orderParser.toDto(context.orderParser.parseEntity(context.anotherOrder));
        context.setUp();

        ResponseEntity<OrderDto> aResponse = (ResponseEntity<OrderDto>) context.orderController.addOrder(anOrderDto);
        ResponseEntity<OrderDto> anotherResponse = (ResponseEntity<OrderDto>) context.orderController.addOrder(anotherOrderDto);

        assertEquals(Response.Status.CREATED.getStatusCode(), aResponse.getStatusCodeValue());
        assertEquals(Response.Status.CREATED.getStatusCode(), anotherResponse.getStatusCodeValue());
        assertNotNull(aResponse.getBody());
        assertNotNull(anotherResponse.getBody());
    }

    @Test
    public void postOrderController_shouldReturnUniqueOrderNumbers() {
        OrderDto anOrderDto = context.orderParser.toDto(context.orderParser.parseEntity(context.anOrder));
        OrderDto anotherOrderDto = context.orderParser.toDto(context.orderParser.parseEntity(context.anotherOrder));
        context.setUp();

        ResponseEntity<OrderDto> aResponse = (ResponseEntity<OrderDto>) context.orderController.addOrder(anOrderDto);
        ResponseEntity<OrderDto> anotherResponse = (ResponseEntity<OrderDto>) context.orderController.addOrder(anotherOrderDto);

        assertEquals(Response.Status.CREATED.getStatusCode(), aResponse.getStatusCodeValue());
        assertEquals(Response.Status.CREATED.getStatusCode(), anotherResponse.getStatusCodeValue());
        assertNotNull(aResponse.getBody().orderNumber);
        assertNotNull(anotherResponse.getBody().orderNumber);
        assertNotEquals(aResponse.getBody().orderNumber, anotherResponse.getBody().orderNumber);
    }
}
