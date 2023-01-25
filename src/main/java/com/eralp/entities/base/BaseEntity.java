package com.eralp.entities.base;

import com.eralp.entities.enums.State;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

/**
 * This is an abstract class that represents basic properties of entities in the system.
 * This class is intended to be extended by other entities in the system.
 * {@link MappedSuperclass} annotation is used to indicate that this class should not be mapped to a table in the database.
 *
 * @author Eralp Nitelik
 */
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@RequiredArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    /*
        Saves as 0 if auditor is anonymous.
     */
    @CreatedBy
    @Column(name = "created_by")
    private Long createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private Long updatedBy;

    @Enumerated(EnumType.STRING)
    @Nonnull
    private State state;

    /**
     * This method is annotated with {@link PrePersist} which makes this method to be executed before the object is persisted.
     *
     * @author Eralp Nitelik
     */
    @PrePersist
    public void prePersist() {
        setCreatedAt(new Date());
        setUpdatedAt(new Date());
        setState(State.ACTIVE);
    }

    /**
     * It is annotated with {@link PreUpdate} which means that it is called before the update operation is executed.
     *
     * @author Eralp Nitelik
     */
    @PreUpdate
    public void preUpdate() {
        setUpdatedAt(new Date());
    }
}
