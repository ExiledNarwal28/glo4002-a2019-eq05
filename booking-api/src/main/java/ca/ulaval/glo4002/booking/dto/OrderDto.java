package ca.ulaval.glo4002.booking.dto;

import java.util.List;

public class OrderDto implements Dto {

    public String orderDate;
    public String vendorCode;
    public List<PassDto> passes;
}
