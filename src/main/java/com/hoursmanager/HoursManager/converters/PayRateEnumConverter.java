package com.hoursmanager.HoursManager.converters;

import com.hoursmanager.HoursManager.enums.PayRateEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PayRateEnumConverter implements AttributeConverter<PayRateEnum, String>
{

    @Override
    public String convertToDatabaseColumn(PayRateEnum attribute) {

        if (attribute == null)
        {
            return null;
        }

        return attribute.getPayRateType();
    }

    @Override
    public PayRateEnum convertToEntityAttribute(String dbData) {
        if (dbData == null)
        {
            return null;
        }

        return PayRateEnum.fromValue(dbData);
    }
}
