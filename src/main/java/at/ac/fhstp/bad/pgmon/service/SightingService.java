package at.ac.fhstp.bad.pgmon.service;

import at.ac.fhstp.bad.pgmon.entities.Sighting;

import java.util.List;
import java.util.Optional;

public interface SightingService {

    void saveSighting(Sighting sighting);
    Optional<Sighting> getSightingById(Long sightingId);
    List<Sighting> getSightingsInArea(Double north, Double south, Double west, Double east);

}
