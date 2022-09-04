package org.example.warehouse.infrastructure.store;

import org.example.warehouse.domain.item.IItemStore;
import org.example.warehouse.domain.item.Item;
import org.example.warehouse.domain.vo.PageVO;
import org.example.warehouse.infrastructure.entity.ItemEntity;
import org.example.warehouse.infrastructure.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.stream.Collectors;

public class ItemStore implements IItemStore {
    private ItemRepository itemRepository;

    public ItemStore(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public PageVO<Item> getItemPage(int page, int size) {
        Page<ItemEntity> itemEntities = itemRepository.findAll(PageRequest.of(page, size));
        return new PageVO<>(
                itemEntities.getContent().stream().map(ItemEntity::toDomainItem).collect(Collectors.toList()),
                itemEntities.getTotalElements(),
                itemEntities.getTotalPages(),
                itemEntities.getNumber()
        );
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id).map(ItemEntity::toDomainItem);
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(ItemEntity.fromDomainItem(item)).toDomainItem();
    }
}
