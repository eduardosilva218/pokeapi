package com.eduardosilva218.pokeapi.models.hateoas;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@JacksonXmlRootElement(localName = "trainer")
public class TrainerHateoasModel extends RepresentationModel<TrainerHateoasModel> {
    public String id;
    public String name;
    //todo solve xml content name problem, maybe -> https://stackoverflow.com/questions/48453715/how-do-i-specify-a-list-element-name-when-serializing-xml-with-jackson-datab
    public List<ItemHateoasModel> items;
}
