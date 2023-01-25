package com.eralp.repositories;

import com.eralp.entities.Auth;
import com.eralp.repositories.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This is a repository interface for {@link Auth} entity.
 * {@link Repository} annotation is used to indicate that this interface is a repository bean.
 *
 * @author Eralp Nitelik
 */
@Repository
public interface AuthRepository extends BaseRepository<Auth, Long> {
    @Query("SELECT u FROM Auth u WHERE u.state = 'ACTIVE' AND u.email = ?1")
    Optional<Auth> findByEmail(String email);
}
