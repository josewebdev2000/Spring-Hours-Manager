package com.hoursmanager.HoursManager.repositories;

import com.hoursmanager.HoursManager.models.WorkingDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkingDayRepository extends JpaRepository<WorkingDay, Long> {
}
