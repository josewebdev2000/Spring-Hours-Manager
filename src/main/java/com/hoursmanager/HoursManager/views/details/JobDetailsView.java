package com.hoursmanager.HoursManager.views.details;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobDetailsView
{
    private String jobName;
    private String jobDescription;
    private CompanyDetailsView companyDetailsView;
    private PayRateDetailsView payRateDetailsView;
    private PayCheckDetailsView payCheckDetailsView;
}
