package de.flapdoodle.mongomapper;

import com.google.common.collect.ImmutableList;


public class ListMapper<T> extends AbstractListMapper<ImmutableList<? extends T>,T>  {

    public ListMapper(ObjectMapper<T> subMapper) {
        super(subMapper);
    }

    @Override
    protected ImmutableList<? extends T> asObject(ImmutableList<T> list) {
        return list;
    }

    @Override
    protected ImmutableList<T> asList(ImmutableList<? extends T> value) {
        return ImmutableList.copyOf(value);
    }


}
