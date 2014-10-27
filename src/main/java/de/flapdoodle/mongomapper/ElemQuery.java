package de.flapdoodle.mongomapper;


public class ElemQuery<T> {

    public ElemQuery<T> is(Attr<String> bar, String string) {
        return this;
    }

    public ElemQuery<T> build() {
        return this;
    }
    
}