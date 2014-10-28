package de.flapdoodle.mongomapper;

public class Attr<T> {
    
    private String name;

    public Attr(String name) {
        this.name = name;
    }
    
    public String name() {
        return name;
    }
}