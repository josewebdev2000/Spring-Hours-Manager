package com.hoursmanager.HoursManager.views;

import com.hoursmanager.HoursManager.views.JsonPayloadResponse;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class JsonPayloadSuccessResponse
{
    private final HashMap<String, String> payload;
    public JsonPayloadSuccessResponse(String message)
    {
        this.payload = new JsonPayloadResponse().payload("success", message);
    }
}
