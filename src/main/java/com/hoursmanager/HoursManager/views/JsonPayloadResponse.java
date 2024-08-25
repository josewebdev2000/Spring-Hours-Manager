package com.hoursmanager.HoursManager.views;

import com.hoursmanager.HoursManager.views.interfaces.ViewsInterface;

import java.util.HashMap;
public class JsonPayloadResponse implements ViewsInterface
{
    @Override
    public HashMap<String, String> payload(String key, String value)
    {

        // Define the response Hash Map
        HashMap<String, String> jsonPayload = new HashMap<String, String>();

        // Add the key value pairs
        jsonPayload.put(key, value);

        // Return the Hash Map
        return jsonPayload;
    }
}
