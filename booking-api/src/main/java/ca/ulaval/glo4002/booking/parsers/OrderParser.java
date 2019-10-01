package ca.ulaval.glo4002.booking.parsers;

import ca.ulaval.glo4002.booking.builders.VendorBuilder;
import ca.ulaval.glo4002.booking.domainObjects.orders.Order;
import ca.ulaval.glo4002.booking.domainObjects.vendors.Vendor;
import ca.ulaval.glo4002.booking.dto.OrderDto;
import ca.ulaval.glo4002.booking.entities.OrderEntity;
import ca.ulaval.glo4002.booking.exceptions.InvalidDateTimeException;
import ca.ulaval.glo4002.booking.exceptions.orders.OrderDtoInvalidException;
import ca.ulaval.glo4002.booking.util.FestivalDateUtil;

import java.time.LocalDateTime;

public class OrderParser implements EntityParser<Order, OrderEntity>, DtoParser<Order, OrderDto> {

    private VendorBuilder vendorBuilder = new VendorBuilder();

    @Override
    public Order parseDto(OrderDto dto) {
        Vendor vendor = vendorBuilder.buildByCode(dto.vendorCode);

        LocalDateTime orderDate = parseOrderDate(dto.orderDate);
        validateOrderDate(orderDate);

        return new Order(dto.orderNumber, orderDate, vendor);
    }

    @Override
    public Order parseEntity(OrderEntity entity) {
        Vendor vendor = vendorBuilder.buildById(entity.vendorId);

        return new Order(entity.id, entity.orderDate, vendor);
    }

    @Override
    public OrderDto toDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.orderNumber = order.getId();
        dto.orderDate = FestivalDateUtil.toZonedDateTimeString(order.getOrderDate());
        dto.vendorCode = order.getVendor().getCode();
        // TODO : dto.passes

        return dto;
    }

    @Override
    public OrderEntity toEntity(Order order) {
        return new OrderEntity(
                order.getId(),
                order.getOrderDate(),
                order.getVendor().getId()
        );
    }

    private LocalDateTime parseOrderDate(String orderDate) {
        try {
            return FestivalDateUtil.toLocalDateTime(orderDate);
        } catch(InvalidDateTimeException exception) {
            throw new OrderDtoInvalidException();
        }
    }

    private void validateOrderDate(LocalDateTime orderDate){
        if (FestivalDateUtil.isOutsideOrderDates(orderDate)){
            throw new OrderDtoInvalidException();
        }
    }
}
