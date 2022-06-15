package com.eduardosilva218.pokeapi.migrations;

import com.eduardosilva218.pokeapi.models.database.ItemDatabaseModel;
import com.eduardosilva218.pokeapi.models.database.TrainerDatabaseModel;
import com.eduardosilva218.pokeapi.services.ItemService;
import com.eduardosilva218.pokeapi.services.TrainerService;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;

import java.util.List;

@ChangeUnit(id = "110620221403FirstDataMigration", order = "01", author = "eduardosilva218")
public class FirstDataMigration {
    private final TrainerService trainerService;
    private final ItemService itemService;

    public FirstDataMigration(ItemService itemService, TrainerService trainerService) {
        this.itemService = itemService;
        this.trainerService = trainerService;
    }

    @Execution()
    public void insertItemsAndTrainers() {
        var pokedex = new ItemDatabaseModel("Pokedex", "Is an electronic device created and designed to catalog and provide information regarding the various species of Pokemon.");
        var healerBadge = new ItemDatabaseModel("Healer's Badge", "A badge to heal your pokemon");
        var rareCandy = new ItemDatabaseModel("Rare Candy", "A rare candy for catching pokemon");
        var potion = new ItemDatabaseModel("Potion", "A potion to heal your pokemon");
        itemService.saveAllItems(List.of(pokedex, healerBadge, rareCandy, potion));
        var ash = new TrainerDatabaseModel("Ash", List.of(pokedex, healerBadge));
        var misty = new TrainerDatabaseModel("Misty", List.of(rareCandy, potion, pokedex));
        trainerService.saveAllTrainers(List.of(ash, misty));
    }

    @RollbackExecution
    public void rollback() {
        trainerService.deleteAllTrainersByName("Ash");
        trainerService.deleteAllTrainersByName("Misty");
        itemService.deleteAllItemsByName("Pokedex");
        itemService.deleteAllItemsByName("Healer's Badge");
        itemService.deleteAllItemsByName("Rare Candy");
        itemService.deleteAllItemsByName("Potion");
    }
}
