package org.example.warehouse.application.rest;

import org.example.warehouse.application.rest.dto.ItemDTO;
import org.example.warehouse.application.rest.dto.PageDTO;
import org.example.warehouse.domain.item.Item;
import org.example.warehouse.domain.vo.PageVO;
import org.example.warehouse.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
public class ItemController {
    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<Object> getItems(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 20;

        PageVO<Item> items = itemService.getItemPage(page, size);
        PageDTO<ItemDTO> itemsDTO = new PageDTO<>();
        itemsDTO.setItems(items.getItems().stream().map(ItemDTO::fromDomainItem).collect(Collectors.toList()));
        itemsDTO.setTotalItems(items.getTotalItems());
        itemsDTO.setTotalPages(items.getTotalPages());
        itemsDTO.setCurrentPage(items.getCurrentPage());
        return ResponseEntity.ok(itemsDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItem(@PathVariable Long id) {
        return ResponseEntity.ok(ItemDTO.fromDomainItem(itemService.getItem(id)));
    }

    @PostMapping
    public ResponseEntity<Object> createItem(@RequestBody ItemDTO itemDTO) {
        return ResponseEntity.ok(ItemDTO.fromDomainItem(itemService.createItem(itemDTO.toDomainItem())));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateItem(@PathVariable Long id, @RequestBody ItemDTO itemDTO) {
        itemDTO.setId(id);
        return ResponseEntity.ok(ItemDTO.fromDomainItem(itemService.updateItem(itemDTO.toDomainItem())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return new ResponseEntity<>(ResponseEntity.EMPTY, ResponseEntity.EMPTY.getHeaders(), HttpStatus.NO_CONTENT);
    }
}
