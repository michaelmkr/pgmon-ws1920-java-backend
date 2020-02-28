package at.ac.fhstp.bad.pgmon.rest.pokemon;

import at.ac.fhstp.bad.pgmon.entities.Species;
import at.ac.fhstp.bad.pgmon.rest.pokemon.exceptions.PokemonNotFoundException;
import at.ac.fhstp.bad.pgmon.rest.pokemon.responsemodel.PokemonResponse;
import at.ac.fhstp.bad.pgmon.service.PokemonService;
import at.ac.fhstp.bad.pgmon.util.RestDTOMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pokemon")
public class PokemonEndpoint {

    private PokemonService pokemonService;

    public PokemonEndpoint(PokemonService pokemonService){
        this.pokemonService = pokemonService;
    }

    @GetMapping("/{language}")
    public List<PokemonResponse> getPokemonList(@PathVariable("language") String language){
        List<Species> species = pokemonService.getAllSpecies();
        List<PokemonResponse> pokemonResponses = new ArrayList<>();

        // Wenn language!=de und language!=en -> Fehler!

        for(Species s: species){
            pokemonResponses.add(RestDTOMapper.species2PokemonResponse(s,language));
        }
        return pokemonResponses;
    }


    @GetMapping("/{language}/{pokedexId}")
    public PokemonResponse getPokemonByPokedexId(@PathVariable("language") String language,
                                                       @PathVariable("pokedexId") Integer pokedexId){

        Species species;
        Optional<Species> speciesResult = pokemonService.getSpeciesByPokedexId(pokedexId);

        if(speciesResult.isPresent()){
            species = speciesResult.get();
        } else {
            throw new PokemonNotFoundException("Pokemon with id " + pokedexId + " not found!");
        }
        
        return RestDTOMapper.species2PokemonResponse(species, language);
    }

}
