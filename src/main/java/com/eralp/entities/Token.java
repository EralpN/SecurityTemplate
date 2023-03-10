package com.eralp.entities;

import com.eralp.entities.base.BaseEntity;
import com.eralp.entities.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

/**
 * This is an entity class that represents a {@link Token} in the system.
 * It extends {@link BaseEntity} to keep necessary data like when it was created.
 * It contains fields which are required to see if the token is still usable.
 * It provides the necessary logout functionality to the program.
 *
 * @author Eralp Nitelik
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "tokens")
public class Token extends BaseEntity {
    @Column(nullable = false)
    private String token;

    @Column(name = "token_type")
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @Column(name = "logged_out")
    private boolean loggedOut;

    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
