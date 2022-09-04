package org.example.warehouse.infrastructure.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class OrderItemID implements Serializable {

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private OrderEntity orderEntity;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private ItemEntity itemEntity;

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public ItemEntity getItemEntity() {
        return itemEntity;
    }

    public void setItemEntity(ItemEntity itemEntity) {
        this.itemEntity = itemEntity;
    }
}
