package com.hoursmanager.HoursManager.converters;

import com.hoursmanager.HoursManager.enums.WeekDayEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class WeekDayEnumConverter implements AttributeConverter<WeekDayEnum, String>
{
    @Override
    public String convertToDatabaseColumn(WeekDayEnum attribute) {

        if (attribute == null)
        {
            return null;
        }

        return attribute.getWeekDay();
    }

    @Override
    public WeekDayEnum convertToEntityAttribute(String dbData) {

        if (dbData == null)
        {
            return null;
        }

        return WeekDayEnum.fromValue(dbData);
    }
}
