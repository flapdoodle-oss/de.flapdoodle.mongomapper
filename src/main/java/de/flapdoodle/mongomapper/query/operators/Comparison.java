package de.flapdoodle.mongomapper.query.operators;

public enum Comparison {
    GT, GTE, LT, LTE, NE;
    
    @Override
    public String toString(){
        return "$" + this.name().toLowerCase();
    }
}
