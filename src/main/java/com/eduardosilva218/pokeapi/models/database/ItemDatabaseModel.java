package com.eduardosilva218.pokeapi.models.database;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Document(collection = "items")
public class ItemDatabaseModel {
    @Id
    public String id;
    @Indexed
    @NonNull
    public String name;
    @NonNull
    public String description;
}
