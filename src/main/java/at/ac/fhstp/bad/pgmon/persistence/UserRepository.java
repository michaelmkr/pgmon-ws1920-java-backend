package at.ac.fhstp.bad.pgmon.persistence;


import at.ac.fhstp.bad.pgmon.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

    User findByEmailIgnoreCase(String email);

}
