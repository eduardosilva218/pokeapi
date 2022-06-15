package com.eduardosilva218.pokeapi.models.adapters;

import com.eduardosilva218.pokeapi.controllers.ItemController;
import com.eduardosilva218.pokeapi.models.database.TrainerDatabaseModel;
import com.eduardosilva218.pokeapi.models.hateoas.ItemHateoasModel;
import com.eduardosilva218.pokeapi.models.hateoas.TrainerHateoasModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.stream.Collectors;

public class AdapterTrainerDatabaseModelToTrainerHateoasModel extends TrainerHateoasModel {
    public AdapterTrainerDatabaseModelToTrainerHateoasModel(TrainerDatabaseModel trainerDatabaseModel) {
        this.id = trainerDatabaseModel.getId();
        this.name = trainerDatabaseModel.getName();
        //todo put in method to avoid code duplication, together with the method returnModelWithLinkById
        this.items = trainerDatabaseModel.getItems().stream()
                .map(itemDatabaseModel -> {
                    var itemHateoasModel = new AdapterItemDatabaseModelToItemHateoasModel(itemDatabaseModel);
                    try {
                        return returnModelWithLinkById(itemHateoasModel);
                    } catch (NoSuchMethodException noSuchMethodException) {
                        throw new RuntimeException(noSuchMethodException);
                    }
                }).collect(Collectors.toList());
    }

    //todo refactor this method, is copy paste from ItemController
    private ItemHateoasModel returnModelWithLinkById(ItemHateoasModel itemHateoasModel) throws NoSuchMethodException {
        itemHateoasModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                .methodOn(ItemController.class)
                .getItemById(itemHateoasModel.getId())).withSelfRel());
        return itemHateoasModel;
    }
}
