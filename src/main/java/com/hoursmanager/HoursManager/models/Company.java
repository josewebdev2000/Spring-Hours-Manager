package com.hoursmanager.HoursManager.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "Company",
        schema = "spring_hours_manager_db"
)
public class Company
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "CompanyID", nullable = false)
    private Long companyId;

    @Column(name = "CompanyName", nullable = false)
    private String companyName;

    @Column(name = "CompanyContactEmail", nullable = true)
    private String companyContactEmail;

    @Column(name = "CompanyContactPhoneNumber", nullable = true)
    private String companyContactPhoneNumber;

    @ManyToOne
    @JoinColumn(name = "CompanyUserID", nullable = false)
    private SpringUser companyUser;
}
