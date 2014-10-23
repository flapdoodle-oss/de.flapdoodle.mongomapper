package de.flapdoodle.mongomapper;

import com.google.common.collect.ImmutableList;


public class ListMapper<T,W extends ObjectMapper<T>> extends AbstractListMapper<ImmutableList<? extends T>,T,W>  {

    public ListMapper(W subMapper) {
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
