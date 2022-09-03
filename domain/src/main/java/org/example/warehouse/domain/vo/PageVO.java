package org.example.warehouse.domain.vo;

import java.util.List;

public final class PageVO<T> {
    private List<T> items;
    private Long totalItems;
    private Integer totalPages;
    private Integer currentPage;

    public PageVO(List<T> items, Long totalItems, Integer totalPages, Integer currentPage) {
        this.items = items;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    public List<T> getItems() {
        return items;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }
}


