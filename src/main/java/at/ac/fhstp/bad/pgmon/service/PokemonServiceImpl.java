package at.ac.fhstp.bad.pgmon.service;

import at.ac.fhstp.bad.pgmon.entities.Species;
import at.ac.fhstp.bad.pgmon.persistence.SpeciesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PokemonServiceImpl implements PokemonService {

   private SpeciesRepository speciesRepository;

    public PokemonServiceImpl(SpeciesRepository speciesRepository){
        this.speciesRepository = speciesRepository;
    }

    @Override
    public List<Species> getAllSpecies() {

        List<Species> species = new ArrayList<>();
        speciesRepository.findAll().forEach(species::add);
        return species;
    }

    @Override
    public Optional<Species> getSpeciesByPokedexId(Integer pokedexId) {
        return speciesRepository.findById(pokedexId);
    }
}
