package com.hoursmanager.HoursManager.forms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Data class meant to represent the JSON payload sent from the front-end to the POST /register route
 * It provides the required info to register a new user to the DB
 * */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterInfo
{
    @JsonProperty("name")
    public String name;

    @JsonProperty("email")
    public String email;

    @JsonProperty("password")
    public String password;
}
