package at.ac.fhstp.bad.pgmon;

import at.ac.fhstp.bad.pgmon.entities.PokemonType;
import at.ac.fhstp.bad.pgmon.util.PokemonDataImporter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class DataImportTests {

    @Autowired
    PokemonDataImporter pokemonDataImporter;

    @Test
    public void testGetTypeNameENbyTypeId(){
         assertEquals("Normal", pokemonDataImporter.getTypeNameEN(1));
         assertEquals("Fighting", pokemonDataImporter.getTypeNameEN(2));
         assertEquals("Fairy", pokemonDataImporter.getTypeNameEN(18));
    }

    @Test
    public void testGetTypeNameDEbyTypeId(){
        assertEquals("Normal", pokemonDataImporter.getTypeNameDE(1));
        assertEquals("Kampf", pokemonDataImporter.getTypeNameDE(2));
        assertEquals("Fee", pokemonDataImporter.getTypeNameDE(18));
    }


    @Test
    public void testGetTypeIdsBySpeciesId(){
        Integer[] typesIdsFor2 = {12,4};
        assertArrayEquals(typesIdsFor2, pokemonDataImporter.getTypeIds(2).toArray());

        Integer[] typesIdsFor293 = {1};
        assertArrayEquals(typesIdsFor293, pokemonDataImporter.getTypeIds(293).toArray());

        Integer[] typesIdsFor799 = {17,16};
        assertArrayEquals(typesIdsFor799, pokemonDataImporter.getTypeIds(799).toArray());
    }


    @Test
    public void testGetTypesBySpeciesId(){

        List<PokemonType> result = pokemonDataImporter.getTypes(2);
        result.sort((PokemonType o1, PokemonType o2)->o1.getTypeId().compareTo(o2.getTypeId()));

        assertEquals(2,result.size());

        assertEquals(4, result.get(0).getTypeId());
        assertEquals("Poison", result.get(0).getTypeName_en());
        assertEquals("Gift", result.get(0).getTypeName_de());

        assertEquals(12, result.get(1).getTypeId());
        assertEquals("Grass", result.get(1).getTypeName_en());
        assertEquals("Pflanze", result.get(1).getTypeName_de());


        result = pokemonDataImporter.getTypes(140);
        result.sort((PokemonType o1, PokemonType o2)->o1.getTypeId().compareTo(o2.getTypeId()));

        assertEquals(2,result.size());

        assertEquals(6, result.get(0).getTypeId());
        assertEquals("Rock", result.get(0).getTypeName_en());
        assertEquals("Gestein", result.get(0).getTypeName_de());

        assertEquals(11, result.get(1).getTypeId());
        assertEquals("Water", result.get(1).getTypeName_en());
        assertEquals("Wasser", result.get(1).getTypeName_de());

    }

    @Test
    public void  testGetNameDeBySpeciesId(){
        assertEquals("Taubsi", pokemonDataImporter.getNameDE(16));
        assertEquals("Sandamer", pokemonDataImporter.getNameDE(28));
        assertEquals("Trikephalo", pokemonDataImporter.getNameDE(635));
    }

    @Test
    public void  testGetNameEnBySpeciesId(){
        assertEquals("Pidgey", pokemonDataImporter.getNameEN(16));
        assertEquals("Sandslash", pokemonDataImporter.getNameEN(28));
        assertEquals("Hydreigon", pokemonDataImporter.getNameEN(635));
    }
}
