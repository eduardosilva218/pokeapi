package com.eduardosilva218.pokeapi.models.adapters;

import com.eduardosilva218.pokeapi.models.database.ItemDatabaseModel;
import com.eduardosilva218.pokeapi.models.hateoas.ItemHateoasModel;

public class AdapterItemDatabaseModelToItemHateoasModel extends ItemHateoasModel {
    public AdapterItemDatabaseModelToItemHateoasModel(ItemDatabaseModel itemDatabaseModel) {
        this.id = itemDatabaseModel.getId();
        this.name = itemDatabaseModel.getName();
        this.description = itemDatabaseModel.getDescription();
    }
}
