package com.anything.type.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import com.anything.type.CodeValue;

public final class StringTypeParameterConverter implements ConverterFactory<String, Enum<? extends CodeValue>>{

	@Override
	public <T extends Enum<? extends CodeValue>> Converter<String, T> getConverter(Class<T> targetType) {

		return source -> {
			T[] constants = targetType.getEnumConstants();
			for (T c : constants) {
				if (((CodeValue) c).getCode().equals(source.trim())) {
					return c;
				}
			}
			return null;
		};
	}
}
