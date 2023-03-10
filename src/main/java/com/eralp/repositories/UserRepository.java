package com.eralp.repositories;

import com.eralp.entities.User;
import com.eralp.repositories.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This is a repository interface for {@link User} entity.
 * {@link Repository} annotation is used to indicate that this interface is a repository bean.
 *
 * @author Eralp Nitelik
 */
@Repository
public interface UserRepository extends BaseRepository<User, String> {
    @Query("SELECT u FROM User u WHERE u.state = 'ACTIVE' AND u.email = ?1")
    Optional<User> findActiveUserByEmail(String email);
}
