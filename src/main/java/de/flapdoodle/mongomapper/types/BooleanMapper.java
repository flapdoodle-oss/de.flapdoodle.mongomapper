package de.flapdoodle.mongomapper.types;

import de.flapdoodle.mongomapper.AttributeMapper;


public final class BooleanMapper implements AttributeMapper<Boolean> {

    private final String name;

    public BooleanMapper(String name) {
        this.name = name;
    }
    
    @Override
    public String name() {
        return name;
    }

    @Override
    public Object asDBobject(Boolean value) {
        return value;
    }

    @Override
    public Boolean asObject(Object value) {
        return (Boolean) value;
    }

}
