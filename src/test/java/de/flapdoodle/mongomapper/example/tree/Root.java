package de.flapdoodle.mongomapper.example.tree;

import org.joda.time.DateTime;

import com.google.common.base.Optional;

public class Root {
    private final String foo;
    private final int bar;
    private final DateTime created;
    private final Optional<Sub> sub;
    
    public Root(String foo, int bar, DateTime created) {
        this(foo,bar, created, Optional.<Sub>absent());
    }
    
    public Root(String foo, int bar, DateTime created, Optional<Sub> sub) {
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
    
    public Optional<Sub> sub() {
        return sub;
    }
}
