package com.hoursmanager.HoursManager.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
      name = "WorkSession",
      schema = "spring_hours_manager_db"
)
public class WorkSession
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WorkSessionID", nullable = false)
    private Long workSessionId;

    @Column(name = "WorkSessionStartTime", nullable = false)
    private LocalDateTime workSessionStartTime;

    @Column(name = "WorkSessionEndTime", nullable = true)
    private LocalDateTime workSessionEndTime;

    @Column(name = "WorkSessionDuration", nullable = true)
    private LocalDateTime workSessionDuration;

    @ManyToOne
    @JoinColumn(name = "WorkSessionUserID", nullable = false)
    private SpringUser workSessionUser;

    @ManyToOne
    @JoinColumn(name = "WorkSessionJobID", nullable = false)
    private Job workSessionJob;
}
