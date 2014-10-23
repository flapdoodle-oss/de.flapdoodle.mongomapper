package de.flapdoodle.mongomapper.types;

import de.flapdoodle.mongomapper.AttributeMapper;

public class EnumMapper<E extends Enum<E>> implements AttributeMapper<E> {

    private final String name;
    private final Class<E> enumType;

    public EnumMapper(String name, Class<E> enumType) {
        this.name = name;
        this.enumType = enumType;
    }
    
    @Override
    public String name() {
        return name;
    }

    @Override
    public Object asDBObject(E value) {
        return value.name();
    }

    @Override
    public E asObject(Object value) {
        if (value!=null) {
            return Enum.valueOf(enumType, (String) value);
        }
        return null;
    }

}
