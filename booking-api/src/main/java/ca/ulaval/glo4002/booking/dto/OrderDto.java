package ca.ulaval.glo4002.booking.dto;

import java.time.LocalDateTime;

public class OrderDto implements Dto {

    public Long orderNumber;
    public LocalDateTime orderDate;
    public String vendorCode;
    public PassesDto passes;
}
