package at.ac.fhstp.bad.pgmon.service;

import at.ac.fhstp.bad.pgmon.entities.HelloMessage;
import at.ac.fhstp.bad.pgmon.persistence.HelloWorldRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class HelloWorldServiceImpl implements HelloWorldService {

    private static Logger logger = LoggerFactory.getLogger(HelloWorldServiceImpl.class);

    private HelloWorldRepository helloWorldRepository;

    public HelloWorldServiceImpl(HelloWorldRepository helloWorldRepository){
        this.helloWorldRepository = helloWorldRepository;
    }

    @Override
    public void save(HelloMessage helloMessage) {
        helloMessage.setCreated_at(Instant.now());
        helloWorldRepository.save(helloMessage);
    }
}
