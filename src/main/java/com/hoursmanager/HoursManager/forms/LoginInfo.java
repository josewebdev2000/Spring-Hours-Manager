package com.hoursmanager.HoursManager.forms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Data class meant to represent the JSON payload sent from the front-end to the POST /login route
 * It provides the required info to login the user to authenticate him/her
 * */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo
{
    @JsonProperty("email")
    public String email;

    @JsonProperty("password")
    public String password;
}
