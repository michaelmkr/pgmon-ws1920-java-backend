package at.ac.fhstp.bad.pgmon.persistence;

import at.ac.fhstp.bad.pgmon.entities.HelloMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelloWorldRepository extends CrudRepository<HelloMessage, Long> {

}
