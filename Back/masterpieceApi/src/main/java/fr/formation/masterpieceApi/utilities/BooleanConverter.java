package fr.formation.masterpieceApi.utilities;

import javax.persistence.AttributeConverter;

/*
 * A custom converter to convert entity Boolean attribute state into
 * database column representation and back again.
 *
 * This implementation converts Boolean from/to String such as:
 *
 * Boolean.TRUE from/to "T"
 * Boolean.FALSE from/to "F"
 */
public class BooleanConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean value) {
	return Boolean.TRUE.equals(value) ? "T" : "F";
    }

    @Override
    public Boolean convertToEntityAttribute(String value) {
	return "T".equals(value);
    }
}
