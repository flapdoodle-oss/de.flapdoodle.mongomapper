package de.flapdoodle.mongomapper.example;

public class Root {
    private final String foo;
    private final int bar;
    
    public Root(String foo, int bar) {
        this.foo = foo;
        this.bar = bar;
    }
    
    public String foo() {
        return foo;
    }
    
    public int bar() {
        return bar;
    }
}
