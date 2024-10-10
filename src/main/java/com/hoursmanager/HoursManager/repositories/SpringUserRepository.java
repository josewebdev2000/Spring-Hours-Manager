package com.hoursmanager.HoursManager.repositories;

import com.hoursmanager.HoursManager.models.SpringUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringUserRepository extends JpaRepository<SpringUser, Long>
{
    // Method to find user by email
    SpringUser findSpringUserBySpringUserEmail(String springUserEmail);

    // Method to update user's profile Picture
}
