package com.hoursmanager.HoursManager.repositories;

import com.hoursmanager.HoursManager.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>
{
}
