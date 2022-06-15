package com.eduardosilva218.pokeapi.services;

import com.eduardosilva218.pokeapi.models.database.TrainerDatabaseModel;
import com.eduardosilva218.pokeapi.repositories.interfaces.ITrainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {
    private final ITrainerRepository trainerRepository;

    public TrainerService(ITrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public List<TrainerDatabaseModel> getAllTrainers() {
        return trainerRepository.findAll();
    }

    public TrainerDatabaseModel getTrainerById(String id) {
        return trainerRepository.findById(id).orElse(null);
    }

    public List<TrainerDatabaseModel> getAllTrainersByName(String name) {
        return trainerRepository.findAllByName(name);
    }

    public TrainerDatabaseModel saveOrUpdateTrainer(TrainerDatabaseModel trainer) {
        return trainerRepository.save(trainer);
    }

    public List<TrainerDatabaseModel> saveAllTrainers(List<TrainerDatabaseModel> trainers) {
        return trainerRepository.saveAll(trainers);
    }

    public void deleteAllTrainers() {
        trainerRepository.deleteAll();
    }

    public void deleteTrainerById(String id) {
        trainerRepository.deleteById(id);
    }

    public void deleteAllTrainersByName(String name) {
        trainerRepository.deleteAllByName(name);
    }
}
