package com.hoursmanager.HoursManager.repositories;

import com.hoursmanager.HoursManager.models.Job;
import com.hoursmanager.HoursManager.models.SpringUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JobRepository extends JpaRepository<Job, Long>
{
    // Custom query to count the number of jobs for a given user
    @Query("SELECT COUNT(j) FROM Job j WHERE j.jobUser = :springUser")
    Long countBySpringUser(SpringUser springUser);
}
