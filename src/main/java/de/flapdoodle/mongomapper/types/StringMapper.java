package de.flapdoodle.mongomapper.types;

import de.flapdoodle.mongomapper.AttributeMapper;

public final class StringMapper implements AttributeMapper<String> {

    private final String name;

    public StringMapper(String name) {
        this.name = name;
    }
    
    @Override
    public String name() {
        return name;
    }

    @Override
    public Object asDBobject(String value) {
        return value;
    }

    @Override
    public String asObject(Object value) {
        return (String) value;
    }

}
