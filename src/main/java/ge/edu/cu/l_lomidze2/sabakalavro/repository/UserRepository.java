package ge.edu.cu.l_lomidze2.sabakalavro.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import ge.edu.cu.l_lomidze2.sabakalavro.model.User;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {

}
