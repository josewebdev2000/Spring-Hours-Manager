package com.hoursmanager.HoursManager.repositories;

import com.hoursmanager.HoursManager.models.PayRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRateRepository extends JpaRepository<PayRate, Long>
{
}
