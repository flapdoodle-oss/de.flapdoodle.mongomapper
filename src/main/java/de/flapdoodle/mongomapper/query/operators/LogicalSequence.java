package de.flapdoodle.mongomapper.query.operators;

public enum LogicalSequence implements NamedMongoDBOperator {
    AND, OR;
    
    @Override
    public String toString(){
        return "$" + this.name().toLowerCase();
    }
}
