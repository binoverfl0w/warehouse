package org.example.warehouse.domain.item;

import org.example.warehouse.domain.vo.PageVO;

public interface IItemStore {
    PageVO<Item> getItemPage(int page, int size);
}
