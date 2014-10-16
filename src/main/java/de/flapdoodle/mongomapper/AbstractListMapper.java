package de.flapdoodle.mongomapper;

import com.google.common.collect.ImmutableList;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

public abstract class AbstractListMapper<T,S> extends AbstractMapper implements ObjectMapper<T>, ListWrappingMapper<S> {

    private final ObjectMapper<S> subMapper;

    public AbstractListMapper(ObjectMapper<S> subMapper) {
        this.subMapper = subMapper;
    }
    
    private ImmutableList<S> asObjectList(DBObject dbValue) {
        ImmutableList.Builder<S> builder=ImmutableList.builder();
        
        BasicDBList list=(BasicDBList) dbValue;
        
        for (Object entry : list) {
            DBObject styleDBObject=(DBObject) entry;
            builder.add(subMapper.asObject(styleDBObject));
        }

        return builder.build();
    }
    
    @Override
    public final T asObject(DBObject dbValue) {
        ImmutableList<S> list = asObjectList(dbValue);
        return asObject(list);
    }

    protected abstract T asObject(ImmutableList<S> list);

    
    @Override
    public final DBObject asDBObject(T value) {
        BasicDBList list=new BasicDBList();
        for (S style : asList(value)) {
            list.add(subMapper.asDBObject(style));
        }
        return list;

    }

    protected abstract ImmutableList<S> asList(T value);
    
    @Override
    public ObjectMapper<S> wrappedMapper() {
        return subMapper;
    }
}
