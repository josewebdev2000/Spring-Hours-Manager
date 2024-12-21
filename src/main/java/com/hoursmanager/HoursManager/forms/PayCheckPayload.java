package com.hoursmanager.HoursManager.forms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayCheckPayload
{
    @JsonProperty("payCheckPaymentDay")
    private String payCheckPaymentDay;

    @JsonProperty("payCheckTotalPayment")
    private float payCheckTotalPayment;

    @JsonProperty("payCheckTips")
    private float payCheckTips;
}