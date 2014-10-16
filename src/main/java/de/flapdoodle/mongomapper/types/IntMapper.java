package de.flapdoodle.mongomapper.types;

import de.flapdoodle.mongomapper.AttributeMapper;

public final class IntMapper implements AttributeMapper<Integer> {

    private final String name;

    public IntMapper(String name) {
        this.name = name;
    }
    
    @Override
    public String name() {
        return name;
    }

    @Override
    public Object asDBobject(Integer value) {
        return value;
    }

    @Override
    public Integer asObject(Object value) {
        return (Integer) value;
    }

}
