package ge.edu.cu.l_lomidze2.sabakalavro.repository;

import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import ge.edu.cu.l_lomidze2.sabakalavro.model.User;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
