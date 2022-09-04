package org.example.warehouse.infrastructure.entity;

import org.example.warehouse.domain.order.OrderItem;
import org.example.warehouse.domain.vo.Quantity;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_ITEM")
@AssociationOverrides({
        @AssociationOverride(name = "orderEntity",
                joinColumns = @JoinColumn(name = "ORDER_ID")),
        @AssociationOverride(name = "itemEntity",
                joinColumns = @JoinColumn(name = "ITEM_ID")) })
public class OrderItemEntity {
    @EmbeddedId
    private OrderItemID pk = new OrderItemID();

    @Column(name = "REQUESTED_QUANTITY", nullable = false)
    private Long requestedQuantity;

    public OrderItemID getPk() {
        return pk;
    }
    public void setPk(OrderItemID pk) {
        this.pk = pk;
    }

    @Transient
    public OrderEntity getOrderEntity() {
        return pk.getOrderEntity();
    }

    @Transient
    public ItemEntity getItemEntity() {
        return pk.getItemEntity();
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        pk.setOrderEntity(orderEntity);
    }

    public void setItemEntity(ItemEntity itemEntity) {
        pk.setItemEntity(itemEntity);
    }

    public void setRequestedQuantity(Long requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public OrderItem toDomainOrderItem() {
        return new OrderItem(
                getItemEntity().toDomainItem(),
                new Quantity(requestedQuantity)
        );
    }

    public static OrderItemEntity fromDomainOrderItem(OrderItem orderItem) {
        OrderItemEntity mapOrderItem = new OrderItemEntity();
        mapOrderItem.setItemEntity(ItemEntity.fromDomainItem(orderItem.getItem()));
        mapOrderItem.setRequestedQuantity(orderItem.getRequestedQuantity().getValue());
        return mapOrderItem;
    }
}
