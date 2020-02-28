package at.ac.fhstp.bad.pgmon.persistence;

import at.ac.fhstp.bad.pgmon.entities.Species;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeciesRepository extends CrudRepository<Species,Integer> {

}

