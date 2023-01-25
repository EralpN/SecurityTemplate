package com.eralp.repositories.base;

import com.eralp.entities.base.BaseEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * This is an interface that defines basic CRUD functionality for entities that extend BaseEntity.
 * It extends JpaRepository and provides additional methods to find entities by their state and ordering.
 * {@link NoRepositoryBean} annotation is used to indicate that this interface is not a repository bean.
 *
 * @param <T>  The type of the entity the repository manages
 * @param <Id> The type of the entity's id
 * @author Eralp Nitelik
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity, Id> extends JpaRepository<T, Id> {

    /*
        Find Methods For Active Entities
     */

    @Query("SELECT t FROM #{#entityName} t WHERE t.state = 'ACTIVE' AND t.id = ?1")
    Optional<T> findActiveById(@NonNull Id id);

    @Query("SELECT t FROM #{#entityName} t WHERE t.state = 'ACTIVE' ORDER BY t.updatedAt DESC")
    List<T> findAllActive();

    @Query("SELECT t FROM #{#entityName} t WHERE t.state = 'ACTIVE' ORDER BY t.updatedAt DESC")
    Page<T> findAllActive(@NonNull Pageable pageable);

    /*
        Find Methods For Deleted Entities
     */

    @Query("SELECT t FROM #{#entityName} t WHERE t.state = 'DELETED' AND t.id = ?1")
    Optional<T> findDeletedById(Id id);

    @Query("SELECT t FROM #{#entityName} t WHERE t.state = 'DELETED' ORDER BY t.updatedAt DESC")
    List<T> findAllDeleted();

    @Query("SELECT t FROM #{#entityName} t WHERE t.state = 'DELETED' ORDER BY t.updatedAt DESC")
    Page<T> findAllDeleted(Pageable pageable);

    /*
        Modified Delete Methods
     */

    /**
     * Instead of removing from database this method changes entity state.
     *
     * @param entity entity
     * @return {@link Integer} number of modified entries
     * @author Eralp Nitelik
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE #{#entityName} t SET t.state = 'DELETED' WHERE t = ?1", nativeQuery = true)
    int softDelete(T entity);

    /**
     * Instead of removing from database this method changes entity state.
     *
     * @param id id
     * @return {@link Integer} number of modified entries
     * @author Eralp Nitelik
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE #{#entityName} t SET t.state = 'DELETED' WHERE t.id = ?1", nativeQuery = true)
    int softDeleteById(Id id);

    /**
     * Instead of removing from database this method changes entity state.
     *
     * @param entities {@link Iterable} entities
     * @return {@link Integer} number of modified entries
     * @author Eralp Nitelik
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE #{#entityName} t SET t.state = 'DELETED' WHERE t IN ?1", nativeQuery = true)
    int softDeleteAll(Iterable<T> entities);

    /**
     * Instead of removing from database this method changes entity state.
     *
     * @param ids {@link Iterable} ids
     * @return {@link Integer} number of modified entries
     * @author Eralp Nitelik
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE #{#entityName} t SET t.state = 'DELETED' WHERE t.id IN ?1", nativeQuery = true)
    int softDeleteAllById(Iterable<Id> ids);

    /**
     * Deletes the entity.
     *
     * @param id the id of the entity that will be deleted
     * @return {@link Integer} number of modified entries
     * @author Eralp Nitelik
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM #{#entityName} t WHERE t.id = ?1")
    int hardDeleteById(Id id);

    /*
        Overridden Delete Methods
     */

    /**
     * Throws {@link UnsupportedOperationException}. Use {@link #softDelete(T)}} instead.
     *
     * @author Eralp Nitelik
     */
    @Override
    default void delete(@NonNull T entity) {
        throw new UnsupportedOperationException("Use soft delete instead.");
    }

    /**
     * Throws {@link UnsupportedOperationException}. Use {@link #softDeleteById(Id)} instead.
     *
     * @author Eralp Nitelik
     */
    @Override
    default void deleteById(@NonNull Id id) {
        throw new UnsupportedOperationException("Use soft delete instead.");
    }

    /**
     * Throws {@link UnsupportedOperationException}. Use {@link #softDeleteAll(Iterable)} instead.
     *
     * @author Eralp Nitelik
     */
    @Override
    default void deleteAll(@NonNull Iterable<? extends T> entities) {
        throw new UnsupportedOperationException("Use soft delete instead.");
    }

    /**
     * Throws {@link UnsupportedOperationException}. Use {@link #softDeleteAllById(Iterable)} instead.
     *
     * @author Eralp Nitelik
     */
    @Override
    default void deleteAllById(@NonNull Iterable<? extends Id> ids) {
        throw new UnsupportedOperationException("Use soft delete instead.");
    }

}
