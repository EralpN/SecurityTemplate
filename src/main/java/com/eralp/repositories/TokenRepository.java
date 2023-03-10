package com.eralp.repositories;

import com.eralp.entities.Token;
import com.eralp.repositories.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * This is a repository interface for {@link Token} entity.
 * {@link Repository} annotation is used to indicate that this interface is a repository bean.
 *
 * @author Eralp Nitelik
 */
public interface TokenRepository extends BaseRepository<Token, String> {
    @Query("SELECT t FROM Token t WHERE t.user.id = ?1 AND (t.loggedOut = false AND t.revoked = false)")
    List<Token> findAllValidTokensByUser(String userId);

    Optional<Token> findByToken(String token);
}
