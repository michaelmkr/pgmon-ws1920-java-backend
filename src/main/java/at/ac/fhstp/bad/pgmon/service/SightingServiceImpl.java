package at.ac.fhstp.bad.pgmon.service;

import at.ac.fhstp.bad.pgmon.entities.Sighting;
import at.ac.fhstp.bad.pgmon.persistence.SightingRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SightingServiceImpl implements SightingService {

    private SightingRepository sightingRepository;

    public SightingServiceImpl(SightingRepository sightingRepository){
        this.sightingRepository = sightingRepository;
    }


    @Override
    public void saveSighting(Sighting sighting) {
        sighting.setTimestamp(new Date());
        sightingRepository.save(sighting);
    }

    @Override
    public Optional<Sighting> getSightingById(Long sightingId) {
        return sightingRepository.findById(sightingId);
    }

    @Override
    public List<Sighting> getSightingsInArea(Double north, Double south, Double west, Double east) {
        return sightingRepository.findByArea(north, south, west, east);
    }

}
