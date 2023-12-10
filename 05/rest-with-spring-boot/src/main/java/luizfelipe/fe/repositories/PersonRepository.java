package luizfelipe.fe.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import luizfelipe.fe.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
