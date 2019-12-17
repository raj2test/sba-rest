package com.fsd.sba.util;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

	@Override
    public java.sql.Date convertToDatabaseColumn(LocalDate attribute) {
       
        return attribute == null ? null : java.sql.Date.valueOf(attribute);
    }

    @Override
    public java.time.LocalDate convertToEntityAttribute(Date dbData) {
     
        return dbData == null ? null : dbData.toLocalDate();
    }
    
}
