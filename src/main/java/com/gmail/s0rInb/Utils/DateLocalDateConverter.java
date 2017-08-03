//package com.gmail.s0rInb.Utils;
//
//import javax.persistence.AttributeConverter;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.Date;
//
//public class DateLocalDateConverter implements AttributeConverter<LocalDate, Date> {
//	@Override
//	public Date convertToDatabaseColumn(LocalDate attribute) {
//		if (attribute == null) return null;
//		return Date.from(attribute.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
//	}
//
//	@Override
//	public LocalDate convertToEntityAttribute(Date dbData) {
//		if (dbData == null) return null;
//		return LocalDateTime.ofInstant(dbData.toInstant(), ZoneId.systemDefault()).toLocalDate();
//	}
//}
