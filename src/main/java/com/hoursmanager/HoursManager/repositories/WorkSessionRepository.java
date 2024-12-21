package com.hoursmanager.HoursManager.repositories;

import com.hoursmanager.HoursManager.models.WorkSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkSessionRepository extends JpaRepository<WorkSession, Long>
{
}
