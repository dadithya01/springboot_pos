package edu.example.pos_backend.service.impl;

import edu.example.pos_backend.dto.ItemDTO;
import edu.example.pos_backend.entity.Item;
import edu.example.pos_backend.repository.ItemRepo;
import edu.example.pos_backend.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepo itemRepo;
    private final ModelMapper modelMapper;

    @Override
    public void saveItem(ItemDTO itemDTO) {
        itemRepo.save(modelMapper.map(itemDTO , Item.class));
    }

    @Override
    public void updateItem(ItemDTO itemDTO) {
        itemRepo.save(modelMapper.map(itemDTO , Item.class));
    }

    @Override
    public void deleteItem(long id) {
        itemRepo.deleteById(id);
    }

    @Override
    public List<ItemDTO> getAllItems() {
        List<Item> items = itemRepo.findAll();
        return modelMapper.map(items, new TypeToken<List<ItemDTO>>() {}.getType());
    }
}
