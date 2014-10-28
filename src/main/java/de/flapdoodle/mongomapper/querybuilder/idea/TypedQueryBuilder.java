package de.flapdoodle.mongomapper.querybuilder.idea;

import de.flapdoodle.mongomapper.ObjectReadMapper;
import de.flapdoodle.mongomapper.query.Properties;
import de.flapdoodle.mongomapper.query.Property;
import de.flapdoodle.mongomapper.query.QueryableProperty;
import de.flapdoodle.mongomapper.query.VoidProperty;

public class TypedQueryBuilder<T, P extends Property<?, ?>> {

    private TypedQueryBuilder() {
        
    }
    
    public static <T> TypedQueryBuilder<T, VoidProperty> start(ObjectReadMapper<T> mapper) {
        return new TypedQueryBuilder<T, VoidProperty>();
    }
    
    public <TL> TypedQueryBuilder<T,P> is(QueryableProperty<TL, ? extends Property<T, ?>> property, TL value) {
        System.out.println(""+Properties.name(property)+"="+property.mapper().asDBObject(value));
        return this;
    }
}
