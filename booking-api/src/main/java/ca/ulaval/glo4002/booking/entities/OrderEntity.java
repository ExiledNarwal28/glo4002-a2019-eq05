package ca.ulaval.glo4002.booking.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
	private Long id;
    private LocalDateTime orderDate;
    private Long vendorId;
    @OneToMany(mappedBy = "order")
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    public OrderEntity() {

    }

    public OrderEntity(LocalDateTime orderDate, Long vendorId, List<OrderItemEntity> orderItems) {
        this.orderDate = orderDate;
        this.vendorId = vendorId;
        this.orderItems = orderItems;
    }

    public OrderEntity(Long id, LocalDateTime orderDate, Long vendorId, List<OrderItemEntity> orderItems) {
        this.id = id;
        this.orderDate = orderDate;
        this.vendorId = vendorId;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public List<? extends OrderItemEntity> getOrderItems() {
        return orderItems;
    }

    public void addOrderItems(List<? extends OrderItemEntity> orderItems) {
        this.orderItems.addAll(orderItems);
    }

    public void clearOrderItems() {
        this.orderItems.clear();
    }
}