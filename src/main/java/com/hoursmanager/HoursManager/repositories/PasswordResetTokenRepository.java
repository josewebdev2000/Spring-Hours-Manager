package com.hoursmanager.HoursManager.repositories;

import com.hoursmanager.HoursManager.models.PasswordResetToken;
import com.hoursmanager.HoursManager.models.SpringUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>
{
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByToken(String token);

    @Modifying
    @Transactional
    @Query("DELETE FROM PasswordResetToken p WHERE p.passwordTokenUser = :passwordTokenUser")
    void deleteAllByPasswordTokenUser(SpringUser passwordTokenUser);
}
