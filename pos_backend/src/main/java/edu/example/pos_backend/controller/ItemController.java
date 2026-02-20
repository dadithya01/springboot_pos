package edu.example.pos_backend.controller;

import edu.example.pos_backend.dto.ItemDTO;
import edu.example.pos_backend.service.ItemService;
import edu.example.pos_backend.util.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/v1/item")
@CrossOrigin
@RequiredArgsConstructor
@Validated
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<APIResponse<String>> saveItem(@RequestBody @Valid ItemDTO itemDTO) {

        itemService.saveItem(itemDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(
                        201,
                        "Item saved successfully",
                        null
                ));
    }

    @PutMapping
    public ResponseEntity<APIResponse<String>> updateItem(@RequestBody @Valid ItemDTO itemDTO) {

        itemService.updateItem(itemDTO);

        return ResponseEntity.ok(
                new APIResponse<>(
                        200,
                        "Item updated successfully",
                        null
                )
        );
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<ItemDTO>>> getAllItems() {

        List<ItemDTO> items = itemService.getAllItems();

        return ResponseEntity.ok(
                new APIResponse<>(
                        200,
                        "Item list fetched",
                        items
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ItemDTO>> getItem(@PathVariable Integer id) {

        ItemDTO item = itemService.getItem(id);

        return ResponseEntity.ok(
                new APIResponse<>(
                        200,
                        "Item fetched",
                        item
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteItem(@PathVariable Integer id) {

        itemService.deleteItem(id);

        return ResponseEntity.ok(
                new APIResponse<>(
                        200,
                        "Item deleted successfully",
                        null
                )
        );
    }
}
