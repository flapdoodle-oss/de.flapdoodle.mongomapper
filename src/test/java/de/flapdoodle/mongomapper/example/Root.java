package de.flapdoodle.mongomapper.example;

import org.joda.time.DateTime;

public class Root {
    private final String foo;
    private final int bar;
    private final DateTime created;
    
    public Root(String foo, int bar, DateTime created) {
        this.foo = foo;
        this.bar = bar;
        this.created = created;
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
}
