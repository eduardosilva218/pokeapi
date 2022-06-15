package com.eduardosilva218.pokeapi.models.hateoas;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@JacksonXmlRootElement(localName = "item")
public class ItemHateoasModel extends RepresentationModel<ItemHateoasModel> {
    public String id;
    public String name;
    public String description;
}
