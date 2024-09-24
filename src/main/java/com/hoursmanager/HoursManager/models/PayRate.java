package com.hoursmanager.HoursManager.models;

import com.hoursmanager.HoursManager.enums.PayRateEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name="PayRate",
        schema="spring_hours_manager_db"
)
public class PayRate
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PayRateID", nullable = false)
    private Long payRateId;

    @Enumerated(EnumType.STRING)
    @Column(name = "PayRateType", nullable = false)
    private PayRateEnum payRateType;

    @Column(name = "PayRateAmount", precision=10, scale=2, nullable = false)
    private BigDecimal payRateAmount;

    @ManyToOne
    @JoinColumn(name = "PayRateUserID", nullable = false)
    private SpringUser payRateUser;

    @ManyToOne
    @JoinColumn(name = "PayRateJobID", nullable = false)
    private Job payRateJob;

}
