package com.hoursmanager.HoursManager.views.details;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayCheckDetailsView
{
    private String payCheckPaymentDay;
    private BigDecimal payCheckTotalPayment;
    private BigDecimal payCheckTips;
}
