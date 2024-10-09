package com.hoursmanager.HoursManager.repoImp;

import com.hoursmanager.HoursManager.exceptions.InvalidResetPasswordToken;
import com.hoursmanager.HoursManager.exceptions.UserNotFound;
import com.hoursmanager.HoursManager.models.PasswordResetToken;
import com.hoursmanager.HoursManager.models.SpringUser;
import com.hoursmanager.HoursManager.repositories.PasswordResetTokenRepository;
import com.hoursmanager.HoursManager.repositories.SpringUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetTokenRepoImp
{
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private SpringUserRepository springUserRepository;

    // Create and save a new token
    public String createPasswordResetToken(Long userId)
    {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(UUID.randomUUID().toString());
        passwordResetToken.setExpiryDate(LocalDateTime.now().plusMinutes(15));
        passwordResetToken.setPasswordTokenUser(springUserRepository.findById(userId).orElseThrow(() -> new UserNotFound("User of id " + userId + " could not be found")));
        passwordResetToken.setCreatedAt(LocalDateTime.now());
        passwordResetTokenRepository.save(passwordResetToken);
        return passwordResetToken.getToken();
    }

    // Validate token
    public boolean validatePasswordResetToken(String token)
    {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token).orElse(null);
        return passwordResetToken != null && passwordResetToken.getExpiryDate().isAfter(LocalDateTime.now());
    }

    // Get SpringUser by token
    public SpringUser getSpringUserByToken(String token)
    {
        Optional<PasswordResetToken> resetToken = passwordResetTokenRepository.findByToken(token);

        if (resetToken.isPresent())
        {
            return resetToken.get().getPasswordTokenUser();
        }

        else
        {
            throw new InvalidResetPasswordToken("The given reset password token is invalid");
        }
    }

    // Delete token
    public void deletePasswordResetToken(String token)
    {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken((token)).orElse(null);
        if (passwordResetToken != null)
        {
            passwordResetTokenRepository.deleteByToken(token);
        }
    }

    // Bulk delete all tokens of a user
    @Transactional
    public void deleteAllPasswordResetTokensBySpringUser(SpringUser springUser)
    {
        passwordResetTokenRepository.deleteAllByPasswordTokenUser(springUser);
    }

}
