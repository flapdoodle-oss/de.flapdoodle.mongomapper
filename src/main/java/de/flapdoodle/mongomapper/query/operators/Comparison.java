package de.flapdoodle.mongomapper.query.operators;

public enum Comparison implements NamedMongoOperator {
    GT, GTE, LT, LTE, NE, SIZE;
    
    @Override
    public String toString(){
        return "$" + this.name().toLowerCase();
    }
}
