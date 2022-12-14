package org.example.warehouse.service;

import org.example.warehouse.domain.DomainService;
import org.example.warehouse.domain.item.IItemStore;
import org.example.warehouse.domain.item.Item;
import org.example.warehouse.domain.order.Order;
import org.example.warehouse.domain.vo.PageVO;
import org.example.warehouse.domain.vo.Quantity;
import org.example.warehouse.service.exception.AccessDeniedException;
import org.example.warehouse.service.exception.ItemNotFoundException;

import java.util.Set;

public class ItemService extends DomainService {
    private IItemStore itemStore;

    public ItemService(IItemStore itemStore) {
        this.itemStore = itemStore;
    }

    public PageVO<Item> getItemPage(int page, int size) {
        return itemStore.getItemPage(page, size);
    }

    public Item getItem(Long id) {
        return itemStore.findById(id).orElseThrow(() -> new ItemNotFoundException("id", id.toString()));
    }

    public Item createItem(Item item) {
        if (!hasRole("WAREHOUSE_MANAGER")) throw new AccessDeniedException();
        item.isValid();
        return itemStore.save(item);
    }

    public Item updateItem(Item item) {
        if (!hasRole("WAREHOUSE_MANAGER")) throw new AccessDeniedException();
        Item toUpdate = itemStore.findById(item.getId()).orElseThrow(() -> new ItemNotFoundException("id", item.getId().toString()));
        if (item.getName() != null) toUpdate.setName(item.getName());
        if (item.getQuantity() != null) toUpdate.setQuantity(item.getQuantity());
        if (item.getUnitPrice() != null) toUpdate.setUnitPrice(item.getUnitPrice());
        toUpdate.isValid();
        return itemStore.save(toUpdate);
    }

    public void deleteItem(Long id) {
        if (!hasRole("WAREHOUSE_MANAGER")) throw new AccessDeniedException();
        itemStore.findById(id).orElseThrow(() -> new ItemNotFoundException("id", id.toString()));
        itemStore.deleteById(id);
    }

    public void recalculateItemQuantities(Set<Order> orders) {
        orders.forEach(order -> {
            order.getOrderItems().forEach(orderItem -> {
                orderItem.getItem().setQuantity(new Quantity(orderItem.getItem().getQuantity().getValue() - orderItem.getRequestedQuantity().getValue()));
                itemStore.save(orderItem.getItem());
            });
        });
    }
}
