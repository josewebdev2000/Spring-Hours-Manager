package com.hoursmanager.HoursManager.views;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class JsonPayloadRedirectUrl
{
    private final HashMap<String, String> payload;

    public JsonPayloadRedirectUrl(String redirectUrl)
    {
        this.payload = new JsonPayloadResponse().payload("redirectUrl", redirectUrl);
    }
}
