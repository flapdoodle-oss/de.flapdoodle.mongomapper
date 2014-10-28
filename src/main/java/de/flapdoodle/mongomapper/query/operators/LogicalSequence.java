package de.flapdoodle.mongomapper.query.operators;

public enum LogicalSequence implements NamedMongoOperator {
    AND, OR;
    
    @Override
    public String toString(){
        return "$" + this.name().toLowerCase();
    }
}
