package at.ac.fhstp.bad.pgmon.util;

import at.ac.fhstp.bad.pgmon.entities.Sighting;
import at.ac.fhstp.bad.pgmon.entities.Species;
import at.ac.fhstp.bad.pgmon.rest.pokemon.responsemodel.PokemonResponse;
import at.ac.fhstp.bad.pgmon.rest.sightings.requestmodel.SightingPostRequest;
import at.ac.fhstp.bad.pgmon.rest.sightings.responseModel.Position;
import at.ac.fhstp.bad.pgmon.rest.sightings.responseModel.SightingResponse;

public class RestDTOMapper {

    public static PokemonResponse species2PokemonResponse(Species species, String language){
        PokemonResponse pokemonResponse = new PokemonResponse();
        pokemonResponse.setHeight(species.getHeight());
        if(language.equalsIgnoreCase("DE")) {
            pokemonResponse.setName(species.getName_de());
            species.getPokemonTypes().forEach(t->pokemonResponse.getTypes().add(t.getTypeName_de()));
        }
        else {
            pokemonResponse.setName(species.getName_en());
            species.getPokemonTypes().forEach(t->pokemonResponse.getTypes().add(t.getTypeName_en()));

        }
        pokemonResponse.setWeight(species.getWeight());
        pokemonResponse.setPokedex_id(species.getSpeciesId());

        return pokemonResponse;
    }

    public static Sighting sightingPostRequest2Sighting(SightingPostRequest sightingPostRequest){
        Sighting sighting = new Sighting();
        sighting.setPokedexId(sightingPostRequest.getPokedexId());
        sighting.setLat(sightingPostRequest.getPosition().getLat());
        sighting.setLng(sightingPostRequest.getPosition().getLng());
        return sighting;
    }

    public static SightingResponse sighting2SightingResponse(Sighting sighting){
        SightingResponse sightingResponse = new SightingResponse();
        sightingResponse.setTimestamp(sighting.getTimestamp());
        sightingResponse.setSightingId(sighting.getId());
        Position position = new Position();
        position.setLng(sighting.getLng());
        position.setLat(sighting.getLat());
        sightingResponse.setPosition(position);
        sightingResponse.setPokedexId(sighting.getPokedexId());
        return sightingResponse;
    }

}
