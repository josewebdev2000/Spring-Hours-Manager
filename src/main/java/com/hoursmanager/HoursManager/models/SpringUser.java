package com.hoursmanager.HoursManager.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name="SpringUser",
        schema="spring_hours_manager_db",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "email_unique",
                        columnNames="SpringUserEmail"
                )
        }
)
public class SpringUser
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SpringUserID", nullable = false)
    private Long springUserId;

    public SpringUser(String springUserName, String springUserEmail, String springUserPassword)
    {
        this.springUserName = springUserName;
        this.springUserEmail = springUserEmail;
        this.springUserPassword = springUserPassword;
    }

    @Column(name="SpringUserName", nullable = false)
    private String springUserName;

    @Column(name="SpringUserEmail", nullable = false)
    private String springUserEmail;

    @Column(name="SpringUserPassword", nullable = false)
    private String springUserPassword;

    @Column(name="SpringUserPicUrl", nullable = true)
    private String springUserPicUrl;

    @OneToMany(mappedBy="passwordTokenUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PasswordResetToken> passwordResetTokens;

    @OneToMany(mappedBy="companyUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Company> companies;

    @OneToMany(mappedBy="jobUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Job> jobs;

    @OneToMany(mappedBy="payCheckUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PayCheck> payChecks;

    @OneToMany(mappedBy="payRateUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PayRate> payRates;

    @OneToMany(mappedBy="workingDayUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkingDay> workingDays;

    @OneToMany(mappedBy="workSessionUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkSession> workSessions;
}
