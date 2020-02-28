package at.ac.fhstp.bad.pgmon.rest.helloworld;

import at.ac.fhstp.bad.pgmon.entities.HelloMessage;
import at.ac.fhstp.bad.pgmon.rest.helloworld.requestmodel.HelloMessageRequestDto;
import at.ac.fhstp.bad.pgmon.rest.helloworld.responsemodel.HelloJsonResponseDto;
import at.ac.fhstp.bad.pgmon.service.HelloWorldService;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/helloworld")
public class HelloWorldEndpoint {

    private static Logger logger = LoggerFactory.getLogger(HelloWorldEndpoint.class);

    private HelloWorldService helloWorldService;

    public HelloWorldEndpoint(HelloWorldService helloWorldService){
        this.helloWorldService = helloWorldService;
    }

    @GetMapping(value="/hi")
    @ResponseStatus(HttpStatus.OK)
    public String sayhi(){
        return "Hello World!";
    }

    @GetMapping(value="/ciao")
    @ResponseStatus(HttpStatus.OK)
    public String sayciao(){
        return "Ciao World!";
    }

    @GetMapping(value="/hijson")
    @ResponseStatus(HttpStatus.OK)
    public HelloJsonResponseDto sayhijson(){
        HelloJsonResponseDto helloJsonResponseDto = new HelloJsonResponseDto();
        helloJsonResponseDto.setMessage("Hello World!");
        helloJsonResponseDto.setTimestamp(Instant.now());
        return helloJsonResponseDto;
    }

    @PostMapping(value="/hi")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void hiMessage(@RequestBody HelloMessageRequestDto helloMessageRequestDto){
        logger.info("messageDE: {}", helloMessageRequestDto.getMessageDE());
        logger.info("messageEN: {}", helloMessageRequestDto.getMessageEN());

        HelloMessage helloMessage = new HelloMessage();
        helloMessage.setMessageDE(helloMessageRequestDto.getMessageDE());
        helloMessage.setMessageEN(helloMessageRequestDto.getMessageEN());

        helloWorldService.save(helloMessage);
    }
}
