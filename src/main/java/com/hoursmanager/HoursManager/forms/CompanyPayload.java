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
public class CompanyPayload
{
    @JsonProperty("companyName")
    private String companyName;

    @JsonProperty("companyContactEmail")
    private String companyContactEmail;

    @JsonProperty("companyContactPhoneNumber")
    private String companyContactPhoneNumber;
}

