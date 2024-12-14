package com.hoursmanager.HoursManager.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/* Show Data about the Spring User */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobView
{
    private Long jobId;
    private String jobName;
    private String jobDescription;
    private String companyName;
    private String formattedPayRate;
    private String payCheckDay;
}
