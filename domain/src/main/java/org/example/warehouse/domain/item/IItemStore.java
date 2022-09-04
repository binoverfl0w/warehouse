package org.example.warehouse.domain.item;

import org.example.warehouse.domain.vo.PageVO;

import java.util.Optional;

public interface IItemStore {
    PageVO<Item> getItemPage(int page, int size);

    Optional<Item> findById(Long id);
}
