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
public class AddJobPayload
{
    @JsonProperty("springUserId")
    private Long springUserId;

    @JsonProperty("company")
    private CompanyPayload companyPayload;

    @JsonProperty("job")
    private JobPayload jobPayload;

    @JsonProperty("payRate")
    private PayRatePayload payRatePayload;

    @JsonProperty("payCheck")
    private PayCheckPayload payCheckPayload;
}
