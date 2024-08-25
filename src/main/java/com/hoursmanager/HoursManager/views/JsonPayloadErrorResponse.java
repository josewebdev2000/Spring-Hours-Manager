package com.hoursmanager.HoursManager.views;

import com.hoursmanager.HoursManager.views.JsonPayloadResponse;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class JsonPayloadErrorResponse
{
    private final HashMap<String, String> payload;
    public JsonPayloadErrorResponse(String message)
    {
        this.payload = new JsonPayloadResponse().payload("error", message);
    }
}
