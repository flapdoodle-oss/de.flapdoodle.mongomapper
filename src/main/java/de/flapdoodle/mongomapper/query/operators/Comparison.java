package de.flapdoodle.mongomapper.query.operators;

public enum Comparison implements NamedMongoDBOperator {
    GT, GTE, LT, LTE, NE, SIZE;
    
    @Override
    public String toString(){
        return "$" + this.name().toLowerCase();
    }
}
