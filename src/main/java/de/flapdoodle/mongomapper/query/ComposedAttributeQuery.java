package de.flapdoodle.mongomapper.query;

import de.flapdoodle.mongomapper.Attr;

public class ComposedAttributeQuery<T> extends AttributeQuery<T>{

    private Query query;

    public ComposedAttributeQuery(Attr<T> attribute, Query query){
        super(attribute);
        this.query = query;
    }

    @Override
    public String generate() {
        StringBuffer buffer = new StringBuffer("{\"");
        buffer.append(attribute().name());
        buffer.append("\":\"");
        buffer.append(query.generate());
        buffer.append("\"}");
        
        return buffer.toString();
    }

}
