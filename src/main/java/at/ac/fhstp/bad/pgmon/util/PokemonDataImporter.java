package at.ac.fhstp.bad.pgmon.util;

import at.ac.fhstp.bad.pgmon.entities.PokemonType;
import at.ac.fhstp.bad.pgmon.entities.Species;
import at.ac.fhstp.bad.pgmon.persistence.SpeciesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class PokemonDataImporter {

    private static Logger logger = LoggerFactory.getLogger(PokemonDataImporter.class);
    private SpeciesRepository speciesRepository;
    private Environment environment;

    private static String POKEMON = "pokemon.csv";
    private static String POKEMON_SPECIES_NAMES = "pokemon_species_names.csv";
    private static String POKEMON_TYPES = "pokemon_types.csv";
    private static String POKEMON_TYPE_NAMES = "type_names.csv";

    private ClassLoader classLoader = PokemonDataImporter.class.getClassLoader();

    public PokemonDataImporter(SpeciesRepository speciesRepository, Environment environment){
        this.speciesRepository = speciesRepository;
        this.environment = environment;
    }

    @PostConstruct
    public void importData(){

        if (Arrays.asList(environment.getActiveProfiles()).contains("test")){
            logger.info("test Profile active, skipping data import.");
            return;
        }

        //  pokemon_species_id,local_language_id,name,genus
        //  en: 9, de: 6

        logger.info("importData...");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(classLoader.getResourceAsStream(POKEMON)));

        String line;

        try {
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] lineValues = line.split(",");
                Integer speciesId;
                Integer height;
                Integer weight;
                String name_de;
                String name_en;
                List<PokemonType> types;

                speciesId = Integer.parseInt(lineValues[2]);
                height = Integer.parseInt(lineValues[3]);
                weight = Integer.parseInt(lineValues[4]);
                name_de = getNameDE(speciesId);
                name_en = getNameEN(speciesId);
                types = getTypes(speciesId);

                Species species = new Species();
                species.setHeight(height);
                species.setName_de(name_de);
                species.setName_en(name_en);
                species.setSpeciesId(speciesId);
                species.setWeight(weight);
                species.setPokemonTypes(types);

                speciesRepository.save(species);
            }
            reader.close();
        } catch (IOException ioexception){
            ioexception.printStackTrace();
        }

    }


    public String getNameDE(Integer speciesId){
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(classLoader.getResourceAsStream(POKEMON_SPECIES_NAMES)));

        String line;

        try {
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] lineValues = line.split(",");
                if((Integer.parseInt(lineValues[0]) == speciesId) && (Integer.parseInt(lineValues[1]) == 6)){
                    return lineValues[2];
                }

            }
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
        return null;
    }


    public String getNameEN(Integer speciesId){
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(classLoader.getResourceAsStream(POKEMON_SPECIES_NAMES)));

        String line;

        try {
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] lineValues = line.split(",");
                if((Integer.parseInt(lineValues[0]) == speciesId) && (Integer.parseInt(lineValues[1]) == 9)){
                    return lineValues[2];
                }

            }
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
        return null;
    }


    public List<PokemonType> getTypes(Integer speciesId){

        List<Integer> typeIds;
        typeIds = getTypeIds(speciesId);

        List<PokemonType> pokemonTypes = new ArrayList<>();

        for(Integer i: typeIds){
            PokemonType pokemonType = new PokemonType();
            pokemonType.setTypeId(i);
            pokemonType.setTypeName_de(getTypeNameDE(i));
            pokemonType.setTypeName_en(getTypeNameEN(i));
            pokemonTypes.add(pokemonType);
        }

        return pokemonTypes;
    }


    public String getTypeNameDE(Integer typeId){

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(classLoader.getResourceAsStream(POKEMON_TYPE_NAMES)));

        String line;

        try {
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] lineValues = line.split(",");
                if((Integer.parseInt(lineValues[0]) == typeId) && (Integer.parseInt(lineValues[1]) == 6)){
                    return lineValues[2];
                }

            }
        }   catch (IOException ioexception) {
            ioexception.printStackTrace();
        }

        return null;

    }

    public String getTypeNameEN(Integer typeId){

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(classLoader.getResourceAsStream(POKEMON_TYPE_NAMES)));

        String line;

        try {
            reader.readLine();
            while ((line = reader.readLine()) != null) {

                String[] lineValues = line.split(",");
                if((Integer.parseInt(lineValues[0]) == typeId) && (Integer.parseInt(lineValues[1]) == 9)){
                    return lineValues[2];
                }

            }
        }   catch (IOException ioexception) {
            ioexception.printStackTrace();
        }

        return null;

    }


    public List<Integer> getTypeIds(Integer speciesId){
        List<Integer> typeIds = new ArrayList<>();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(classLoader.getResourceAsStream(POKEMON_TYPES)));

        String line;

        try {
            reader.readLine();
            while ((line = reader.readLine()) != null) {

                String[] lineValues = line.split(",");
                if(Integer.parseInt(lineValues[0]) == speciesId){
                    typeIds.add(Integer.parseInt(lineValues[1]));
                }

            }
        }   catch (IOException ioexception) {
            ioexception.printStackTrace();
        }
        return typeIds;
    }

}
