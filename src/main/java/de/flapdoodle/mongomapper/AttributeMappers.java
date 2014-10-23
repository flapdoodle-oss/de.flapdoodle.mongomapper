package de.flapdoodle.mongomapper;

import java.util.Collection;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public abstract class AttributeMappers {

    private AttributeMappers() {
        // no instance
    }

    /**
     * Wandelt data in Objekte.
     * 
     * @param data
     * @param mapper
     * @return
     */
    public static <T> T from(DBObject data, AttributeMapper<T> mapper) {
        Object sourceValue = data.get(mapper.name());
        if (sourceValue!=null) {
            return mapper.asObject(sourceValue);
        }
        return null;
    }

    public static AttributeValueMap asMap(DBObject data, AttributeMapper<?>... mapper) {
        return asMap(data, ImmutableList.copyOf(mapper));
    }

    public static AttributeValueMap asMap(DBObject data, Collection<? extends AttributeMapper<?>> mapper) {
        AttributeValueMap.Builder builder = AttributeValueMap.builder();
        for (AttributeMapper m : mapper) {
            Object mapped = from(data, m);
            if (mapped != null) {
                builder.put(m, mapped);
            }
        }
        return builder.build();
    }

    public static DBObject asDBObject(AttributeValueMap attributes,
            Collection<? extends AttributeMapper<?>> mapper) {
        BasicDBObjectBuilder builder=new BasicDBObjectBuilder();
        for (AttributeMapper m : mapper) {
            Object value = attributes.get(m);
            if (value!=null) {
                builder.add(m.name(), m.asDBObject(value));
            }
        }
        
        return builder.get();
    }

    public static ImmutableSet<String> names(Collection<AttributeMapper<?>> mapper) {
        
        Collection<String> names = Collections2.transform(mapper,new Function<AttributeMapper, String>() {
            @Override
            public String apply(AttributeMapper input) {
                return input.name();
            }
        });
        ImmutableSet<String> namesAsSet = ImmutableSet.copyOf(names);
        Preconditions.checkArgument(namesAsSet.size()==names.size(),"name collisions %s",names);
        return namesAsSet;
    }
    
    public static <T> Function<T,Object> asDBObjectFunction(final AttributeMapper<T> mapper) {
        return new Function<T, Object>() {
            @Override
            public Object apply(T input) {
                return mapper.asDBObject(input);
            };
        };
    }

    public static <T> Function<Object,T> asObjectFunction(final AttributeMapper<T> mapper) {
        return new Function<Object, T>() {
            @Override
            public T apply(Object input) {
                return mapper.asObject(input);
            };
        };
    }
}
