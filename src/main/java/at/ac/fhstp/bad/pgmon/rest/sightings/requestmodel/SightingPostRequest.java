package at.ac.fhstp.bad.pgmon.rest.sightings.requestmodel;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SightingPostRequest {

    @JsonProperty("pokedex-id")
    private Integer pokedexId;
    private Position position;

    public Integer getPokedexId() {
        return pokedexId;
    }

    public void setPokedexId(Integer pokedexId) {
        this.pokedexId = pokedexId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

}
