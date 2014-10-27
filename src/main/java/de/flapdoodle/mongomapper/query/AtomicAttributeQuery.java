package de.flapdoodle.mongomapper.query;

import de.flapdoodle.mongomapper.Attr;

public final class AtomicAttributeQuery<T> extends AttributeQuery<T>{

    private final T value;

    public AtomicAttributeQuery(Attr<T> attribute, T value){
        super(attribute);
        this.value = value;
    }

    @Override
    public String generate() {
        StringBuffer buffer = new StringBuffer("{\"");
        buffer.append(attribute().name());
        buffer.append("\": \"");
        buffer.append(value);
        buffer.append("\"}");
        return buffer.toString();
    }

}
