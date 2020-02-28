package at.ac.fhstp.bad.pgmon.rest.pokemon.responsemodel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class PokemonResponse {

    private String name;

    @JsonProperty("pokedex-id")
    private Integer pokedex_id;
    private Integer height;
    private Integer weight;
    private List<String> types;


    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public List<String> getTypes() {
        if(types == null){
            types = new ArrayList<>();
        }
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPokedex_id() {
        return pokedex_id;
    }

    public void setPokedex_id(Integer pokedex_id) {
        this.pokedex_id = pokedex_id;
    }

}
