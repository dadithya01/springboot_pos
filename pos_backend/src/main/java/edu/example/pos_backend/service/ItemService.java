package edu.example.pos_backend.service;

import edu.example.pos_backend.dto.ItemDTO;

import java.util.List;

public interface ItemService {

    void saveItem(ItemDTO itemDTO);

    void updateItem(ItemDTO itemDTO);

    List<ItemDTO> getAllItems();

    ItemDTO getItem(Integer id);

    void deleteItem(Integer id);
}
