package com.eduardosilva218.pokeapi.models.database;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Document(collection = "trainers")
public class TrainerDatabaseModel {
    @Id
    public String id;
    @NonNull
    public String name;
    @DBRef
    @NonNull
    public List<ItemDatabaseModel> items;
}
