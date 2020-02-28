package at.ac.fhstp.bad.pgmon.service;

import at.ac.fhstp.bad.pgmon.entities.Species;

import java.util.List;
import java.util.Optional;

public interface PokemonService {

    List<Species> getAllSpecies();
    Optional<Species> getSpeciesByPokedexId(Integer pokedexId);

}
