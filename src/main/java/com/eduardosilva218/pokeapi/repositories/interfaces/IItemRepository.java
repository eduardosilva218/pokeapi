package com.eduardosilva218.pokeapi.repositories.interfaces;

import com.eduardosilva218.pokeapi.models.database.ItemDatabaseModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IItemRepository extends MongoRepository<ItemDatabaseModel, String> {
    @Query("{'name': {$regex: ?0, $options: 'i'}}")
    List<ItemDatabaseModel> findAllByName(String name);

    @Query("{'name': {$regex: ?0, $options: 'i'}}")
    void deleteAllByName(String name);
}
