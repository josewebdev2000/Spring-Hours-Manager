package com.hoursmanager.HoursManager.models;

import com.hoursmanager.HoursManager.converters.WeekDayEnumConverter;
import com.hoursmanager.HoursManager.enums.WeekDayEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "PayCheck",
        schema="spring_hours_manager_db"
)
public class PayCheck
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PayCheckID", nullable = false)
    private Long payCheckId;

    @Convert(converter = WeekDayEnumConverter.class)
    @Column(name = "PayCheckPaymentDay", nullable = false)
    private WeekDayEnum payCheckPaymentDay;

    @Column(name = "PayCheckTotalPayment", precision = 10, scale = 2, nullable = false)
    private BigDecimal payCheckTotalPayment;

    @Column(name = "PayCheckTips", precision = 10, scale = 2, nullable = false)
    private BigDecimal payCheckTips = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "PayCheckUserID", nullable = false)
    private SpringUser payCheckUser;

    @ManyToOne
    @JoinColumn(name = "PayCheckJobID", nullable = false)
    private Job payCheckJob;
}
