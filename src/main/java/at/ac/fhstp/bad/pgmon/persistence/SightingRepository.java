package at.ac.fhstp.bad.pgmon.persistence;

import at.ac.fhstp.bad.pgmon.entities.Sighting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SightingRepository extends CrudRepository<Sighting,Long> {

    @Query("select s from Sighting s where s.lat <= :north AND "
            + "s.lng <= :east AND "
            + "s.lat >= :south AND "
            + "s.lng >= :west")
    public List<Sighting> findByArea(@Param("north") Double north,
                                     @Param("south") Double south,
                                     @Param("west") Double west,
                                     @Param("east") Double east);
}
