package org.example.warehouse.service;

import org.example.warehouse.domain.DomainService;
import org.example.warehouse.domain.item.IItemStore;
import org.example.warehouse.domain.item.Item;
import org.example.warehouse.domain.vo.PageVO;
import org.example.warehouse.service.exception.AccessDeniedException;
import org.example.warehouse.service.exception.ItemNotFoundException;

public class ItemService extends DomainService {
    private IItemStore itemStore;

    public ItemService(IItemStore itemStore) {
        this.itemStore = itemStore;
    }

    public PageVO<Item> getItemPage(int page, int size) {
        if (!hasRole("WAREHOUSE_MANAGER") && !hasRole("CLIENT")) throw new AccessDeniedException();
        return itemStore.getItemPage(page, size);
    }

    public Item getItem(Long id) {
        if (!hasRole("WAREHOUSE_MANAGER") && !hasRole("CLIENT")) throw new AccessDeniedException();
        return itemStore.findById(id).orElseThrow(() -> new ItemNotFoundException("id", id.toString()));
    }
}