package com.hoursmanager.HoursManager.repositories;

import com.hoursmanager.HoursManager.models.PayCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayCheckRepository extends JpaRepository<PayCheck, Long>
{
}
