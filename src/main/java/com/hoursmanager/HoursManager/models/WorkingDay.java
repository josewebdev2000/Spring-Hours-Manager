package com.hoursmanager.HoursManager.models;

import com.hoursmanager.HoursManager.enums.WeekDayEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "WorkingDay",
        schema = "spring_hours_manager_db"
)
public class WorkingDay
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WorkingDayID", nullable = false)
    private Long workingDayId;

    @Enumerated(EnumType.STRING)
    @Column(name = "WorkingDayName", nullable = false)
    private WeekDayEnum workingDayName;

    @ManyToOne
    @JoinColumn(name = "WorkingDayUserID", nullable = false)
    private SpringUser workingDayUser;

    @ManyToOne
    @JoinColumn(name = "WorkingDayJobID", nullable = false)
    private Job workingDayJob;
}
