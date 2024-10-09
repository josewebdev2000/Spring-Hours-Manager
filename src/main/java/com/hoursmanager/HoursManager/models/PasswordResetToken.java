package com.hoursmanager.HoursManager.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name="PasswordResetToken",
        schema="spring_hours_manager_db",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "token_unique",
                        columnNames = "Token"
                )
        }
)
public class PasswordResetToken
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PasswordResetTokenID", nullable = false)
    private Long passwordResetTokenId;

    @Column(name = "Token", nullable = false)
    private String token;

    @Column(name = "ExpiryDate", nullable = false)
    private LocalDateTime expiryDate;

    @Column(name = "CreatedAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Set associations
    @ManyToOne
    @JoinColumn(name = "SpringUserID", nullable = false)
    private SpringUser passwordTokenUser;

}
