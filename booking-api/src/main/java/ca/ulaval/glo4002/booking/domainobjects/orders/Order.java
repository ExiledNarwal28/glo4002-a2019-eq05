package ca.ulaval.glo4002.booking.domainobjects.orders;

import ca.ulaval.glo4002.booking.domainobjects.vendors.Vendor;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
	
	protected Long id;
    private LocalDateTime orderDate;
    private Vendor vendor;
    private List<? extends OrderItem> orderItems;

    public Order(Long id, LocalDateTime orderDate, Vendor vendor, List<? extends OrderItem> orderItems) {
        this.id = id;
        this.orderDate = orderDate;
        this.vendor = vendor;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public Vendor getVendor(){
        return this.vendor;
    }

    public List<? extends OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}