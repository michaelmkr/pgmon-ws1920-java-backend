package at.ac.fhstp.bad.pgmon.rest.sightings;

import at.ac.fhstp.bad.pgmon.entities.Sighting;
import at.ac.fhstp.bad.pgmon.rest.sightings.exceptions.SightingNotFoundException;
import at.ac.fhstp.bad.pgmon.rest.sightings.requestmodel.SightingPostRequest;
import at.ac.fhstp.bad.pgmon.rest.sightings.responseModel.SightingResponse;
import at.ac.fhstp.bad.pgmon.service.SightingService;
import at.ac.fhstp.bad.pgmon.util.RestDTOMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sightings")
public class SightingsEndpoint {

    private SightingService sightingService;

    public SightingsEndpoint(SightingService sightingService){
        this.sightingService = sightingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SightingResponse postSighting(@RequestBody SightingPostRequest sightingPostRequest){

        Sighting sighting = RestDTOMapper.sightingPostRequest2Sighting(sightingPostRequest);
        sightingService.saveSighting(sighting);

        return RestDTOMapper.sighting2SightingResponse(sighting);

    }


    @GetMapping("/{sighting-id}")
    public SightingResponse getSightings(@PathVariable("sighting-id") Long sightingId){
        Optional<Sighting> sightingResult = sightingService.getSightingById(sightingId);

        Sighting sighting;
        if(sightingResult.isPresent()){
            sighting = sightingResult.get();
        } else {
            throw new SightingNotFoundException("Sighting " + sightingId + " not found!");
        }

        return RestDTOMapper.sighting2SightingResponse(sighting);

    }

    @PatchMapping("/{sighting-id}")
    public SightingResponse editSighting(@PathVariable("sighting-id") Long sightingId,
                                         @RequestBody SightingPostRequest sightingPostRequest){
        Optional<Sighting> sightingResult = sightingService.getSightingById(sightingId);

        Sighting sighting;
        if(sightingResult.isPresent()){
            sighting = sightingResult.get();
        } else {
            throw new SightingNotFoundException("Sighting " + sightingId + " not found!");
        }

        if(sightingPostRequest.getPokedexId()!=null) {
            sighting.setPokedexId(sightingPostRequest.getPokedexId());
        }
        if(sightingPostRequest.getPosition()!=null){
            if(sightingPostRequest.getPosition().getLng()!=null){
                sighting.setLng(sightingPostRequest.getPosition().getLng());
            }
            if(sightingPostRequest.getPosition().getLat()!=null){
                sighting.setLat(sightingPostRequest.getPosition().getLat());
            }
        }

        sightingService.saveSighting(sighting);

        return RestDTOMapper.sighting2SightingResponse(sighting);

    }

    @GetMapping("/map/{north},{east},{south},{west}")
    public List<SightingResponse> getSightingsOnMap(@PathVariable("north") Double north,
                                                    @PathVariable("east") Double east,
                                                    @PathVariable("south") Double south,
                                                    @PathVariable("west") Double west){

        List<Sighting> results = sightingService.getSightingsInArea(north, south, west, east);

        List<SightingResponse> sightingResponses = new ArrayList<>();

        for(Sighting sighting: results){
            sightingResponses.add(RestDTOMapper.sighting2SightingResponse(sighting));
        }

        return sightingResponses;

    }

}
