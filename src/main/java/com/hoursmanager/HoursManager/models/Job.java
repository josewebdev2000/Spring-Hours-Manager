package com.hoursmanager.HoursManager.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "Job",
        schema = "spring_hours_manager_db"
)
public class Job
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JobID", nullable = false)
    private Long jobId;

    @Column(name = "JobName", nullable = false)
    private String jobName;

    @Column(name = "jobDescription", nullable = true)
    private String jobDescription;

    @OneToMany(mappedBy="payRateJob", cascade = CascadeType.ALL)
    private List<PayRate> payRates;

    @OneToMany(mappedBy="payCheckJob", cascade = CascadeType.ALL)
    private List<PayCheck> payChecks;

    @OneToMany(mappedBy="workingDayJob", cascade = CascadeType.ALL)
    private List<WorkingDay> workingDays;

    @OneToMany(mappedBy="workSessionJob", cascade = CascadeType.ALL)
    private List<WorkSession> workSessions;

    @ManyToOne
    @JoinColumn(name = "JobUserID", nullable = false)
    private SpringUser jobUser;

    @ManyToOne
    @JoinColumn(name = "JobCompanyID", nullable = false)
    private Company jobCompany;
}
