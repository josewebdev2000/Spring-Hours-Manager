package com.hoursmanager.HoursManager.views.details;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayRateDetailsView
{
    private String payRateType;
    private BigDecimal payRateAmount;
}
