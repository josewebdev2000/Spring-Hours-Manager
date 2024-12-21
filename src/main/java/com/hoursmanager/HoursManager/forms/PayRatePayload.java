package com.hoursmanager.HoursManager.forms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayRatePayload
{
    @JsonProperty("payRateType")
    private String payRateType;

    @JsonProperty("payRateAmount")
    private float payRateAmount;
}