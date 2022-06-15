package com.eduardosilva218.pokeapi.services;

import com.eduardosilva218.pokeapi.models.database.ItemDatabaseModel;
import com.eduardosilva218.pokeapi.repositories.interfaces.IItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final IItemRepository itemRepository;

    public ItemService(IItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemDatabaseModel> getAllItems() {
        return itemRepository.findAll();
    }

    public ItemDatabaseModel getItemById(String id) {
        return itemRepository.findById(id).orElse(null);
    }

    public List<ItemDatabaseModel> getAllItemsByName(String name) {
        return itemRepository.findAllByName(name);
    }

    public ItemDatabaseModel saveOrUpdateItem(ItemDatabaseModel item) {
        return itemRepository.save(item);
    }

    public List<ItemDatabaseModel> saveAllItems(List<ItemDatabaseModel> items) {
        return itemRepository.saveAll(items);
    }

    public void deleteAllItems() {
        itemRepository.deleteAll();
    }

    public void deleteItemById(String id) {
        itemRepository.deleteById(id);
    }

    public void deleteAllItemsByName(String name) {
        itemRepository.deleteAllByName(name);
    }
}
