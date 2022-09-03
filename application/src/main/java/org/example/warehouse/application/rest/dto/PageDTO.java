package org.example.warehouse.application.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter

public class PageDTO<T> implements Serializable {
    @JsonProperty(value = "items", index = 1)
    private List<T> items;

    @JsonProperty(value = "totalItems", index = 2)
    private Long totalItems;

    @JsonProperty(value = "currentPage", index = 3)
    private Integer currentPage;

    @JsonProperty(value = "totalPages", index = 4)
    private Integer totalPages;
}
