package com.eduardosilva218.pokeapi.controllers;

import com.eduardosilva218.pokeapi.models.adapters.AdapterTrainerDatabaseModelToTrainerHateoasModel;
import com.eduardosilva218.pokeapi.models.database.TrainerDatabaseModel;
import com.eduardosilva218.pokeapi.models.hateoas.TrainerHateoasModel;
import com.eduardosilva218.pokeapi.services.TrainerService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//todo treat exceptions
@CrossOrigin(origins = {"http://localhost:8081"})
@RequestMapping(value = "/trainers/v1", produces = {"application/json", "application/xml"})
@RestController
public class TrainerController {
    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping(value = "/only/{id}", produces = {"application/json", "application/xml"})
    public TrainerHateoasModel getTrainerById(@PathVariable String id) throws NoSuchMethodException {
        return returnModelWithLinkById(new AdapterTrainerDatabaseModelToTrainerHateoasModel(trainerService.getTrainerById(id)));
    }

    @GetMapping
    public CollectionModel<TrainerHateoasModel> getAllTrainers() throws NoSuchMethodException {
        var trainers = adapterListTrainersDatabaseModelsToListTrainersHateoasModels(trainerService.getAllTrainers());
        appendLinksToTrainers(trainers);
        return returnCollectionModelWithLinksToGetAll(trainers);
    }

    @GetMapping(value = "/any/{name}")
    public CollectionModel<TrainerHateoasModel> getAllTrainersByName(@PathVariable String name) throws NoSuchMethodException {
        var trainers = adapterListTrainersDatabaseModelsToListTrainersHateoasModels(trainerService.getAllTrainersByName(name));
        appendLinksToTrainers(trainers);
        return returnCollectionModelWithLinksToGetAllByName(trainers, name);
    }

    @PutMapping(value = "/any/{name}", consumes = {"application/json", "application/xml"})
    public CollectionModel<TrainerHateoasModel> updateAllTrainersByName(@PathVariable String name, @RequestBody TrainerDatabaseModel trainerModel)
            throws NoSuchMethodException {
        var trainers = trainerService.getAllTrainersByName(name);
        trainers = trainers.stream().peek(trainer -> {
            trainer.setName(trainerModel.getName());
            trainer.setItems(trainerModel.getItems());
        }).toList();
        var trainersToReturn = adapterListTrainersDatabaseModelsToListTrainersHateoasModels(trainers);
        appendLinksToTrainers(trainersToReturn);
        return returnCollectionModelWithLinksToGetAllByName(trainersToReturn, name);
    }

    private List<TrainerHateoasModel> adapterListTrainersDatabaseModelsToListTrainersHateoasModels(List<TrainerDatabaseModel> trainersDatabaseModels) {
        var trainerHateoasModels = new ArrayList<TrainerHateoasModel>();
        for (var trainer : trainersDatabaseModels)
            trainerHateoasModels.add(new AdapterTrainerDatabaseModelToTrainerHateoasModel(trainer));
        return trainerHateoasModels;
    }

    @PostMapping(consumes = {"application/json", "application/xml"})
    public TrainerHateoasModel createTrainer(@RequestBody TrainerDatabaseModel trainerModel) throws NoSuchMethodException {
        return returnModelWithLinkById(new AdapterTrainerDatabaseModelToTrainerHateoasModel(trainerService.saveOrUpdateTrainer(trainerModel)));
    }

    @PutMapping(value = "/only/{id}", consumes = {"application/json", "application/xml"})
    public TrainerHateoasModel updateTrainerById(@PathVariable String id, @RequestBody TrainerDatabaseModel trainerModel)
            throws NoSuchMethodException {
        var trainer = trainerService.getTrainerById(id);
        trainer.setName(trainerModel.getName());
        trainer.setItems(trainerModel.getItems());
        return returnModelWithLinkById(new AdapterTrainerDatabaseModelToTrainerHateoasModel(trainerService.saveOrUpdateTrainer(trainer)));
    }

    @DeleteMapping("/trainers")
    public void deleteAllTrainers() {
        trainerService.deleteAllTrainers();
    }

    @DeleteMapping("/only/{id}")
    public void deleteTrainerById(@PathVariable String id) {
        trainerService.deleteTrainerById(id);
    }

    @DeleteMapping("/any/{name}")
    public void deleteAllTrainersByName(@PathVariable String name) {
        trainerService.deleteAllTrainersByName(name);
    }

    //todo - leave the four methods below in a generic service
    private CollectionModel<TrainerHateoasModel> returnCollectionModelWithLinksToGetAll(List<TrainerHateoasModel> trainers)
            throws NoSuchMethodException {
        var collectionTrainers = CollectionModel.of(trainers);
        collectionTrainers.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TrainerController.class)
                .getAllTrainers()).withRel(IanaLinkRelations.COLLECTION));
        return collectionTrainers;
    }

    private CollectionModel<TrainerHateoasModel> returnCollectionModelWithLinksToGetAllByName(List<TrainerHateoasModel> trainers, String name)
            throws NoSuchMethodException {
        var collectionTrainers = CollectionModel.of(trainers);
        collectionTrainers.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TrainerController.class).getAllTrainersByName(name))
                .withRel(IanaLinkRelations.COLLECTION));
        return collectionTrainers;
    }

    private void appendLinksToTrainers(List<TrainerHateoasModel> trainers) throws NoSuchMethodException {
        for (var trainer : trainers) returnModelWithLinkById(trainer);
    }

    private TrainerHateoasModel returnModelWithLinkById(TrainerHateoasModel trainerHateoasModel) throws NoSuchMethodException {
        trainerHateoasModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TrainerController.class)
                .getTrainerById(trainerHateoasModel.getId())).withSelfRel());
        return trainerHateoasModel;
    }
}
