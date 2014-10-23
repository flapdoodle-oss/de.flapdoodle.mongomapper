package de.flapdoodle.mongomapper.example.list;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableList;

public class Root {
    private final String foo;
    private final int bar;
    private final DateTime created;
    private final ImmutableList<? extends Sub> sub;
    
    public Root(String foo, int bar, DateTime created, ImmutableList<? extends Sub> sub) {
        this.foo = foo;
        this.bar = bar;
        this.created = created;
        this.sub = sub;
        
    }
    
    public String foo() {
        return foo;
    }
    
    public int bar() {
        return bar;
    }
    
    public DateTime created() {
        return created;
    }
    
    public ImmutableList<? extends Sub> sub() {
        return sub;
    }
}
