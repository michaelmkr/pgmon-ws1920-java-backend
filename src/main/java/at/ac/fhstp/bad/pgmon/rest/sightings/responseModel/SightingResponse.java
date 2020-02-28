package at.ac.fhstp.bad.pgmon.rest.sightings.responseModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class SightingResponse {

    @JsonProperty("sighting-id")
    private Long sightingId;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Date timestamp;

    @JsonProperty("pokedex-id")
    private Integer pokedexId;

    private Position position;

    public Long getSightingId() {
        return sightingId;
    }

    public void setSightingId(Long sightingId) {
        this.sightingId = sightingId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

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
