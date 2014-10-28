package de.flapdoodle.mongomapper.query.operators;

public enum ArrayQueryType implements NamedMongoDBOperator {
    SIZE, ELEMMATCH, ALL;

    @Override
    public String toString() {
        if (this.equals(ELEMMATCH)) {
            return "$elemMatch";
        } else {
            return "$" + name().toLowerCase();
        }
    }
}
