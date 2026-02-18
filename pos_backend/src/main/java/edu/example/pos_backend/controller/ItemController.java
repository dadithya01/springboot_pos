package edu.example.pos_backend.controller;

import edu.example.pos_backend.dto.ItemDTO;
import edu.example.pos_backend.service.impl.ItemServiceImpl;
import edu.example.pos_backend.util.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/items")
@RestController
@RequiredArgsConstructor
@CrossOrigin
@Validated
public class ItemController {

    private final ItemServiceImpl itemServiceImpl;

    @PostMapping
    public ResponseEntity <APIResponse<String>> saveItem(@RequestBody ItemDTO itemDTO) {
        itemServiceImpl.saveItem(itemDTO);
        return new ResponseEntity<>(new APIResponse<>(201, "Item saved successfully", null), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<APIResponse<String>> updateItem(@RequestBody ItemDTO itemDTO) {
        itemServiceImpl.updateItem(itemDTO);
        return new ResponseEntity<>(new APIResponse<>(200, "Item updated successfully", null), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <APIResponse<String>> deleteItem(@PathVariable long id) {
        itemServiceImpl.deleteItem(id);
        return new ResponseEntity<>(new APIResponse<>(200, "Item deleted successfully", null), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity <APIResponse<Iterable<ItemDTO>>> getAllItems() {
        Iterable<ItemDTO> itemDTOs = itemServiceImpl.getAllItems();
        return new ResponseEntity<>(new APIResponse<>(200, "Items retrieved successfully", itemDTOs), HttpStatus.OK);
    }
}
