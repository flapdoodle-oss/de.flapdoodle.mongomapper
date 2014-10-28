package de.flapdoodle.mongomapper.query.operators;

public enum ArrayQueryType implements NamedMongoOperator {
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
