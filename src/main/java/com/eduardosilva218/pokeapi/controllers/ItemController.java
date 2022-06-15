package com.eduardosilva218.pokeapi.controllers;

import com.eduardosilva218.pokeapi.models.adapters.AdapterItemDatabaseModelToItemHateoasModel;
import com.eduardosilva218.pokeapi.models.database.ItemDatabaseModel;
import com.eduardosilva218.pokeapi.models.hateoas.ItemHateoasModel;
import com.eduardosilva218.pokeapi.services.ItemService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//todo appropriated origins, maybe environment variable?
@CrossOrigin(origins = {"http://localhost:8081"})
@RequestMapping(value = "/items/v1", produces = {"application/json", "application/xml"})
//todo treat exceptions
@RestController
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public CollectionModel<ItemHateoasModel> getAllItems() throws NoSuchMethodException {
        var items = adapterListItemsDatabaseModelToListItemsHateoasModel(itemService.getAllItems());
        appendLinksToItems(items);
        return returnCollectionModelWithLinksToGetAll(items);
    }

    @GetMapping(value = "/only/{id}")
    public ItemHateoasModel getItemById(@PathVariable String id) throws NoSuchMethodException {
        return returnModelWithLinkById(new AdapterItemDatabaseModelToItemHateoasModel(itemService.getItemById(id)));
    }

    @GetMapping(value = "/any/{name}")
    public CollectionModel<ItemHateoasModel> getAllItemsByName(@PathVariable String name) throws NoSuchMethodException {
        var items = adapterListItemsDatabaseModelToListItemsHateoasModel(itemService.getAllItemsByName(name));
        appendLinksToItems(items);
        return returnCollectionModelWithLinksToGetAllByName(items, name);
    }

    @PutMapping(value = "/any/{name}", consumes = {"application/json", "application/xml"})
    public CollectionModel<ItemHateoasModel> updateAllItemsByName(@PathVariable String name, @RequestBody ItemDatabaseModel itemModel) throws NoSuchMethodException {
        var itemsDatabaseModels = itemService.getAllItemsByName(name);
        itemsDatabaseModels = itemsDatabaseModels.stream().peek(item -> {
            item.setName(itemModel.getName());
            item.setDescription(itemModel.getDescription());
        }).toList();
        var items = adapterListItemsDatabaseModelToListItemsHateoasModel(itemsDatabaseModels);
        appendLinksToItems(items);
        return returnCollectionModelWithLinksToGetAllByName(items, name);
    }

    private List<ItemHateoasModel> adapterListItemsDatabaseModelToListItemsHateoasModel(
            List<ItemDatabaseModel> itemsDatabaseModels) {
        var itemsHateoas = new ArrayList<ItemHateoasModel>();
        for (var item : itemsDatabaseModels){
            itemsHateoas.add(new AdapterItemDatabaseModelToItemHateoasModel(item));
        }
        return itemsHateoas;
    }

    @PostMapping(consumes = {"application/json", "application/xml"})
    public ItemHateoasModel createItem(@RequestBody ItemDatabaseModel itemModel) throws NoSuchMethodException {
        return returnModelWithLinkById(new AdapterItemDatabaseModelToItemHateoasModel(itemService.saveOrUpdateItem(itemModel)));
    }

    @PutMapping(consumes = {"application/json", "application/xml"})
    public ItemHateoasModel updateItemById(@PathVariable String id, @RequestBody ItemDatabaseModel itemModel)
            throws NoSuchMethodException {
        var item = itemService.getItemById(id);
        item.setName(itemModel.getName());
        item.setDescription(itemModel.getDescription());
        return returnModelWithLinkById(new AdapterItemDatabaseModelToItemHateoasModel(itemService.saveOrUpdateItem(item)));
    }

    @DeleteMapping
    public void deleteAllItems() {
        itemService.deleteAllItems();
    }

    @DeleteMapping("/only/{id}")
    public void deleteItemById(@PathVariable String id) {
        itemService.deleteItemById(id);
    }

    @DeleteMapping("/any/{name}")
    public void deleteAllItemsByName(@PathVariable String name) {
        itemService.deleteAllItemsByName(name);
    }

    //todo treat exceptions
    //todo - leave the four methods below in a generic service
    private CollectionModel<ItemHateoasModel> returnCollectionModelWithLinksToGetAll(List<ItemHateoasModel> items) throws NoSuchMethodException {
        var collectionItems = CollectionModel.of(items);
        collectionItems.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ItemController.class).getAllItems()).withRel(IanaLinkRelations.COLLECTION));
        return collectionItems;
    }

    private CollectionModel<ItemHateoasModel> returnCollectionModelWithLinksToGetAllByName(List<ItemHateoasModel> items, String name) throws NoSuchMethodException {
        var collectionItems = CollectionModel.of(items);
        collectionItems.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ItemController.class).getAllItemsByName(name))
                .withRel(IanaLinkRelations.COLLECTION));
        return collectionItems;
    }

    private void appendLinksToItems(List<ItemHateoasModel> items) throws NoSuchMethodException {
        for (var item : items) returnModelWithLinkById(item);
    }

    private ItemHateoasModel returnModelWithLinkById(ItemHateoasModel itemHateoasModel) throws NoSuchMethodException {
        itemHateoasModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ItemController.class)
                .getItemById(itemHateoasModel.getId())).withSelfRel());
        return itemHateoasModel;
    }
}
