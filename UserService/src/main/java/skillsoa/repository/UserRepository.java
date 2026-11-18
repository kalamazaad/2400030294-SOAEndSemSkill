
package skillsoa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import skillsoa.model.Users;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);

    @Query("SELECT u FROM Users u WHERE u.email_id = :email")
    Optional<Users> findByEmail(@Param("email") String email);
}
