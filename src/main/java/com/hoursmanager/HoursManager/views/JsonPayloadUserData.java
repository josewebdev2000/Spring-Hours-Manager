package com.hoursmanager.HoursManager.views;

import com.hoursmanager.HoursManager.dto.SpringUserDto;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class JsonPayloadUserData
{
    private final HashMap<String, String> payload;
    public JsonPayloadUserData(SpringUserDto springUserDto)
    {
        this.payload = new JsonPayloadResponse().payload("id", String.valueOf(springUserDto.getId()));
        this.payload.put("name", springUserDto.getName());
        this.payload.put("picUrl", springUserDto.getPicUrl());
    }
}
