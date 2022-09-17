package com.anything.type.converter;

import java.util.EnumSet;
import java.util.NoSuchElementException;

import javax.persistence.AttributeConverter;

import com.anything.type.CodeValue;

public class StringTypeConverter<E extends Enum<E> & CodeValue> implements AttributeConverter<E, String> {

	private Class<E> eClass;

	protected StringTypeConverter(Class<E> eClass) {
		this.eClass = eClass;
	}

	@Override
	public String convertToDatabaseColumn(E attribute) {

		return attribute.getCode();
	}

	@Override
	public E convertToEntityAttribute(String dbData) {

		return EnumSet.allOf(eClass)
				.stream()
				.filter(e -> e.getCode().equals(dbData))
				.findAny()
				.orElseThrow(() -> new NoSuchElementException());
	}
}
